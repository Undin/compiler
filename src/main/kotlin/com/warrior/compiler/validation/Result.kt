package com.warrior.compiler.validation

import com.warrior.compiler.Position

/**
 * Created by warrior on 24.03.16.
 */

sealed class Result {
    object Ok : Result() {
        override fun plus(other: Result): Result = other
    }
    class Error(val messages: List<ErrorMessage>) : Result() {

        constructor(message : ErrorMessage): this(listOf(message))

        override fun plus(other: Result): Result {
            return when (other) {
                is Ok -> this
                is Error -> Result.Error(messages + other.messages)
            }
        }
    }

    abstract operator fun plus(other: Result): Result

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Result) {
            return false
        }
        return when (other) {
            is Ok -> this is Ok
            is Error -> this is Error && messages.map { it.error } == other.messages.map { it.error }
            else -> false
        }
    }

    override fun hashCode(): Int = when (this) {
        is Ok -> super.hashCode()
        is Error -> messages.hashCode()
    }

    override fun toString(): String = when (this) {
        is Ok -> "Ok"
        is Error -> "Error($messages)"
    }
}

fun List<Result>.fold(): Result = this.fold(Result.Ok, Result::plus)

data class ErrorMessage(val error: ErrorType, val message: String, val start: Position, val end: Position) {
    override fun toString(): String = "$error. $start-$end $message"
}

enum class ErrorType {
    TYPE_MISMATCH,
    UNDECLARED_VARIABLE,
    VARIABLE_IS_ALREADY_DECLARED,
    UNDECLARED_FUNCTION,
    WRONG_ARGS_NUMBER,
    ARGUMENT_IS_ALREADY_DECLARED,
    RETURN_EXPRESSION,
    FUNCTION_IS_ALREADY_DECLARED,
    UNKNOWN_VARIABLE_TYPE,
    INDEX_OUT_OF_RANGE,
    NON_CONST_EXPRESSION,
    ONE_LENGTH_TUPLE,
    NOT_EXTENSION_FUNCTION
}
