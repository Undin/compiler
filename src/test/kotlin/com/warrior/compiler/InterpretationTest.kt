package com.warrior.compiler

import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by warrior on 27.03.16.
 */
class InterpretationTest {

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
        Assert.assertEquals("15", interpret(program, "5\n"))
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
        Assert.assertEquals("-1\n", interpret(program, "-1\n"))
        Assert.assertEquals("1\n-1\n", interpret(program, "1\n"))
        Assert.assertEquals("0\n0\n0\n", interpret(program, "0\n"))
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
        Assert.assertEquals("-1\n1\n0\n", interpret(program, "-1\n"))
        Assert.assertEquals("1\n0\n", interpret(program, "1\n"))
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
        Assert.assertEquals("0\n1\n1\n1\n0\n", interpret(program, ""))
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
        Assert.assertEquals("0\n1\n0\n", interpret(program, ""))
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
        val rand = Random()
        for (i in 0 until 100) {
            val a = rand.nextInt(10) + 1
            val pow = rand.nextInt(9) + 1
            val input = "$a $pow\n"
            val result = Math.pow(a.toDouble(), pow.toDouble()).toInt()
            Assert.assertEquals(result.toString(), interpret(program, input))
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
            Assert.assertEquals(fib(i).toString(), interpret(program, "$i\n"))
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
        Assert.assertEquals("1", interpret(program, "1\n"))
        Assert.assertEquals("1", interpret(program, "-1\n"))
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
        Assert.assertEquals("0\n1\n2\n", interpret(program, ""))
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
        Assert.assertEquals("5", interpret(program, "2 3\n"))
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
        Assert.assertEquals("5", interpret(program, "2 3\n"))
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
        Assert.assertEquals("2\n3\n5\n", interpret(program, "2 3\n"))
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
        Assert.assertEquals("2\n3\n5\n6\n", interpret(program, "2 3\n"))
    }

    @Test
    fun elementTest() {
        val program = """
            fn main() -> i32 {
                let v = readI32();
                let array = [[v + 1, v + 2], [v + 3, v + 4]];
                let tuple = ([v + 1, v + 2], v + 3);
                println(array[0][0]);
                println(array[1][1]);
                println(tuple.0[0]);
                println(tuple.0[1]);
                println(tuple.1);
                return 0;
            }
        """
        Assert.assertEquals("3\n6\n3\n4\n5\n", interpret(program, "2\n"))
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
        Assert.assertEquals("4\n1\n", interpret(program, "2\n1\n"))
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
        Assert.assertEquals("4\n1\n", interpret(program, "2\n1\n"))
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
        Assert.assertEquals("5\n1\n", interpret(program, "2\n1\n"))
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
        Assert.assertEquals("4\n1\n", interpret(program, "2\n1\n"))
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
        Assert.assertEquals("3\n2\n", interpret(program, "3\n"))
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
        Assert.assertEquals("2\n5\n", interpret(program, "5\n"))
    }

    private fun interpret(program: String, input: String): String {
        Compiler(program).use {
            if (it.compile()) {
                return it.interpret(input)
            } else {
                throw RuntimeException()
            }
        }
    }
}
