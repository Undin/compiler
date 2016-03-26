package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import java.util.*

/**
 * Created by warrior on 10.03.16.
 */
class SymbolTable<T> {

    private val localVariables: MutableMap<String, T>
    private val globalVariables: MutableMap<String, T>

    constructor() {
        localVariables = HashMap()
        globalVariables = HashMap()
    }

    constructor(symbolTable: SymbolTable<T>) {
        localVariables = HashMap(symbolTable.localVariables)
        globalVariables = HashMap(symbolTable.globalVariables)
    }

    operator fun get(name: String): T? = localVariables[name] ?: globalVariables[name]
    operator fun contains(name: String): Boolean = localVariables.contains(name) || globalVariables.contains(name)

    fun putGlobal(name: String, variable: T) {
        globalVariables[name] = variable
    }

    fun putLocal(name: String, variable: T) {
        localVariables[name] = variable
    }

    fun getGlobal(): Map<String, T> = globalVariables
    fun getLocal(): Map<String, T> = localVariables
}

data class VariableAttrs(val name: String, val type: Type, val ref: LLVM.LLVMValueRef)
