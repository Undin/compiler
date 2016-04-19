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
    fun seqArrayLiteralTest() {
        checkType("[1, 2]")
    }

    @Test
    fun repeatArrayLiteralTest() {
        checkType("[1; 2]")
    }

    @Test
    fun nestedAggregationLiteralTest1() {
        checkType("([1, 2], [0; 3], false)")
    }

    @Test
    fun nestedAggregationLiteralTest2() {
        checkType("[(1, 2), (3, 4)]")
    }

    @Test
    fun nestedAggregationLiteralTest3() {
        checkType("[(1, 2); 3]")
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
    fun extensionCallTest() {
        val functions = mapOf("add" to Type.Fn(listOf(I32, I32), I32, true))
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        checkType("a.add(2)", functions, symbolTable)
    }

    @Test
    fun tupleElementTest() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", Tuple(Bool, I32))
        }
        checkType("a.1", variables = symbolTable)
    }

    @Test
    fun arrayElementTest() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(I32, 2))
        }
        checkType("a[1]", variables = symbolTable)
    }

    @Test
    fun elementTest1() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", Tuple(Type.Array(Bool, 2), I32))
        }
        checkType("a.0[1]", variables = symbolTable)
    }

    @Test
    fun elementTest2() {
        val symbolTable = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(Tuple(Bool, I32), 2))
        }
        checkType("a[0].1", variables = symbolTable)
    }

    private fun checkType(expression: String, functions: Map<String, Type.Fn> = emptyMap(), variables: SymbolTable<Type> = SymbolTable()) {
        val expr = parseExpr(expression)
        expr.validate(functions, variables)
        expr.determineType(functions, variables)
        expr.checkType()
    }
}
