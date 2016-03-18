package com.warrior.compiler.expression

import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 17.03.16.
 */
class ExpressionASTTest {

    @Test
    fun constTest1() {
        Assert.assertEquals(
                I32(1),
                parseExpr("1")
        )
    }

    @Test
    fun constTest2() {
        Assert.assertEquals(
                Bool(false),
                parseExpr("false")
        )
    }

    @Test
    fun variableTest() {
        Assert.assertEquals(
                Variable("variableName"),
                parseExpr("variableName")
        )
    }

    @Test
    fun unaryTest1() {
        Assert.assertEquals(
                Variable("a"),
                parseExpr("+a")
        )
    }

    @Test
    fun unaryTest2() {
        Assert.assertEquals(
                UnaryMinus(I32(5)),
                parseExpr("-5")
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                Not(Bool(true)),
                parseExpr("!true")
        )
    }

    @Test
    fun arithmeticTest1() {
        Assert.assertEquals(
                add(I32(1), I32(2)),
                parseExpr("1 + 2")
        )
    }

    @Test
    fun arithmeticTest2() {
        Assert.assertEquals(
                add(I32(1), mul(I32(2), I32(3))),
                parseExpr("1 + 2 * 3")
        )
    }

    @Test
    fun arithmeticTest3() {
        Assert.assertEquals(
                div(sub(I32(12), I32(2)), I32(5)),
                parseExpr("(12 - 2) / 5")
        )
    }

    @Test
    fun arithmeticTest4() {
        Assert.assertEquals(
                add(UnaryMinus(mod(I32(4), I32(3))), I32(7)),
                parseExpr("-(4 % 3) + 7")
        )
    }

    @Test
    fun arithmeticTest5() {
        Assert.assertEquals(
                mul(add(Variable("a"), I32(3)), sub(Variable("b"), I32(8))),
                parseExpr("(a + 3) * (b - 8)")
        )
    }

    @Test
    fun boolTest1() {
        Assert.assertEquals(
                or(Bool(true), Variable("a")),
                parseExpr("true || a")
        )
    }

    @Test
    fun boolTest2() {
        Assert.assertEquals(
                or(Variable("b"), and(Variable("a"), Variable("c"))),
                parseExpr("b || a && c")
        )
    }

    @Test
    fun boolTest3() {
        Assert.assertEquals(
                or(Not(or(Variable("qqq"), Variable("b"))), and(Variable("a"), Variable("c"))),
                parseExpr("!(qqq || b) || a && c")
        )
    }

    @Test
    fun cmpTest1() {
        Assert.assertEquals(
                eq(I32(1), I32(5)),
                parseExpr("1 == 5")
        )
    }

    @Test
    fun cmpTest2() {
        Assert.assertEquals(
                ge(Variable("b"), Variable("c")),
                parseExpr("b >= c")
        )
    }

    @Test
    fun callTest1() {
        Assert.assertEquals(
                Call("f", emptyList()),
                parseExpr("f()")
        )
    }

    @Test
    fun callTest2() {
        Assert.assertEquals(
                Call("f", listOf(Variable("param"), I32(2), Bool(false))),
                parseExpr("f(param, 2, false)")
        )
    }

    @Test
    fun callTest3() {
        Assert.assertEquals(
                Call("f", listOf(Variable("a"), mul(I32(2), Variable("b")), or(Variable("c"), Variable("d")))),
                parseExpr("f(a, 2 * b, c || d)")
        )
    }

    @Test
    fun test1() {
        Assert.assertEquals(
                ne(add(I32(1), Variable("b")), Variable("c")),
                parseExpr("1 + b != c")
        )
    }

    @Test
    fun test2() {
        Assert.assertEquals(
                gt(sub(mul(I32(5), Variable("a")), I32(1)), div(Variable("b"), I32(10))),
                parseExpr("5 * a - 1 > b / 10")
        )
    }

    @Test
    fun test3() {
        Assert.assertEquals(
                and(le(Variable("x"), Variable("y")), eq(mod(Variable("x"), I32(2)), I32(0))),
                parseExpr("x <= y && x % 2 == 0")
        )
    }

    @Test
    fun test4() {
        Assert.assertEquals(
                mul(I32(5), Call("f", listOf(Variable("x"), ge(Variable("y"), I32(0))))),
                parseExpr("5 * f(x, y >= 0)")
        )
    }

    @Test
    fun test5() {
        Assert.assertEquals(
                or(le(Variable("x"), Call("g", listOf(Variable("y"), Bool(true)))),
                        eq(mod(Call("f", listOf(mul(I32(5), Variable("x")))), I32(2)), I32(0))),
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0")
        )
    }
}
