package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Type
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.ARGUMENT_IS_ALREADY_DECLARED
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.*
import com.warrior.compiler.validation.fold
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer
import java.util.*

/**
 * Created by warrior on 09.03.16.
 */
class Prototype(ctx: ParserRuleContext, val name: String, val args: List<Arg>, val returnType: Type) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef) {
        var argsTypes = args.map { it.type.toLLVMType() }.toTypedArray()
        val fnType = if (!returnType.isPrimitive()) {
            val retType = LLVMPointerType(returnType.toLLVMType(), 0);
            argsTypes += retType
            LLVMFunctionType(LLVMVoidType(), PointerPointer(*argsTypes), argsTypes.size, 0)
        } else {
            LLVMFunctionType(returnType.toLLVMType(), PointerPointer(*argsTypes), argsTypes.size, 0)
        }
        val fn = LLVMAddFunction(module, name, fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }

    fun validate(): Result {
        val variables = HashSet<String>()
        return args.withIndex()
            .map {
                if (it.value.name in variables) {
                    val message = "'${getText()}' ${it.index} arg: argument '${it.value.name}' is already declared"
                    Error(ErrorMessage(ARGUMENT_IS_ALREADY_DECLARED, message, start(), end()))
                } else {
                    variables.add(it.value.name)
                    Ok
                }
            }
            .fold()
    }

    data class Arg(val name: String, val type: Type)
}
