package com.warrior.compiler.expression

import com.warrior.compiler.parseExpr
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.*
import com.warrior.compiler.validation.TypedValue.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 18.03.16.
 */
class ExpressionCalcTest {

    @Test
    fun intLiteralTest() {
        Assert.assertEquals(
                int(1),
                parseExpr("1").calculate()
        )
    }

    @Test
    fun boolLiteralTest() {
        Assert.assertEquals(
                bool(false),
                parseExpr("false").calculate()
        )
    }

    @Test
    fun tupleLiteralTest() {
        Assert.assertEquals(
                tuple(int(1), bool(true)),
                parseExpr("(1, true)").calculate()
        )
    }

    @Test
    fun seqArrayLiteralTest() {
        Assert.assertEquals(
                array(int(1), int(2)),
                parseExpr("[1, 2]").calculate()
        )
    }

    @Test
    fun repeatArrayLiteralTest() {
        Assert.assertEquals(
                array(int(1), 10),
                parseExpr("[1; 10]").calculate()
        )
    }

    @Test
    fun nestedAggregationLiteralTest1() {
        Assert.assertEquals(
                tuple(array(int(1), int(2)), array(int(0), 3), bool(false)),
                parseExpr("([1, 2], [0; 3], false)").calculate()
        )
    }

    @Test
    fun nestedAggregationLiteralTest2() {
        Assert.assertEquals(
                array(tuple(int(1), int(2)), tuple(int(3), int(4))),
                parseExpr("[(1, 2), (3, 4)]").calculate()
        )
    }

    @Test
    fun variableTest() {
        val variables = mapOf("variableName" to int(5))
        Assert.assertEquals(
                int(5),
                parseExpr("variableName").calculate(variables = variables)
        )
    }

    @Test
    fun unaryTest1() {
        val a = 2
        val variables = mapOf("a" to int(a))
        Assert.assertEquals(
                int(+a),
                parseExpr("+a").calculate(variables = variables)
        )
    }

    @Test
    fun unaryTest2() {
        Assert.assertEquals(
                int(-5),
                parseExpr("-5").calculate()
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                bool(!true),
                parseExpr("!true").calculate()
        )
    }

    @Test
    fun arithmeticTest1() {
        Assert.assertEquals(
                int(1 + 2),
                parseExpr("1 + 2").calculate()
        )
    }

    @Test
    fun arithmeticTest2() {
        Assert.assertEquals(
                int(1 + 2 * 3),
                parseExpr("1 + 2 * 3").calculate()
        )
    }

    @Test
    fun arithmeticTest3() {
        Assert.assertEquals(
                int((12 - 2) / 5),
                parseExpr("(12 - 2) / 5").calculate()
        )
    }

    @Test
    fun arithmeticTest4() {
        Assert.assertEquals(
                int(-(4 % 3) + 7),
                parseExpr("-(4 % 3) + 7").calculate()
        )
    }

    @Test
    fun arithmeticTest5() {
        val a = 4;
        val b = 2;
        val variables = mapOf("a" to int(a), "b" to int(b))
        Assert.assertEquals(
                int((a + 3) * (b - 8)),
                parseExpr("(a + 3) * (b - 8)").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest1() {
        val a = false
        val variables = mapOf("a" to bool(a))
        Assert.assertEquals(
                bool(true || a),
                parseExpr("true || a").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest2() {
        val a = true
        val b = false
        val c = false
        val variables = mapOf("a" to bool(a), "b" to bool(b), "c" to bool(c))
        Assert.assertEquals(
                bool(b || a && c),
                parseExpr("b || a && c").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest3() {
        val a = true
        val b = false
        val c = true
        val qqq = true
        val variables = mapOf("a" to bool(a), "b" to bool(b), "c" to bool(c), "qqq" to bool(qqq))
        Assert.assertEquals(
                bool(!(qqq || b) || a && c),
                parseExpr("!(qqq || b) || a && c").calculate(variables = variables)
        )
    }

    @Test
    fun equalTest1() {
        Assert.assertEquals(
                bool(1 == 5),
                parseExpr("1 == 5").calculate()
        )
    }

    @Test
    fun equalTest2() {
        val a = true
        val variables = mapOf("a" to bool(a))
        Assert.assertEquals(
                bool(true != a),
                parseExpr("true != a").calculate(variables = variables)
        )
    }

    @Test
    fun cmpTest1() {
        val b = 10
        val c = -4
        val variables = mapOf("b" to int(b), "c" to int(c))
        Assert.assertEquals(
                bool(b >= c),
                parseExpr("b >= c").calculate(variables = variables)
        )
    }

    @Test
    fun callTest1() {
        val f = Fn() {
            int(10);
        }
        val functions = mapOf("f" to f);
        Assert.assertEquals(
                f(),
                parseExpr("f()").calculate(functions)
        )
    }

    @Test
    fun callTest2() {
        val f = Fn() { args ->
            val param = args[0] as IntValue
            val int = args[1] as IntValue
            val flag = args[2] as BoolValue
            if (flag.value) {
                param * int
            } else {
                param * int - int(1)
            }
        }
        val param = int(5)

        val variables = mapOf("param" to param)
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                f(listOf(param, int(2), bool(false))),
                parseExpr("f(param, 2, false)").calculate(functions, variables)
        )
    }

    @Test
    fun callTest3() {
        val f = Fn() { args ->
            val param = args[0] as IntValue
            val int = args[1] as IntValue
            val flag = args[2] as BoolValue
            if (flag.value) {
                param * int
            } else {
                param * int - int(1)
            }
        }
        val a = int(2)
        val b = int(3)
        val c = false
        val d = true

        val variables = mapOf("a" to a, "b" to b, "c" to bool(c), "d" to bool(d))
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                f(listOf(a, int(2) * b, bool(c || d))),
                parseExpr("f(a, 2 * b, c || d)").calculate(functions, variables)
        )
    }

    @Test
    fun tupleElementTest() {
        val variables = mapOf("a" to tuple(int(1), int(2)))
        Assert.assertEquals(
                int(2),
                parseExpr("a.1").calculate(variables = variables)
        )
    }

    @Test
    fun arrayElementTest() {
        val variables = mapOf("a" to array(int(1), int(2)))
        Assert.assertEquals(
                int(2),
                parseExpr("a[1]").calculate(variables = variables)
        )
    }

    @Test
    fun elementTest1() {
        val variables = mapOf("a" to array(tuple(int(0), int(1)), tuple(int(2), int(3))))
        Assert.assertEquals(
                int(2),
                parseExpr("a[1].0").calculate(variables = variables)
        )
    }

    @Test
    fun elementTest2() {
        val variables = mapOf("a" to tuple(tuple(int(0), int(1)), array(int(2), int(3))))
        Assert.assertEquals(
                int(2),
                parseExpr("a.1[0]").calculate(variables = variables)
        )
    }

    @Test
    fun test1() {
        val b = 5
        val c = 7
        val variables = mapOf("b" to int(b), "c" to int(c))
        Assert.assertEquals(
                bool(1 + b != c),
                parseExpr("1 + b != c").calculate(variables = variables)
        )
    }

    @Test
    fun test2() {
        val a = 7
        val b = 48
        val variables = mapOf("b" to int(b), "a" to int(a))
        Assert.assertEquals(
                bool(5 * a - 1 > b / 10),
                parseExpr("5 * a - 1 > b / 10").calculate(variables = variables)
        )
    }

    @Test
    fun test3() {
        val x = 7
        val y = 48
        val variables = mapOf("x" to int(x), "y" to int(y))
        Assert.assertEquals(
                bool(x <= y && x % 2 == 0),
                parseExpr("x <= y && x % 2 == 0").calculate(variables = variables)
        )
    }

    @Test
    fun test4() {
        val f = Fn() { args ->
            val x = args[0] as IntValue
            val flag = args[1] as BoolValue
            if (flag.value) {
                x
            } else {
                x + int(2)
            }
        }
        val x = int(7)
        val y = int(-1)

        val variables = mapOf("x" to x, "y" to y)
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                int(5) * f(listOf(x, bool(y >= int(0)))) as IntValue,
                parseExpr("5 * f(x, y >= 0)").calculate(functions, variables)
        )
    }

    @Test
    fun test5() {
        val g = Fn() { args ->
            val y = args[0] as IntValue
            val flag = args[1] as BoolValue
            if (flag.value) {
                y
            } else {
                y + int(2)
            }
        }
        val f = Fn() { args ->
            args[0] as IntValue
        }
        val x = int(7)
        val y = int(-1)

        val variables = mapOf("x" to x, "y" to y)
        val functions = mapOf("f" to f, "g" to g)
        Assert.assertEquals(
                bool(x <= g(listOf(y, bool(true))) as IntValue ||
                        f(listOf(int(5) * x)) as IntValue % int(2) == int(0)),
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0").calculate(functions, variables)
        )
    }

    @Test
    fun test6() {
        val a = array(int(1), int(2))
        val x = int(1)
        val b = tuple(bool(false), int(1), int(7))

        val variables = mapOf("a" to a, "x" to x, "b" to b)
        Assert.assertEquals(
                bool(int(5) * (a[x] as IntValue) < b[2] as IntValue + int(10)),
                parseExpr("5 * a[x] < b.2 + 10").calculate(variables = variables)
        )
    }
}
