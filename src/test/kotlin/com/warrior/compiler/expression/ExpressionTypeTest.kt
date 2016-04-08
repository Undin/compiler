package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
import com.warrior.compiler.checkType
import com.warrior.compiler.parseExpr
import org.junit.Test

/**
 * Created by warrior on 08.04.16.
 */
class ExpressionTypeTest {

    @Test
    fun tupleLiteralTest() {
        checkType("(1, true)")
    }

    @Test
    fun arrayLiteralTest() {
        checkType("[1, 2]")
    }

    @Test
    fun notTest() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("v", Bool)
        }
        checkType("!v", variables = symbolTable)
    }

    @Test
    fun unaryMinusTest() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("v", I32)
        }
        checkType("-v", variables = symbolTable)
    }

    @Test
    fun binaryTest() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        checkType("a + 2", variables = symbolTable)
    }

    @Test
    fun callTest() {
        val functions = mapOf("f" to Type.Fn(listOf(I32, I32), I32))
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        checkType("f(a, 2)", functions, symbolTable)
    }

    @Test
    fun test() {
        val functions = mapOf("f" to Type.Fn(listOf(Array(I32, 2), Tuple(listOf(I32, Bool)), Bool), I32))
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", I32)
            putLocal("b", I32)
            putLocal("c", Bool)
            putLocal("d", I32)
            putLocal("e", Bool)
        }
        checkType("-f([a + 1, 2], (b * 3, c || d < 10), e) * 4", functions, symbolTable)
    }

    private fun checkType(expression: String, functions: Map<String, Type.Fn> = emptyMap(), variables: SymbolTable<Type> = SymbolTable()) {
        val expr = parseExpr(expression)
        expr.validate(functions, variables)
        expr.determineType(functions, variables)
        expr.checkType()
    }
}
