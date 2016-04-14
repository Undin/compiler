package com.warrior.compiler.module

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.error
import com.warrior.compiler.parseGlobalDeclaration
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Ok
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 26.03.16.
 */
class GlobalValidationTest {

    @Test
    fun test1() {
        val global = "let a: i32 = 10;"
        Assert.assertEquals(
                Ok,
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test2() {
        val global = "let a: bool = 10;"
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test3() {
        val global = "let a: i32 = 10;"
        val variables = SymbolTable<Type>().apply {
            putGlobal("a", Type.I32)
        }
        Assert.assertEquals(
                error(VARIABLE_IS_ALREADY_DECLARED),
                parseGlobalDeclaration(global).validate(variables)
        )
    }

    @Test
    fun test4() {
        val global = "let a: i32 = true;"
        val variables = SymbolTable<Type>().apply {
            putGlobal("a", Type.I32)
        }
        Assert.assertEquals(
                error(VARIABLE_IS_ALREADY_DECLARED),
                parseGlobalDeclaration(global).validate(variables)
        )
    }

    @Test
    fun test5() {
        val global = "let a = true;"
        Assert.assertEquals(
                Ok,
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test6() {
        val global = "let a: (i32, i32) = (1, 2);"
        Assert.assertEquals(
                Ok,
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test7() {
        val global = "let a = (1, 2);"
        Assert.assertEquals(
                Ok,
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test8() {
        val global = "let a = ([1, 2], (3, 4));"
        Assert.assertEquals(
                Ok,
                parseGlobalDeclaration(global).validate()
        )
    }

    @Test
    fun test9() {
        val global = "let a = [1 + 1, 2];"
        Assert.assertEquals(
                error(NON_CONST_EXPRESSION),
                parseGlobalDeclaration(global).validate()
        )
    }
}
