package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class VariableExpr(val name: String): Expr {
    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val ref = symbolTable.variables[name]?.ref ?: throw IllegalStateException("undeclared variable")
        return LLVM.LLVMBuildLoad(builder, ref, name)
    }
}
