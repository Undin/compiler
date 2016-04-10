package com.warrior.compiler.expression

import com.warrior.compiler.parseExpr
import com.warrior.compiler.validation.Fn
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
                IntValue(1),
                parseExpr("1").calculate()
        )
    }

    @Test
    fun boolLiteralTest() {
        Assert.assertEquals(
                BoolValue(false),
                parseExpr("false").calculate()
        )
    }

    @Test
    fun tupleLiteralTest() {
        Assert.assertEquals(
                TupleValue(listOf(IntValue(1), BoolValue(true))),
                parseExpr("(1, true)").calculate()
        )
    }

    @Test
    fun seqArrayLiteralTest() {
        Assert.assertEquals(
                ArrayValue(listOf(IntValue(1), IntValue(2))),
                parseExpr("[1, 2]").calculate()
        )
    }

    @Test
    fun repeatArrayLiteralTest() {
        Assert.assertEquals(
                ArrayValue(IntValue(1), 10),
                parseExpr("[1; 10]").calculate()
        )
    }

    @Test
    fun nestedAggregationLiteralTest1() {
        Assert.assertEquals(
                TupleValue(listOf(ArrayValue(listOf(IntValue(1), IntValue(2))),
                        ArrayValue(IntValue(0), 3), BoolValue(false))),
                parseExpr("([1, 2], [0; 3], false)").calculate()
        )
    }

    @Test
    fun nestedAggregationLiteralTest2() {
        Assert.assertEquals(
                ArrayValue(listOf(TupleValue(listOf(IntValue(1), IntValue(2))),
                        TupleValue(listOf(IntValue(3), IntValue(4))))),
                parseExpr("[(1, 2), (3, 4)]").calculate()
        )
    }

    @Test
    fun variableTest() {
        val variables = mapOf("variableName" to IntValue(5))
        Assert.assertEquals(
                IntValue(5),
                parseExpr("variableName").calculate(variables = variables)
        )
    }

    @Test
    fun unaryTest1() {
        val a = 2
        val variables = mapOf("a" to IntValue(a))
        Assert.assertEquals(
                IntValue(+a),
                parseExpr("+a").calculate(variables = variables)
        )
    }

    @Test
    fun unaryTest2() {
        Assert.assertEquals(
                IntValue(-5),
                parseExpr("-5").calculate()
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                BoolValue(!true),
                parseExpr("!true").calculate()
        )
    }

    @Test
    fun arithmeticTest1() {
        Assert.assertEquals(
                IntValue(1 + 2),
                parseExpr("1 + 2").calculate()
        )
    }

    @Test
    fun arithmeticTest2() {
        Assert.assertEquals(
                IntValue(1 + 2 * 3),
                parseExpr("1 + 2 * 3").calculate()
        )
    }

    @Test
    fun arithmeticTest3() {
        Assert.assertEquals(
                IntValue((12 - 2) / 5),
                parseExpr("(12 - 2) / 5").calculate()
        )
    }

    @Test
    fun arithmeticTest4() {
        Assert.assertEquals(
                IntValue(-(4 % 3) + 7),
                parseExpr("-(4 % 3) + 7").calculate()
        )
    }

    @Test
    fun arithmeticTest5() {
        val a = 4;
        val b = 2;
        val variables = mapOf("a" to IntValue(a), "b" to IntValue(b))
        Assert.assertEquals(
                IntValue((a + 3) * (b - 8)),
                parseExpr("(a + 3) * (b - 8)").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest1() {
        val a = false
        val variables = mapOf("a" to BoolValue(a))
        Assert.assertEquals(
                BoolValue(true || a),
                parseExpr("true || a").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest2() {
        val a = true
        val b = false
        val c = false
        val variables = mapOf("a" to BoolValue(a), "b" to BoolValue(b), "c" to BoolValue(c))
        Assert.assertEquals(
                BoolValue(b || a && c),
                parseExpr("b || a && c").calculate(variables = variables)
        )
    }

    @Test
    fun boolTest3() {
        val a = true
        val b = false
        val c = true
        val qqq = true
        val variables = mapOf("a" to BoolValue(a), "b" to BoolValue(b), "c" to BoolValue(c), "qqq" to BoolValue(qqq))
        Assert.assertEquals(
                BoolValue(!(qqq || b) || a && c),
                parseExpr("!(qqq || b) || a && c").calculate(variables = variables)
        )
    }

    @Test
    fun equalTest1() {
        Assert.assertEquals(
                BoolValue(1 == 5),
                parseExpr("1 == 5").calculate()
        )
    }

    @Test
    fun equalTest2() {
        val a = true
        val variables = mapOf("a" to BoolValue(a))
        Assert.assertEquals(
                BoolValue(true != a),
                parseExpr("true != a").calculate(variables = variables)
        )
    }

    @Test
    fun cmpTest1() {
        val b = 10
        val c = -4
        val variables = mapOf("b" to IntValue(b), "c" to IntValue(c))
        Assert.assertEquals(
                BoolValue(b >= c),
                parseExpr("b >= c").calculate(variables = variables)
        )
    }

    @Test
    fun callTest1() {
        val f = Fn() {
            IntValue(10);
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
                param * int - IntValue(1)
            }
        }
        val param = IntValue(5)

        val variables = mapOf("param" to param)
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                f(listOf(param, IntValue(2), BoolValue(false))),
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
                param * int - IntValue(1)
            }
        }
        val a = IntValue(2)
        val b = IntValue(3)
        val c = false
        val d = true

        val variables = mapOf("a" to a, "b" to b, "c" to BoolValue(c), "d" to BoolValue(d))
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                f(listOf(a, IntValue(2) * b, BoolValue(c || d))),
                parseExpr("f(a, 2 * b, c || d)").calculate(functions, variables)
        )
    }

    @Test
    fun tupleElementTest() {
        val variables = mapOf("a" to TupleValue(listOf(IntValue(1), IntValue(2))))
        Assert.assertEquals(
                IntValue(2),
                parseExpr("a.1").calculate(variables = variables)
        )
    }

    @Test
    fun arrayElementTest() {
        val variables = mapOf("a" to ArrayValue(listOf(IntValue(1), IntValue(2))))
        Assert.assertEquals(
                IntValue(2),
                parseExpr("a[1]").calculate(variables = variables)
        )
    }

    @Test
    fun elementTest1() {
        val variables = mapOf("a" to ArrayValue(listOf(
                TupleValue(listOf(IntValue(0), IntValue(1))),
                TupleValue(listOf(IntValue(2), IntValue(3)))
        )))
        Assert.assertEquals(
                IntValue(2),
                parseExpr("a[1].0").calculate(variables = variables)
        )
    }

    @Test
    fun elementTest2() {
        val variables = mapOf("a" to TupleValue(listOf(
                TupleValue(listOf(IntValue(0), IntValue(1))),
                ArrayValue(listOf(IntValue(2), IntValue(3)))
        )))
        Assert.assertEquals(
                IntValue(2),
                parseExpr("a.1[0]").calculate(variables = variables)
        )
    }

    @Test
    fun test1() {
        val b = 5
        val c = 7
        val variables = mapOf("b" to IntValue(b), "c" to IntValue(c))
        Assert.assertEquals(
                BoolValue(1 + b != c),
                parseExpr("1 + b != c").calculate(variables = variables)
        )
    }

    @Test
    fun test2() {
        val a = 7
        val b = 48
        val variables = mapOf("b" to IntValue(b), "a" to IntValue(a))
        Assert.assertEquals(
                BoolValue(5 * a - 1 > b / 10),
                parseExpr("5 * a - 1 > b / 10").calculate(variables = variables)
        )
    }

    @Test
    fun test3() {
        val x = 7
        val y = 48
        val variables = mapOf("x" to IntValue(x), "y" to IntValue(y))
        Assert.assertEquals(
                BoolValue(x <= y && x % 2 == 0),
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
                x + IntValue(2)
            }
        }
        val x = IntValue(7)
        val y = IntValue(-1)

        val variables = mapOf("x" to x, "y" to y)
        val functions = mapOf("f" to f)
        Assert.assertEquals(
                IntValue(5) * f(listOf(x, BoolValue(y >= IntValue(0)))) as IntValue,
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
                y + IntValue(2)
            }
        }
        val f = Fn() { args ->
            args[0] as IntValue
        }
        val x = IntValue(7)
        val y = IntValue(-1)

        val variables = mapOf("x" to x, "y" to y)
        val functions = mapOf("f" to f, "g" to g)
        Assert.assertEquals(
                BoolValue(x <= g(listOf(y, BoolValue(true))) as IntValue ||
                        f(listOf(IntValue(5) * x)) as IntValue % IntValue(2) == IntValue(0)),
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0").calculate(functions, variables)
        )
    }

    @Test
    fun test6() {
        val a = ArrayValue(listOf(IntValue(1), IntValue(2)))
        val x = IntValue(1)
        val b = TupleValue(listOf(BoolValue(false), IntValue(1), IntValue(7)))

        val variables = mapOf("a" to a, "x" to x, "b" to b)
        Assert.assertEquals(
                BoolValue(IntValue(5) * (a[x] as IntValue) < b[2] as IntValue + IntValue(10)),
                parseExpr("5 * a[x] < b.2 + 10").calculate(variables = variables)
        )
    }
}
