package com.warrior.compiler

import org.junit.Assert
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by warrior on 27.03.16.
 */
class RunTest {

    private val random = Random()

    @Test
    fun simpleTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                print(a + 10);
                return 0;
            }
        """
        Assert.assertEquals("15", run(program, input = "5\n"))
    }

    @Test
    fun lazyAndExpressionTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                if (f(a) && f(-a)) {
                    println(0);
                }
                return 0;
            }

            fn f(a: i32) -> bool {
                println(a);
                return a >= 0;
            }
        """
        Assert.assertEquals("-1\n", run(program, input = "-1\n"))
        Assert.assertEquals("1\n-1\n", run(program, input = "1\n"))
        Assert.assertEquals("0\n0\n0\n", run(program, input = "0\n"))
    }

    @Test
    fun lazyOrExpressionTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                if (f(a) || f(-a)) {
                    println(0);
                }
                return 0;
            }

            fn f(a: i32) -> bool {
                println(a);
                return a >= 0;
            }
        """
        Assert.assertEquals("-1\n1\n0\n", run(program, input = "-1\n"))
        Assert.assertEquals("1\n0\n", run(program, input = "1\n"))
    }

    @Test
    fun innerBlockTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                {
                    println(a);
                    a = 1;
                    println(a);
                    let b: i32 = 1;
                    println(b);
                }
                println(a);
                let b: i32 = 0;
                println(b);
                return 0;
            }
        """
        Assert.assertEquals("0\n1\n1\n1\n0\n", run(program, input = ""))
    }

    @Test
    fun scopeTest() {
        val program = """
            let a: i32 = 0;

            fn main() -> i32 {
                println(a);
                {
                    let a: i32 = 1;
                    println(a);
                }
                println(a);
                return 0;
            }
        """
        Assert.assertEquals("0\n1\n0\n", run(program, input = ""))
    }

    @Test
    fun fastPowTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                let pow: i32 = 0;
                read(a);
                read(pow);
                let result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                print(result);
                return 0;
            }
        """
        for (i in 0 until 100) {
            val a = random.nextInt(10) + 1
            val pow = random.nextInt(9) + 1
            val input = "$a $pow\n"
            val result = Math.pow(a.toDouble(), pow.toDouble()).toInt()
            Assert.assertEquals(result.toString(), run(program, input = input))
        }
    }

    @Test
    fun recursionTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                print(fib(a));
                return 0;
            }

            fn fib(a: i32) -> i32 {
                let result: i32 = 0;
                if (a == 0 || a == 1) {
                    result = a;
                } else {
                    result = fib(a - 1) + fib(a - 2);
                }
                return result;
            }
        """
        fun fib(a: Int): Int {
            var result: Int;
            if (a == 0 || a == 1) {
                result = a
            } else {
                result = fib(a - 1) + fib(a - 2)
            }
            return result
        }
        for (i in 0..20) {
            Assert.assertEquals(fib(i).toString(), run(program, input = "$i\n"))
        }
    }

    @Test
    fun returnTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                print(abs(a));
                return 0;
            }

            fn abs(a: i32) -> i32 {
                if (a < 0) {
                    return -a;
                }
                return a;
            }
        """
        Assert.assertEquals("1", run(program, input = "1\n"))
        Assert.assertEquals("1", run(program, input = "-1\n"))
    }

    @Test
    fun globalTest() {
        val program = """
            let a: i32 = 0;

            fn main() -> i32 {
                println(a);
                f();
                println(a);
                f();
                println(a);
                return 0;
            }

            fn f() -> i32 {
                a = a + 1;
                return 0;
            }
        """
        Assert.assertEquals("0\n1\n2\n", run(program, input = ""))
    }

    @Test
    fun argumentsTest() {
        val program = """
            fn main() -> i32 {
                let a = readInt();
                let b = readInt();
                print(add(a, b));
                return 0;
            }

            fn add(a: i32, b: i32) -> i32 {
                return a + b;
            }

            fn readInt() -> i32 {
                let a: i32 = 0;
                read(a);
                return a;
            }
        """
        Assert.assertEquals("5", run(program, input = "2 3\n"))
    }

    @Test
    fun buildinTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = readI32();
                let b: i32 = readI32();
                print(add(a, b));
                return 0;
            }

            fn add(a: i32, b: i32) -> i32 {
                return a + b;
            }
        """
        Assert.assertEquals("5", run(program, input = "2 3\n"))
    }

    @Test
    fun tupleElementTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = readI32();
                let b: i32 = readI32();
                let c = (a, b, a + b);
                println(c.0);
                println(c.1);
                println(c.2);
                return 0;
            }
        """
        Assert.assertEquals("2\n3\n5\n", run(program, input = "2 3\n"))
    }

    @Test
    fun arrayElementTest() {
        val program = """
            fn main() -> i32 {
                let a: i32 = readI32();
                let b: i32 = readI32();
                let c = [a, b, a + b];
                let d = [a * b; 10];
                let i = 0;
                while (i < 3) {
                    println(c[i]);
                    i = i + 1;
                }
                println(d[5]);
                return 0;
            }
        """
        Assert.assertEquals("2\n3\n5\n6\n", run(program, input = "2 3\n"))
    }

    @Test
    fun elementTest() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let array = [[v + 1, v + 2], [v + 3, v + 4]];
                let array2 = [(v + 1, v + 2); 3];
                let tuple = ([v + 1, v + 2], v + 3);
                println(array[0][0]);
                println(array[1][1]);
                println(array2[2].0);
                println(array2[0].1);
                println(tuple.0[0]);
                println(tuple.0[1]);
                println(tuple.1);
                return 0;
            }
        """
        Assert.assertEquals("3\n6\n3\n4\n3\n4\n5\n", run(program, input = "2\n"))
    }

    @Test
    fun setTupleElementTest() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let tuple = (v + 1, v + 2);
                println(tuple.1);
                v = readI32();
                tuple.1 = v;
                println(tuple.1);
                return 0;
            }
        """
        Assert.assertEquals("4\n1\n", run(program, input = "2\n1\n"))
    }

    @Test
    fun setArrayElementTest() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let array = [v + 1, v + 2];
                println(array[1]);
                v = readI32();
                array[1] = v;
                println(array[1]);
                return 0;
            }
        """
        Assert.assertEquals("4\n1\n", run(program, input = "2\n1\n"))
    }

    @Test
    fun setElementTest1() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let array = [(v + 1, v + 2), (v + 3, v + 4)];
                println(array[1].0);
                v = readI32();
                array[1].0 = v;
                println(array[1].0);
                return 0;
            }
        """
        Assert.assertEquals("5\n1\n", run(program, input = "2\n1\n"))
    }

    @Test
    fun setElementTest2() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let tuple = ([v + 1, v + 2], v + 3);
                println(tuple.0[1]);
                v = readI32();
                tuple.0[1] = v;
                println(tuple.0[1]);
                return 0;
            }
        """
        Assert.assertEquals("4\n1\n", run(program, input = "2\n1\n"))
    }

    @Test
    fun copyTest1() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let a1 = [1, 2];
                let a2 = a1;
                a1[1] = v;
                println(a1[1]);
                println(a2[1]);
                return 0;
            }
        """
        Assert.assertEquals("3\n2\n", run(program, input = "3\n"))
    }

    @Test
    fun copyTest2() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let a1 = ([1, 2], 3);
                let a2 = a1.0;
                a2[1] = v;
                println(a1.0[1]);
                println(a2[1]);
                return 0;
            }
        """
        Assert.assertEquals("2\n5\n", run(program, input = "5\n"))
    }

    @Test
    fun functionWithTupleTest() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let b = readI32();
                let tuple = (a, b);
                let result = f(tuple);
                println(result.0);
                println(result.1);
                return 0;
            }

            fn f(a: (i32, i32)) -> (i32, i32) {
                if (a.0 + a.1 > 0) {
                    return (a.1, a.0);
                } else {
                    return (0, 0);
                }
            }

        """

        fun f(a: Int, b: Int): Pair<Int, Int> = if (a + b > 0) { b to a } else { 0 to 0 }

        for (i in 1..20) {
            val a = random.nextInt()
            val b = random.nextInt();
            val (res1, res2) = f(a, b)
            val out = run(program, input = "$a\n$b\n")
            Assert.assertEquals("$res1\n$res2\n", out)
        }
    }

    @Test
    fun functionWithArrayTest() {
        val program = """
            fn main() -> i32 {
                let matrix = [[0; 3]; 3];
                let i = 0;
                while (i < 3) {
                    let j = 0;
                    while (j < 3) {
                        matrix[i][j] = readI32();
                        j = j + 1;
                    }
                    i = i + 1;
                }
                let transposeMatrix = transpose(matrix);
                printMatrix(transposeMatrix);
                return 0;
            }

            fn transpose(matrix: [[i32; 3]; 3]) -> [[i32; 3]; 3] {
                let i = 0;
                while (i < 3) {
                    let j = 0;
                    while (j < i) {
                        let x = matrix[i][j];
                        matrix[i][j] = matrix[j][i];
                        matrix[j][i] = x;
                        j = j + 1;
                    }
                    i = i + 1;
                }
                return matrix;
            }

            fn printMatrix(matrix: [[i32; 3]; 3]) -> i32 {
                let i = 0;
                while (i < 3) {
                    let j = 0;
                    while (j < 3) {
                        println(matrix[i][j]);
                        j = j + 1;
                    }
                    i = i + 1;
                }
                return 0;
            }

        """
        fun transpose(matrix: Array<IntArray>): Array<IntArray> {
            val transposeMatrix = Array(3) { IntArray(3) }
            for (i in 0..matrix.lastIndex) {
                for (j in 0..i) {
                    transposeMatrix[i][j] = matrix[j][i]
                    transposeMatrix[j][i] = matrix[i][j]
                }
            }
            return transposeMatrix
        }

        fun toString(matrix: Array<IntArray>): String {
            val builder = StringBuilder();
            for (a in matrix) {
                for (elem in a) {
                    builder.append(elem).append("\n")

                }
            }
            return builder.toString()
        }

        for (i in 1..20) {
            val matrix = Array(3) { IntArray(3) { random.nextInt() } }
            val transposeMatrix = transpose(matrix)
            val out = run(program, input = toString(matrix))
            Assert.assertEquals(toString(transposeMatrix), out)
        }
    }

    @Test
    fun functionWithAggregateTypesTest() {
        val program = """
            fn main() -> i32 {
                let i = 0;
                let a = [(0, 0); 3];
                while (i < 3) {
                    a[i].0 = readI32();
                    a[i].1 = readI32();
                    i = i + 1;
                }
                let reversed = reverse(a);
                i = 0;
                while (i < 3) {
                    println(reversed[i].0);
                    println(reversed[i].1);
                    i = i + 1;
                }
                return 0;
            }

            fn reverse(a: [(i32, i32); 3]) -> [(i32, i32); 3] {
                let result = [(0, 0); 3];
                let i = 0;
                while (i < 3) {
                    result[i] = a[2 - i];
                    i = i + 1;
                }
                return result;
            }
        """

        for (i in 1..20) {
            val array = Array(3) { random.nextInt() to random.nextInt() }
            var reversed = array.clone()
            reversed.reverse()
            val out = run(program, input = array.map { "${it.first}\n${it.second}\n" }.fold("", String::plus))
            Assert.assertEquals(reversed.map { "${it.first}\n${it.second}\n" }.fold("", String::plus), out)
        }

    }

    @Test
    fun globalAggregateTypeTest() {
        val program = """
            let a = [0; 5];

            fn main() -> i32 {
                let v = readI32();
                let i = 0;
                while (i < 5) {
                    a[i] = v + i;
                    i = i + 1;
                }
                f();
                return 0;
            }

            fn f() -> i32 {
                let i = 0;
                while (i < 5) {
                    println(a[i]);
                    i = i + 1;
                }
                return 0;
            }
        """
        val out = run(program, input = "3\n")
        Assert.assertEquals("3\n4\n5\n6\n7\n", out)
    }

    @Test
    fun destructiveDeclarationTest() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let a = [(v, v + 1), (v + 2, v + 3)];
                let i = 0;
                while (i < 2) {
                    let (f, s) = a[i];
                    println(f);
                    println(s);
                    i = i + 1;
                }
                return 0;
            }
        """
        val out = run(program, input = "1\n")
        Assert.assertEquals("1\n2\n3\n4\n", out)
    }

    @Test
    fun emptyArrayTest() {
        val program = """
            let a: [i32; 0] = [];
            let b: [[i32; 0]; 10] = [[]; 10];
            let c: (i32, [i32; 0]) = (1, []);

            fn main() -> i32 {
                let a: [i32; 0] = [];
                let b: [[i32; 0]; 10] = [[]; 10];
                let c: (i32, [i32; 0]) = (1, []);
                return 0;
            }
        """
        val out = run(program, input = "")
        Assert.assertEquals("", out)
    }

    @Test
    fun setEmptyArrayTest() {
        val program = """
            fn main() -> i32 {
                let a: [i32; 0] = [];
                let b: [[i32; 0]; 10] = [[]; 10];
                let c: (i32, [i32; 0]) = (1, []);

                a = [];
                b[5] = [];
                c.1 = [];
                return 0;
            }
        """
        val out = run(program, input = "")
        Assert.assertEquals("", out)
    }

    @Test
    fun extensionFunctionTest1() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let i = 0;
                while (i < v) {
                    if (i.isEven()) {
                        println(i);
                    }
                    if (isEven(i)) {
                        println(i);
                    }
                    i = i + 1;
                }
                return 0;
            }

            fn i32.isEven(self) -> bool {
                return self % 2 == 0;
            }
        """
        val v = 10
        val expectedOut = (0 until 10).filter { it % 2 == 0 }.fold("", { str, i -> "$str$i\n$i\n" })
        val out = run(program, input = "$v\n")
        Assert.assertEquals(expectedOut, out)
    }

    @Test
    fun extensionFunctionTest2() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let a = (1, 2).add((v, v));
                let b = add((5, 6), (v, v));
                println(a.0);
                println(a.1);
                println(b.0);
                println(b.1);
                return 0;
            }

            fn (i32, i32).add(self, a: (i32, i32)) -> (i32, i32) {
                return (self.0 + a.0, self.1 + a.1);
            }
        """
        val out = run(program, input = "5\n")
        Assert.assertEquals("6\n7\n10\n11\n", out)
    }

    @Test(expected = RuntimeException::class)
    fun withoutTailRecursionElimination1() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fib(a, 0, 1);
                println(res);
                return 0;
            }

            fn fib(n: i32, a: i32, b: i32) -> i32 {
                if (n == 0) {
                    return a;
                } else {
                    return fib(n - 1, b, (a + b) % 10000);
                }
            }
        """
        val out = run(program, false, "1000000\n")
        Assert.assertEquals("6875\n", out)
    }

    @Test(expected = RuntimeException::class)
    fun withoutTailRecursionElimination2() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fact(a);
                println(res);
                return 0;
            }

            fn fact(n: i32) -> i32 {
                if (n == 0) {
                    return 1;
                } else {
                    return n * fact(n - 1);
                }
            }
        """
        val out = run(program, false, "1000000\n")
        Assert.assertEquals("0\n", out)
    }

    @Test
    fun tailRecursionElimination1() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fib(a, 0, 1);
                println(res);
                return 0;
            }

            fn fib(n: i32, a: i32, b: i32) -> i32 {
                if (n == 0) {
                    return a;
                } else {
                    return fib(n - 1, b, (a + b) % 10000);
                }
            }
        """
        val out = run(program, true, "1000000\n")
        Assert.assertEquals("6875\n", out)
    }

    @Test
    fun tailRecursionElimination2() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = a.fib(0, 1);
                println(res);
                return 0;
            }

            fn i32.fib(self, a: i32, b: i32) -> i32 {
                if (self == 0) {
                    return a;
                } else {
                    return (self - 1).fib(b, (a + b) % 10000);
                }
            }
        """
        val out = run(program, true, "1000000\n")
        Assert.assertEquals("6875\n", out)
    }

    @Test
    fun tailRecursionElimination3() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fib(a, (0, 1));
                println(res.0);
                println(res.1);
                return 0;
            }

            fn fib(n: i32, a: (i32, i32)) -> (i32, i32) {
                if (n == 0) {
                    return a;
                } else {
                    return fib(n - 1, (a.1, (a.0 + a.1) % 10000));
                }
            }
        """
        val out = run(program, true, "10000\n")
        Assert.assertEquals("6875\n7501\n", out)
    }

    @Test
    fun tailRecursionElimination4() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fact(a);
                println(res);
                return 0;
            }

            fn fact(n: i32) -> i32 {
                if (n == 0) {
                    return 1;
                } else {
                    return n * fact(n - 1);
                }
            }
        """
        val out = run(program, true, "1000000\n")
        Assert.assertEquals("0\n", out) // 1000000! == 0 because of integer overflow
    }

    @Test
    fun tailRecursionElimination5() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = f(a);
                println(res);
                return 0;
            }

            fn f(n: i32) -> i32 {
                if (n == 0) {
                    return 0;
                } else {
                    return n + f(n - 1);
                }
            }
        """
        for (i in 1..100) {
            val a = random.nextInt(1 shl 15)
            val out = run(program, true, "$a\n")
            Assert.assertEquals("${(1 + a) * a / 2}\n", out)
        }
    }

    @Test
    fun tailRecursionElimination6() {
        val program = """
            fn main() -> i32 {
                let a = readI32();
                let res = fib(a);
                println(res);
                return 0;
            }

            fn fib(n: i32) -> i32 {
                if (n == 0 || n == 1) {
                    return n;
                } else {
                    return fib(n - 1) + fib(n - 2);
                }
            }
        """
        val fibs = Array(41) { 0 }
        for (i in 0..40) {
            if (i == 0 || i == 1) {
                fibs[i] = i
            } else {
                fibs[i] = fibs[i - 1] + fibs[i - 2]
            }
            val out = run(program, true, "$i\n")
            Assert.assertEquals("${fibs[i]}\n", out)
        }
    }

    private fun run(program: String, useTailRecursionOptimization: Boolean = false, input: String): String {
        val executableFilename = "out"
        val success = Compiler(program)
                .use { it.compile(executableFilename, useTailRecursionOptimization) }
        if (!success) {
            throw IllegalStateException()
        }
        try {
            val process = ProcessBuilder()
                    .command("./$executableFilename")
                    .start()
            process.outputStream.write(input.toByteArray())
            process.outputStream.flush()
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw RuntimeException()
            }

            return process.inputStream.readBytes().toString(Charsets.UTF_8)
        } finally {
            File(executableFilename).delete()
        }
    }
}
