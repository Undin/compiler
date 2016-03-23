package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Type
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 09.03.16.
 */
class Prototype(ctx: ParserRuleContext, val name: String, val args: List<Arg>, val returnType: Type) : ASTNode(ctx) {
    fun generateCode(module: LLVM.LLVMModuleRef) {
        val argsTypes = args.map { it.type.toLLVMType() }.toTypedArray()
        val fnType = LLVM.LLVMFunctionType(returnType.toLLVMType(), PointerPointer(*argsTypes), argsTypes.size, 0)
        val fn = LLVM.LLVMAddFunction(module, name, fnType)
        LLVM.LLVMSetFunctionCallConv(fn, LLVM.LLVMCCallConv)
    }

    data class Arg(val name: String, val type: Type)
}
