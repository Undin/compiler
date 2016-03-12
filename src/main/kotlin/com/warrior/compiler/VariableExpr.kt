package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class VariableExpr(val name: String): Expr {
    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return symbolTable.variables[name]?.value ?: throw IllegalStateException("undeclared variable")
    }
}
