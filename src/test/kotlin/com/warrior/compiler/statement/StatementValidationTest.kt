package com.warrior.compiler.statement

import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
import com.warrior.compiler.error
import com.warrior.compiler.parseStatement
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Ok
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 24.03.16.
 */
class StatementValidationTest {

    @Test
    fun expressionTest1() {
        val variables = mutableMapOf<String, Type>("a" to I32)
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                Ok,
                parseStatement("1 + a;").validate(functions, variables, "f")
        )
    }

    @Test
    fun expressionTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement("1 + a;").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun assignTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        Assert.assertEquals(
                Ok,
                parseStatement("a = 4;").validate(functions, variables, "f")
        )
    }

    @Test
    fun assignTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to Bool)
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement("a = 4;").validate(functions, variables, "f")
        )
    }

    @Test
    fun assignTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement("a = 4;").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun assignDeclTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                Ok,
                parseStatement("a: i32 = 4;").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun assignDeclTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to Bool)
        Assert.assertEquals(
                error(VARIABLE_IS_ALREADY_DECLARED),
                parseStatement("a: i32 = 4;").validate(functions, variables, "f")
        )
    }

    @Test
    fun assignDeclTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement("a: i32 = false;").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun returnTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        Assert.assertEquals(
                Ok,
                parseStatement("return a;").validate(functions, variables, "f")
        )
    }

    @Test
    fun returnTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement("return a;").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun returnTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to Bool)
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement("return a;").validate(functions, variables, "f")
        )
    }

    @Test
    fun printTest1() {
        val variables = mutableMapOf<String, Type>("a" to Bool)
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                Ok,
                parseStatement("print(a);").validate(functions, variables, "f")
        )
    }

    @Test
    fun printTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement("print(a);").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun readTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        Assert.assertEquals(
                Ok,
                parseStatement("read(a);").validate(functions, variables, "f")
        )
    }

    @Test
    fun readTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement("read(a);").validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun blockTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32;
                b: i32;
                read(a);
                read(b);
                print(a + b);
            }
        """
        Assert.assertEquals(
                Ok,
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun blockTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32;
                b: bool;
                read(a);
                read(b);
                print(a + b);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun blockTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32 = true;
                b: bool;
                read(a);
                read(b);
                print(a + b + c);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH, TYPE_MISMATCH, UNDECLARED_VARIABLE),
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun innerBlockTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32 = 4;
                {
                    a = 10;
                    b: i32 = 1;
                }
                b: i32 = 0;
            }
        """
        Assert.assertEquals(
                Ok,
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun innerBlockTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32 = 4;
                {
                    a = 10;
                    b: i32 = 1;
                }
                b = 0;
            }
        """
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun innerBlockTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            {
                a: i32 = 4;
                {
                    a: i32 = 10;
                    b: i32 = 1;
                }
                b: i32 = 0;
            }
        """
        Assert.assertEquals(
                error(VARIABLE_IS_ALREADY_DECLARED),
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }

    @Test
    fun ifTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32, "b" to I32)
        val statement = """
            if (a > 10) {
                b = 10;
            }
        """
        Assert.assertEquals(
                Ok,
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf("a" to Bool, "b" to I32)
        val statement = """
            if (a > 10) {
                b = 10;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (a > 10) {
                b = 10;
            }
        """
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifTest4() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to Bool)
        val statement = """
            if (a > 10) {
                b = 10;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH, UNDECLARED_VARIABLE),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (a > 0) {
                print(a);
            } else {
                print(-a);
            }
        """
        Assert.assertEquals(
                Ok,
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (0) {
                print(a);
            } else {
                print(-a);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (a > 0) {
                print(a + false);
            } else {
                print(-a);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest4() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (a > 0) {
                print(a);
            } else {
                print(-a - true);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest5() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (a > 0) {
                print(a + false);
            } else {
                print(-a - true);
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH, TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun ifElseTest6() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            if (b > 0) {
                print(a + false);
            } else {
                print(-a - true);
            }
        """
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, TYPE_MISMATCH, TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun whileTest1() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            while (a > 10) {
                print(a);
                a = a - 1;
            }
        """
        Assert.assertEquals(
                Ok,
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun whileTest2() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            while (0) {
                print(a);
                a = a - 1;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun whileTest3() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val variables = mutableMapOf<String, Type>("a" to I32)
        val statement = """
            while (a > 10) {
                print(a);
                a = a - false;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseStatement(statement).validate(functions, variables, "f")
        )
    }

    @Test
    fun whileTest4() {
        val functions = mapOf("f" to Fn(listOf(), I32))
        val statement = """
            while (a > 10) {
                print(a);
                a = 5;
            }
        """
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, UNDECLARED_VARIABLE, UNDECLARED_VARIABLE),
                parseStatement(statement).validate(functions, mutableMapOf(), "f")
        )
    }
}
