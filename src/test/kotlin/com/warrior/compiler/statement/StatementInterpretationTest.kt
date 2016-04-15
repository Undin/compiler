package com.warrior.compiler.statement

import com.warrior.compiler.*
import com.warrior.compiler.validation.TypedValue
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 21.03.16.
 */
class StatementInterpretationTest {

    @Test
    fun expressionTest() {
        val a = int(1)
        val env = mutableMapOf<String, TypedValue>("a" to a)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("1 + a;").interpret(env = env)
        )
    }

    @Test
    fun assignTest() {
        val a = int(1)
        val b = int(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a = b + 4;").interpret(env)
        )
    }

    @Test
    fun setTupleElementTest() {
        val a = tuple(bool(true), int(0))
        val b = int(2)
        val env = mutableMapOf("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a.1 = b;").interpret(env)
        )
    }

    @Test
    fun setArrayElementTest() {
        val a = array(int(1), int(0))
        val b = int(2)
        val env = mutableMapOf("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a[1] = b;").interpret(env)
        )
    }

    @Test
    fun setElementTest1() {
        val a = array(tuple(int(1), bool(true)), tuple(int(0), bool(false)))
        val b = int(2)
        val env = mutableMapOf("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a[1].0 = b;").interpret(env)
        )
    }

    @Test
    fun setElementTest2() {
        val a = tuple(int(1), array(int(2), int(3)))
        val b = int(2)
        val env = mutableMapOf("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a.1[0] = b;").interpret(env)
        )
    }

    @Test
    fun assignDeclTest() {
        val b = int(2)
        val env = mutableMapOf<String, TypedValue>("b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("let a: i32 = b + 4;").interpret(env)
        )
    }

    @Test
    fun destructiveDeclarationTest() {
        val tuple = tuple(int(1), array(int(2), int(3)))
        val env = mutableMapOf<String, TypedValue>("tuple" to tuple)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("let (a, b) = tuple;").interpret(env)
        )
    }

    @Test
    fun printTest() {
        val a = int(1)
        val b = int(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf(a),
                parseStatement("print(a);").interpret(env)
        )
    }

    @Test
    fun readTest() {
        val a = int(1)
        val b = int(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("read(a);").interpret(env, input = mutableListOf(int(4)))
        )
    }

    @Test
    fun blockTest() {
        val statement = """
            {
                let a: i32 = 0;
                let b: i32 = 0;
                read(a);
                read(b);
                print(a + b);
            }
        """
        val input = mutableListOf<TypedValue>(int(-1), int(11));
        Assert.assertEquals(
                listOf(int(10)),
                parseStatement(statement).interpret(input = input)
        )
    }

    @Test
    fun innerBlockTest1() {
        val statement = """
            {
                let a: i32 = 4;
                print(a);
                {
                    a = 10;
                    print(a);
                }
                print(a);
            }
        """
        Assert.assertEquals(
                listOf(int(4), int(10), int(10)),
                parseStatement(statement).interpret()
        )
    }

    @Test
    fun innerBlockTest2() {
        val statement = """
            {
                {
                    let a: i32 = 10;
                    print(a);
                }
                let a: i32 = 4;
                print(a);
            }
        """
        Assert.assertEquals(
                listOf(int(10), int(4)),
                parseStatement(statement).interpret()
        )
    }

    @Test
    fun ifTest() {
        val statement = """
            if (a > 10) {
                print(a);
            }
        """
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to int(0)))
        )
        Assert.assertEquals(
                listOf(int(12)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to int(12)))
        )
    }

    @Test
    fun ifElseTest() {
        val statement = """
            if (a > 0) {
                print(a);
            } else {
                print(-a);
            }
        """
        Assert.assertEquals(
                listOf(int(1)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to int(1)))
        )
        Assert.assertEquals(
                listOf(int(1)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to int(1)))
        )
    }

    @Test
    fun whileTest() {
        val a = int(13)
        val env = mutableMapOf<String, TypedValue>("a" to a)
        val statement = """
            while (a > 10) {
                print(a);
                a = a - 1;
            }
        """
        Assert.assertEquals(
                listOf(int(13), int(12), int(11)),
                parseStatement(statement).interpret(env)
        )
    }

    @Test
    fun test() {
        val statement = """
            {
                let a: i32 = 0;
                let pow: i32 = 0;
                read(a);
                read(pow);
                let result = 1;
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
            }
        """
        val input = mutableListOf<TypedValue>(int(2), int(20))
        Assert.assertEquals(
                listOf(int(1 shl 20)),
                parseStatement(statement).interpret(input = input)
        )
    }
}
