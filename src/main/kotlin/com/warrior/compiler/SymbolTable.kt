package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import java.util.*

/**
 * Created by warrior on 10.03.16.
 */
class SymbolTable {

    val variables: MutableMap<String, VariableAttrs>

    constructor() {
        variables = HashMap()
    }

    constructor(symbolTable: SymbolTable) {
        variables = HashMap(symbolTable.variables)
    }
}

data class VariableAttrs(val name: String, val type: Type, val ref: LLVM.LLVMValueRef)
