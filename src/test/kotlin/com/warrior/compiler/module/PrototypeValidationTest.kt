package com.warrior.compiler.module

import com.warrior.compiler.error
import com.warrior.compiler.parsePrototype
import com.warrior.compiler.validation.ErrorType.ARGUMENT_IS_ALREADY_DECLARED
import com.warrior.compiler.validation.ErrorType.ONE_LENGTH_TUPLE
import com.warrior.compiler.validation.Result.Ok
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 25.03.16.
 */
class PrototypeValidationTest {

    @Test
    fun prototypeTest1() {
        val prototype = "f(a: i32, b: i32, c: i32) -> i32"
        Assert.assertEquals(
                Ok,
                parsePrototype(prototype).validate()
        )
    }

    @Test
    fun prototypeTest2() {
        val prototype = "f(a: i32, b: i32, a: i32) -> i32"
        Assert.assertEquals(
                error(ARGUMENT_IS_ALREADY_DECLARED),
                parsePrototype(prototype).validate()
        )
    }

    @Test
    fun prototypeTest3() {
        val prototype = "f(a: i32, a: i32, a: i32) -> i32"
        Assert.assertEquals(
                error(ARGUMENT_IS_ALREADY_DECLARED, ARGUMENT_IS_ALREADY_DECLARED),
                parsePrototype(prototype).validate()
        )
    }

    @Test
    fun prototypeTest4() {
        val prototype = "f(a: i32, b: i32, c: (i32)) -> i32"
        Assert.assertEquals(
                error(ONE_LENGTH_TUPLE),
                parsePrototype(prototype).validate()
        )
    }
}
