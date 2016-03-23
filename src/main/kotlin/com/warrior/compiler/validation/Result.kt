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

data class ErrorMessage(val error: ErrorType, val message: String, val start: Position, val end: Position)

enum class ErrorType {
    TYPE_MISMATCH,
    UNDECLARED_VARIABLE,
    UNDECLARED_FUNCTION,
    UNEXPECTED_ARGS_NUMBER
}
