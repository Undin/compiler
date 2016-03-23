package com.warrior.compiler.validation

/**
 * Created by warrior on 24.03.16.
 */
sealed class TypedValue {
    class BoolValue(val value: Boolean) : TypedValue()

    class IntValue(val value: Int) : TypedValue(), Comparable<IntValue> {
        operator fun plus(other: IntValue): IntValue = IntValue(value + other.value)
        operator fun minus(other: IntValue): IntValue = IntValue(value - other.value)
        operator fun times(other: IntValue): IntValue = IntValue(value * other.value)
        operator fun div(other: IntValue): IntValue = IntValue(value / other.value)
        operator fun mod(other: IntValue): IntValue = IntValue(value % other.value)

        override operator fun compareTo(other: IntValue): Int = value.compareTo(other.value)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is TypedValue) {
            return false
        }
        return when (other) {
            is BoolValue -> this is BoolValue && value == other.value
            is IntValue -> this is IntValue && value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int = when (this) {
        is BoolValue -> value.hashCode()
        is IntValue -> value.hashCode()
    }

    override fun toString(): String = when (this) {
        is BoolValue -> value.toString()
        is IntValue -> value.toString()
    }
}

class Fn(val body: (List<TypedValue>) -> TypedValue) {
    operator fun invoke(args: List<TypedValue> = emptyList()): TypedValue = body(args)
}
