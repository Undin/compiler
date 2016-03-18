package com.warrior.compiler.expression

import com.warrior.compiler.TypedValue.BoolValue
import com.warrior.compiler.TypedValue.IntValue
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

/**
 * Created by warrior on 18.03.16.
 */
class ExpressionCalcTest {
    @Test
    fun constTest1() {
        Assert.assertEquals(
                IntValue(1),
                parseExpr("1").calculate(emptyMap())
        )
    }

    @Test
    fun constTest2() {
        Assert.assertEquals(
                BoolValue(false),
                parseExpr("false").calculate(emptyMap())
        )
    }

    @Test
    fun variableTest() {
        val env = mapOf("variableName" to IntValue(5))
        Assert.assertEquals(
                IntValue(5),
                parseExpr("variableName").calculate(env)
        )
    }

    @Test
    fun unaryTest1() {
        val a = 2
        val env = mapOf("a" to IntValue(a))
        Assert.assertEquals(
                IntValue(+a),
                parseExpr("+a").calculate(env)
        )
    }

    @Test
    fun unaryTest2() {
        Assert.assertEquals(
                IntValue(-5),
                parseExpr("-5").calculate(emptyMap())
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                BoolValue(!true),
                parseExpr("!true").calculate(emptyMap())
        )
    }

    @Test
    fun arithmeticTest1() {
        Assert.assertEquals(
                IntValue(1 + 2),
                parseExpr("1 + 2").calculate(emptyMap())
        )
    }

    @Test
    fun arithmeticTest2() {
        Assert.assertEquals(
                IntValue(1 + 2 * 3),
                parseExpr("1 + 2 * 3").calculate(emptyMap())
        )
    }

    @Test
    fun arithmeticTest3() {
        Assert.assertEquals(
                IntValue((12 - 2) / 5),
                parseExpr("(12 - 2) / 5").calculate(emptyMap())
        )
    }

    @Test
    fun arithmeticTest4() {
        Assert.assertEquals(
                IntValue(-(4 % 3) + 7),
                parseExpr("-(4 % 3) + 7").calculate(emptyMap())
        )
    }

    @Test
    fun arithmeticTest5() {
        val a = 4;
        val b = 2;
        val env = mapOf("a" to IntValue(a), "b" to IntValue(b))
        Assert.assertEquals(
                IntValue((a + 3) * (b - 8)),
                parseExpr("(a + 3) * (b - 8)").calculate(env)
        )
    }

    @Test
    fun boolTest1() {
        val a = false
        val env = mapOf("a" to BoolValue(a))
        Assert.assertEquals(
                BoolValue(true || a),
                parseExpr("true || a").calculate(env)
        )
    }

    @Test
    fun boolTest2() {
        val a = true
        val b = false
        val c = false
        val env = mapOf("a" to BoolValue(a), "b" to BoolValue(b), "c" to BoolValue(c))
        Assert.assertEquals(
                BoolValue(b || a && c),
                parseExpr("b || a && c").calculate(env)
        )
    }

    @Test
    fun boolTest3() {
        val a = true
        val b = false
        val c = true
        val qqq = true
        val env = mapOf("a" to BoolValue(a), "b" to BoolValue(b), "c" to BoolValue(c), "qqq" to BoolValue(qqq))
        Assert.assertEquals(
                BoolValue(!(qqq || b) || a && c),
                parseExpr("!(qqq || b) || a && c").calculate(env)
        )
    }

    @Test
    fun cmpTest1() {
        Assert.assertEquals(
                BoolValue(1 == 5),
                parseExpr("1 == 5").calculate(emptyMap())
        )
    }

    @Test
    fun cmpTest2() {
        val b = 10
        val c = -4
        val env = mapOf("b" to IntValue(b), "c" to IntValue(c))
        Assert.assertEquals(
                BoolValue(b >= c),
                parseExpr("b >= c").calculate(env)
        )
    }

    @Ignore
    @Test
    fun callTest1() {
        Assert.assertEquals(
                Call("f", emptyList()),
                parseExpr("f()")
        )
    }

    @Ignore
    @Test
    fun callTest2() {
        Assert.assertEquals(
                Call("f", listOf(Variable("param"), I32(2), Bool(false))),
                parseExpr("f(param, 2, false)")
        )
    }

    @Ignore
    @Test
    fun callTest3() {
        Assert.assertEquals(
                Call("f", listOf(Variable("a"), mul(I32(2), Variable("b")), or(Variable("c"), Variable("d")))),
                parseExpr("f(a, 2 * b, c || d)")
        )
    }

    @Test
    fun test1() {
        val b = 5
        val c = 7
        val env = mapOf("b" to IntValue(b), "c" to IntValue(c))
        Assert.assertEquals(
                BoolValue(1 + b != c),
                parseExpr("1 + b != c").calculate(env)
        )
    }

    @Test
    fun test2() {
        val a = 7
        val b = 48
        val env = mapOf("b" to IntValue(b), "a" to IntValue(a))
        Assert.assertEquals(
                BoolValue(5 * a - 1 > b / 10),
                parseExpr("5 * a - 1 > b / 10").calculate(env)
        )
    }

    @Test
    fun test3() {
        val x = 7
        val y = 48
        val env = mapOf("x" to IntValue(x), "y" to IntValue(y))
        Assert.assertEquals(
                BoolValue(x <= y && x % 2 == 0),
                parseExpr("x <= y && x % 2 == 0").calculate(env)
        )
    }

    @Ignore
    @Test
    fun test4() {
        Assert.assertEquals(
                mul(I32(5), Call("f", listOf(Variable("x"), ge(Variable("y"), I32(0))))),
                parseExpr("5 * f(x, y >= 0)")
        )
    }

    @Ignore
    @Test
    fun test5() {
        Assert.assertEquals(
                or(le(Variable("x"), Call("g", listOf(Variable("y"), Bool(true)))),
                        eq(mod(Call("f", listOf(mul(I32(5), Variable("x")))), I32(2)), I32(0))),
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0")
        )
    }
}
