package com.warrior.compiler.module

import com.warrior.compiler.parseFunction
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 01/05/16.
 */

class TailRecursionOptimization {

    @Test
    fun checkTailRecursion1() {
        val function = """
            fn f(a: i32, b: i32) -> i32 {
                return a + b;
            }
        """
        val fn = parseFunction(function)
        Assert.assertFalse(fn.isTailRecursive())
    }

    @Test
    fun checkTailRecursion2() {
        val function = """
            fn fib(a: i32) -> i32 {
                if (a == 0 || a == 1) {
                    return a;
                } else {
                    return fib(a - 1) + fib(a - 2);
                }
            }
        """
        val fn = parseFunction(function)
        Assert.assertFalse(fn.isTailRecursive())
    }

    @Test
    fun checkTailRecursion3() {
        val function = """
            fn fib(n: i32, a: i32, b: i32) -> i32 {
                if (n == 0) {
                    return a;
                } else {
                    return fib(n - 1, b, a + b);
                }
            }
        """
        val fn = parseFunction(function)
        Assert.assertTrue(fn.isTailRecursive())
    }

    @Test
    fun checkTailRecursion4() {
        val function = """
            fn fact(n: i32) -> i32 {
                if (n == 0) {
                    return 1;
                } else {
                    return n * f(n - 1);
                }
            }
        """
        val fn = parseFunction(function)
        Assert.assertFalse(fn.isTailRecursive())
    }

    @Test
    fun checkTailRecursion5() {
        val function = """
            fn f(n: i32) -> i32 {
                if (n == 0) {
                    return 1;
                } else {
                    return g(n - 1);
                }
            }
        """
        val fn = parseFunction(function)
        Assert.assertFalse(fn.isTailRecursive())
    }
}
