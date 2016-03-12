package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 09.03.16.
 */
class PrototypeExpr(val name: String, val args: List<Arg>, val returnType: Type) : Expr {
    data class Arg(val name: String, val type: Type)

    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val argsTypes = args.map { it.type.getLLVMType() }.toTypedArray()
        val fnType = LLVM.LLVMFunctionType(returnType.getLLVMType(), PointerPointer(*argsTypes), argsTypes.size, 0)
        val fn = LLVM.LLVMAddFunction(module, name, fnType)
        LLVM.LLVMSetFunctionCallConv(fn, LLVM.LLVMCCallConv)
        for ((i, arg) in args.withIndex()) {
            val value = LLVM.LLVMGetParam(fn, i)
            LLVM.LLVMSetValueName(value, arg.name)
            symbolTable.variables[arg.name] = VariableAttrs(arg.name, arg.type, value)
        }
        return fn
    }
}
