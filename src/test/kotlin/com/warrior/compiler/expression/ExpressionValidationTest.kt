package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.error
import com.warrior.compiler.parseExpr
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Ok
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 20.03.16.
 */
class ExpressionValidationTest {

    @Test
    fun constTest1() {
        Assert.assertEquals(
                Ok,
                parseExpr("1").validate()
        )
    }

    @Test
    fun constTest2() {
        Assert.assertEquals(
                Ok,
                parseExpr("false").validate()
        )
    }

    @Test
    fun variableTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("variableName", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("variableName").validate(variables = variables)
        )
    }

    @Test
    fun variableTest2() {
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseExpr("variableName").validate()
        )
    }

    @Test
    fun unaryTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("-a").validate(variables = variables)
        )
    }

    @Test
    fun unaryTest2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseExpr("-a").validate(variables = variables)
        )
    }

    @Test
    fun unaryTest3() {
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseExpr("!a").validate()
        )
    }

    @Test
    fun arithmeticTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("1 + a").validate(variables = variables)
        )
    }

    @Test
    fun arithmeticTest2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
        }
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, TYPE_MISMATCH),
                parseExpr("a + 2 * b").validate(variables = variables)
        )
    }

    @Test
    fun boolTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("true || a").validate(variables = variables)
        )
    }

    @Test
    fun boolTest2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
        }
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, UNDECLARED_VARIABLE, TYPE_MISMATCH),
                parseExpr("b || a && c").validate(variables = variables)
        )
    }

    @Test
    fun equalTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("1 == a").validate(variables = variables)
        )
    }

    @Test
    fun equalTest2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("a != true").validate(variables = variables)
        )
    }

    @Test
    fun equalTest4() {
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseExpr("1 == true").validate()
        )
    }

    @Test
    fun equalTest3() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
            putLocal("b", Type.I32)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseExpr("a != b").validate(variables = variables)
        )
    }

    @Test
    fun cmpTest1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
            putLocal("b", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("b >= a").validate(variables = variables)
        )
    }

    @Test
    fun cmpTest2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
            putLocal("b", Type.I32)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseExpr("b < a").validate(variables = variables)
        )
    }

    @Test
    fun cmpTest3() {
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Bool)
        }
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, TYPE_MISMATCH),
                parseExpr("b <= a").validate(variables = variables)
        )
    }

    @Test
    fun callTest1() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        Assert.assertEquals(
                Ok,
                parseExpr("f(12, true)").validate(functions)
        )
    }

    @Test
    fun callTest2() {
        Assert.assertEquals(
                error(UNDECLARED_FUNCTION),
                parseExpr("f(12, true)").validate()
        )
    }

    @Test
    fun callTest3() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseExpr("f(12, 1)").validate(functions)
        )
    }

    @Test
    fun callTest4() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        Assert.assertEquals(
                error(WRONG_ARGS_NUMBER),
                parseExpr("f(12, true, 0)").validate(functions)
        )
    }

    @Test
    fun callTest5() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
        }
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, TYPE_MISMATCH),
                parseExpr("f(b, a)").validate(functions, variables)
        )
    }

    @Test
    fun callTest6() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.I32)
            putLocal("b", Type.I32)
            putLocal("c", Type.Bool)
            putLocal("d", Type.Bool)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH, TYPE_MISMATCH),
                parseExpr("f(c || d, b + a)").validate(functions, variables)
        )
    }

    @Test
    fun test1() {
        val variables = SymbolTable<Type>().apply {
            putLocal("b", Type.I32)
            putLocal("c", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("1 + b != c").validate(variables = variables)
        )
    }

    @Test
    fun test2() {
        val variables = SymbolTable<Type>().apply {
            putLocal("b", Type.Bool)
            putLocal("c", Type.Bool)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH, TYPE_MISMATCH),
                parseExpr("1 + b != c").validate(variables = variables)
        )
    }

    @Test
    fun test3() {
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.I32)
            putLocal("y", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("x <= y && x % 2 == 0").validate(variables = variables)
        )
    }

    @Test
    fun test4() {
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.Bool)
            putLocal("y", Type.I32)
        }
        Assert.assertEquals(
                error(TYPE_MISMATCH, TYPE_MISMATCH, UNDECLARED_VARIABLE, TYPE_MISMATCH),
                parseExpr("x + y && x || z < 2 == 0").validate(variables = variables)
        )
    }

    @Test
    fun test5() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.I32)
            putLocal("y", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("5 * f(x, y >= 0)").validate(functions, variables)
        )
    }

    @Test
    fun test6() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32, Type.Bool), Type.Bool))
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.Bool)
        }
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, WRONG_ARGS_NUMBER, TYPE_MISMATCH, TYPE_MISMATCH),
                parseExpr("5 * f(x, y >= 0, x)").validate(functions, variables)
        )
    }

    @Test
    fun test7() {
        val functions = mapOf("f" to Type.Fn(listOf(Type.I32), Type.I32),
                "g" to Type.Fn(listOf(Type.I32, Type.Bool), Type.I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.I32)
            putLocal("y", Type.I32)
        }
        Assert.assertEquals(
                Ok,
                parseExpr("x <= g(y, true) || f(5 * x) % 2 == 0").validate(functions, variables)
        )
    }

    @Test
    fun test8() {
        val functions = mapOf("f" to Type.Fn(listOf(), Type.I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("x", Type.Bool)
            putLocal("y", Type.I32)
        }
        Assert.assertEquals(
                error(UNDECLARED_FUNCTION, TYPE_MISMATCH, TYPE_MISMATCH, WRONG_ARGS_NUMBER, TYPE_MISMATCH, TYPE_MISMATCH),
                parseExpr("(x <= g(y, true)) + (f(5 * x) % 2 == 0)").validate(functions, variables)
        )
    }
}
