package com.warrior.compiler.validation

import java.util.*

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

    class TupleValue(val elements: MutableList<TypedValue>) : TypedValue() {
        operator fun get(i: Int): TypedValue = elements[i]
        operator fun set(i: Int, value: TypedValue) {
            elements[i] = value
        }
    }

    class ArrayValue(val elements: MutableList<TypedValue>) : TypedValue() {
        constructor(elementsValue: TypedValue, size: Int): this(Collections.nCopies(size, elementsValue))
        operator fun get(i: IntValue): TypedValue = elements[i.value]
        operator fun set(i: IntValue, value: TypedValue) {
            elements[i.value] = value
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is TypedValue) {
            return false
        }
        return when (other) {
            is BoolValue -> this is BoolValue && value == other.value
            is IntValue -> this is IntValue && value == other.value
            is TupleValue -> this is TupleValue && elements == other.elements
            is ArrayValue -> this is ArrayValue && elements == other.elements
            else -> false
        }
    }

    override fun hashCode(): Int = when (this) {
        is BoolValue -> value.hashCode()
        is IntValue -> value.hashCode()
        is TupleValue -> elements.hashCode()
        is ArrayValue -> elements.hashCode()
    }

    override fun toString(): String = when (this) {
        is BoolValue -> value.toString()
        is IntValue -> value.toString()
        is TupleValue -> elements.joinToString(prefix = "(", postfix = ")")
        is ArrayValue -> elements.joinToString(prefix = "[", postfix = "]")
    }
}

class Fn(val body: (List<TypedValue>) -> TypedValue) {
    operator fun invoke(args: List<TypedValue> = emptyList()): TypedValue = body(args)
}
