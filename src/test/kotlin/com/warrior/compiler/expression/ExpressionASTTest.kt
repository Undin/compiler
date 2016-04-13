package com.warrior.compiler.expression

import com.warrior.compiler.expression.AggregateLiteral.*
import com.warrior.compiler.expression.MemoryExpr.*
import com.warrior.compiler.parseExpr
import org.antlr.v4.runtime.ParserRuleContext
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 17.03.16.
 */
class ExpressionASTTest {
    
    private val ctx = ParserRuleContext()

    @Test
    fun intLiteralTest() {
        Assert.assertEquals(
                i32(1),
                parseExpr("1")
        )
    }

    @Test
    fun boolLiteralTest() {
        Assert.assertEquals(
                bool(false),
                parseExpr("false")
        )
    }

    @Test
    fun tupleLiteralTest() {
        Assert.assertEquals(
                tuple(i32(1), bool(true)),
                parseExpr("(1, true)")
        )
    }

    @Test
    fun seqArrayLiteralTest() {
        Assert.assertEquals(
                array(i32(1), i32(2)),
                parseExpr("[1, 2]")
        )
    }

    @Test
    fun repeatArrayLiteralTest() {
        Assert.assertEquals(
                array(i32(1), 10),
                parseExpr("[1; 10]")
        )
    }

    @Test
    fun nestedAggregationLiteralTest1() {
        Assert.assertEquals(
                tuple(array(i32(1), i32(2)), array(i32(0), 3), bool(false)),
                parseExpr("([1, 2], [0; 3], false)")
        )
    }

    @Test
    fun nestedAggregationLiteralTest2() {
        Assert.assertEquals(
                array(tuple(i32(1), i32(2)), tuple(i32(3), i32(4))),
                parseExpr("[(1, 2), (3, 4)]")
        )
    }

    @Test
    fun nestedAggregationLiteralTest3() {
        Assert.assertEquals(
                array(tuple(i32(1), i32(2)), 3),
                parseExpr("[(1, 2); 3]")
        )
    }

    @Test
    fun variableTest() {
        Assert.assertEquals(
                variable("variableName"),
                parseExpr("variableName")
        )
    }

    @Test
    fun unaryTest1() {
        Assert.assertEquals(
                variable("a"),
                parseExpr("+a")
        )
    }

    @Test
    fun unaryTest2() {
        Assert.assertEquals(
                UnaryMinus(ctx, i32(5)),
                parseExpr("-5")
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                Not(ctx, bool(true)),
                parseExpr("!true")
        )
    }

    @Test
    fun arithmeticTest1() {
        Assert.assertEquals(
                add(i32(1), i32(2)),
                parseExpr("1 + 2")
        )
    }

    @Test
    fun arithmeticTest2() {
        Assert.assertEquals(
                add(i32(1), mul(i32(2), i32(3))),
                parseExpr("1 + 2 * 3")
        )
    }

    @Test
    fun arithmeticTest3() {
        Assert.assertEquals(
                div(sub(i32(12), i32(2)), i32(5)),
                parseExpr("(12 - 2) / 5")
        )
    }

    @Test
    fun arithmeticTest4() {
        Assert.assertEquals(
                add(UnaryMinus(ctx, mod(i32(4), i32(3))), i32(7)),
                parseExpr("-(4 % 3) + 7")
        )
    }

    @Test
    fun arithmeticTest5() {
        Assert.assertEquals(
                mul(add(variable("a"), i32(3)), sub(variable("b"), i32(8))),
                parseExpr("(a + 3) * (b - 8)")
        )
    }

    @Test
    fun boolTest1() {
        Assert.assertEquals(
                or(bool(true), variable("a")),
                parseExpr("true || a")
        )
    }

    @Test
    fun boolTest2() {
        Assert.assertEquals(
                or(variable("b"), and(variable("a"), variable("c"))),
                parseExpr("b || a && c")
        )
    }

    @Test
    fun boolTest3() {
        Assert.assertEquals(
                or(Not(ctx, or(variable("qqq"), variable("b"))), and(variable("a"), variable("c"))),
                parseExpr("!(qqq || b) || a && c")
        )
    }

    @Test
    fun cmpTest1() {
        Assert.assertEquals(
                eq(i32(1), i32(5)),
                parseExpr("1 == 5")
        )
    }

    @Test
    fun cmpTest2() {
        Assert.assertEquals(
                ge(variable("b"), variable("c")),
                parseExpr("b >= c")
        )
    }

    @Test
    fun callTest1() {
        Assert.assertEquals(
                call("f", emptyList()),
                parseExpr("f()")
        )
    }

    @Test
    fun callTest2() {
        Assert.assertEquals(
                call("f", listOf(variable("param"), i32(2), bool(false))),
                parseExpr("f(param, 2, false)")
        )
    }

    @Test
    fun callTest3() {
        Assert.assertEquals(
                call("f", listOf(variable("a"), mul(i32(2), variable("b")), or(variable("c"), variable("d")))),
                parseExpr("f(a, 2 * b, c || d)")
        )
    }

    @Test
    fun tupleElementTest() {
        Assert.assertEquals(
                tupleElem(variable("a"), 1),
                parseExpr("a.1")
        )
    }

    @Test
    fun arrayElementTest() {
        Assert.assertEquals(
                arrayElem(variable("a"), i32(1)),
                parseExpr("a[1]")
        )
    }

    @Test
    fun elementTest1() {
        Assert.assertEquals(
                tupleElem(arrayElem(variable("a"), i32(1)), 0),
                parseExpr("a[1].0")
        )
    }

    @Test
    fun elementTest2() {
        Assert.assertEquals(
                arrayElem(tupleElem(variable("a"), 1), i32(0)),
                parseExpr("a.1[0]")
        )
    }

    @Test
    fun test1() {
        Assert.assertEquals(
                ne(add(i32(1), variable("b")), variable("c")),
                parseExpr("1 + b != c")
        )
    }

    @Test
    fun test2() {
        Assert.assertEquals(
                gt(sub(mul(i32(5), variable("a")), i32(1)), div(variable("b"), i32(10))),
                parseExpr("5 * a - 1 > b / 10")
        )
    }

    @Test
    fun test3() {
        Assert.assertEquals(
                and(le(variable("x"), variable("y")), eq(mod(variable("x"), i32(2)), i32(0))),
                parseExpr("x <= y && x % 2 == 0")
        )
    }

    @Test
    fun test4() {
        Assert.assertEquals(
                mul(i32(5), call("f", listOf(variable("x"), ge(variable("y"), i32(0))))),
                parseExpr("5 * f(x, y >= 0)")
        )
    }

    @Test
    fun test5() {
        Assert.assertEquals(
                or(le(variable("x"), call("g", listOf(variable("y"), bool(true)))),
                        eq(mod(call("f", listOf(mul(i32(5), variable("x")))), i32(2)), i32(0))),
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0")
        )
    }

    @Test
    fun test6() {
        Assert.assertEquals(
                lt(mul(i32(5), arrayElem(variable("a"), variable("x"))), add(tupleElem(variable("b"), 2), i32(10))),
                parseExpr("5 * a[x] < b.2 + 10")
        )
    }

    private fun i32(value: Int): I32 = I32(ctx, value)
    private fun bool(value: Boolean): Bool = Bool(ctx, value)
    private fun tuple(vararg elements: Expr): Tuple = Tuple(ctx, elements.asList())
    private fun array(vararg elements: Expr): SeqArray = SeqArray(ctx, elements.asList())
    private fun array(elementsValue: Expr, size: Int): RepeatArray = RepeatArray(ctx, elementsValue, size)
    private fun variable(name: String): Variable = Variable(ctx, name)
    private fun call(name: String, args: List<Expr>): Call = Call(ctx, name, args)
    private fun tupleElem(array: Expr, index: Int) = TupleElement(ctx, array, index)
    private fun arrayElem(array: Expr, index: Expr) = ArrayElement(ctx, array, index)

    private fun and(lhs: Expr, rhs: Expr) = and(ctx, lhs, rhs)
    private fun or(lhs: Expr, rhs: Expr) = or(ctx, lhs, rhs)
    private fun add(lhs: Expr, rhs: Expr) = add(ctx, lhs, rhs)
    private fun sub(lhs: Expr, rhs: Expr) = sub(ctx, lhs, rhs)
    private fun mul(lhs: Expr, rhs: Expr) = mul(ctx, lhs, rhs)
    private fun div(lhs: Expr, rhs: Expr) = div(ctx, lhs, rhs)
    private fun mod(lhs: Expr, rhs: Expr) = mod(ctx, lhs, rhs)
    private fun eq(lhs: Expr, rhs: Expr) = eq(ctx, lhs, rhs)
    private fun ne(lhs: Expr, rhs: Expr) = ne(ctx, lhs, rhs)
    private fun lt(lhs: Expr, rhs: Expr) = lt(ctx, lhs, rhs)
    private fun le(lhs: Expr, rhs: Expr) = le(ctx, lhs, rhs)
    private fun gt(lhs: Expr, rhs: Expr) = gt(ctx, lhs, rhs)
    private fun ge(lhs: Expr, rhs: Expr) = ge(ctx, lhs, rhs)
}
