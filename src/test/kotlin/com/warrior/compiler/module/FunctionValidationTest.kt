package com.warrior.compiler.module

import com.warrior.compiler.Type.*
import com.warrior.compiler.error
import com.warrior.compiler.parseFunction
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Ok
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 25.03.16.
 */
class FunctionValidationTest {

    @Test
    fun simpleBlockTest1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                return a + b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun simpleBlockTest2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                a + b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun simpleBlockTest3() {
        val function = """
            fn f(a: i32, b: bool) -> i32 {
                return a + b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, Bool), I32))
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun innerBlockTest1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                let c: i32 = a + b;
                {
                    return 2 * c;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun innerBlockTest2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                let c: i32 = a + b;
                {
                    2 * c;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun innerBlockTest3() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                let c: i32 = a + b;
                {
                    return 2 * c + g();
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(UNDECLARED_FUNCTION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifTest1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    return b;
                }
                return a;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifTest2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    return b;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifTest3() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    return b + true;
                }
                return a;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifElseTest1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    return b;
                } else {
                    return -b;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifElseTest2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    return b;
                } else {
                    -b;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun ifElseTest3() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                if (a > 0) {
                    b = b * d;
                    return b;
                } else {
                    b = b - c;
                    return -b;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(UNDECLARED_VARIABLE, UNDECLARED_VARIABLE),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun whileTest1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    b = b + a;
                    a = a - 1;
                }
                return b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun whileTest2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    b = b + a;
                    a = a - 1;
                    return b;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun whileTest3() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    b = b + a;
                    a = a - g(a);
                }
                return b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32), "g" to Fn(listOf(), I32))
        Assert.assertEquals(
                error(WRONG_ARGS_NUMBER),
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun test1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    if (b > 0) {
                        return 0;
                    }
                    b = b + a;
                    a = a - 1;
                }
                return b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun test2() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    if (b > 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                    b = b + a;
                    a = a - 1;
                }
                return b;
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                Ok,
                parseFunction(function).validate(functions)
        )
    }

    @Test
    fun test3() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                while (a > 0) {
                    if (b > 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                    b = b + a;
                    a = a - 1;
                }
            }
        """
        val functions = mapOf("f" to Fn(listOf(I32, I32), I32))
        Assert.assertEquals(
                error(RETURN_EXPRESSION),
                parseFunction(function).validate(functions)
        )
    }
}
