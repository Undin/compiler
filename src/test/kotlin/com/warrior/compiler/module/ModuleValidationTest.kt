package com.warrior.compiler.module

import com.warrior.compiler.error
import com.warrior.compiler.parseModule
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.*
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by warrior on 25.03.16.
 */
class ModuleValidationTest {

    @Test
    fun test1() {
        val module = """
            fn main() -> i32 {
                return 0;
            }

            fn f(a: i32, pow: i32) -> i32 {
                return 0;
            }

            fn g(a: i32) -> i32 {
                return 0;
            }
        """
        Assert.assertEquals(
                Ok,
                parseModule(module).validate()
        )
    }

    @Test
    fun test2() {
        val module = """
            fn main() -> i32 {
                return 0;
            }

            fn f(a: i32, pow: i32) -> i32 {
                return 0;
            }

            fn f(a: i32) -> i32 {
                return 0;
            }
        """
        Assert.assertEquals(
                error(FUNCTION_IS_ALREADY_DECLARED),
                parseModule(module).validate()
        )
    }

    @Test
    fun test3() {
        val module = """
            fn main() -> i32 {
                let a: i32 = 0;
                let pow: i32 = 0;
                read(a);
                read(pow);
                println(fast(a, pow));
                return 0;
            }

            fn fast(a: i32, pow: i32) -> i32 {
                let result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        Assert.assertEquals(
                Ok,
                parseModule(module).validate()
        )
    }

    @Test
    fun test4() {
        val module = """
            fn main() -> i32 {
                let a: i32 = 0;
                let pow: bool = false;
                read(a);
                read(pow);
                println(fast(a, pow));
                return 0;
            }

            fn fast(a: i32, pow: i32) -> i32 {
                result = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH, UNDECLARED_VARIABLE, UNDECLARED_VARIABLE, UNDECLARED_VARIABLE, UNDECLARED_VARIABLE),
                parseModule(module).validate()
        )
    }

    @Test
    fun test5() {
        val module = """
            let pow: i32 = 5;

            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                println(fast(a, pow));
                return 0;
            }

            fn fast(a: i32, pow: i32) -> i32 {
                let result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        Assert.assertEquals(
                Ok,
                parseModule(module).validate()
        )
    }

    @Test
    fun test6() {
        val module = """
            let pow: bool = false;

            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                println(fast(a, pow));
                return 0;
            }

            fn fast(a: i32, pow: i32) -> i32 {
                let result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        Assert.assertEquals(
                error(TYPE_MISMATCH),
                parseModule(module).validate()
        )
    }

    @Test
    fun test7() {
        val module = """
            let pow: bool = false;

            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                println(fast(a));
                return 0;
            }

            fn fast(a: i32) -> i32 {
                let result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        Assert.assertEquals(
                error(*Collections.nCopies(6, TYPE_MISMATCH).toTypedArray()),
                parseModule(module).validate()
        )
    }

    @Test
    fun test8() {
        val module = """
            let a: bool = false;

            fn main() -> i32 {
                let a: i32 = 0;
                read(a);
                println(a + 10);
                return 0;
            }
        """
        Assert.assertEquals(
                Ok,
                parseModule(module).validate()
        )
    }

    @Test
    fun test9() {
        val module = """
            fn f1(a: i32) -> i32 {
                return 0;
            }

            fn f2() -> i32 {
                let a: i32 = 0;
                read(a);
                return a;
            }
        """
        Assert.assertEquals(
                Ok,
                parseModule(module).validate()
        )
    }
}
