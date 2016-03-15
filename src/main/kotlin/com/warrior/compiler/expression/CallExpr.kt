package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 10.03.16.
 */
class CallExpr(val fnName: String, val args: List<Expr>) : Expr {
    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val fn = LLVM.LLVMGetNamedFunction(module, fnName) ?: throw IllegalStateException("function must be declared")
        val argsValues = args.map { it.generateCode(module, builder, symbolTable) }.toTypedArray()
        return LLVM.LLVMBuildCall(builder, fn, PointerPointer(*argsValues), argsValues.size, "${fnName}Call")
    }
}
