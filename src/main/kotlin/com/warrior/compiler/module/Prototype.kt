package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Type
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.*
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
sealed class Prototype(ctx: ParserRuleContext, val name: String, open val args: List<Arg>, val returnType: Type) : ASTNode(ctx) {

    class SimplePrototype(ctx: ParserRuleContext, name: String, args: List<Arg>, returnType: Type) : Prototype(ctx, name, args, returnType)
    class ExtensionPrototype(ctx: ParserRuleContext, name: String, val extendedType: Type,
                             args: List<Arg>, returnType: Type) : Prototype(ctx, name, args, returnType) {
        override val args: List<Arg> = listOf(Arg("self", extendedType)) + args
    }

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
                    val arg = it.value
                    var result: Result = Ok

                    if (arg.name in variables) {
                        val message = "'${getText()}' ${it.index} arg: argument '${arg.name}' is already declared"
                        result += Error(ErrorMessage(ARGUMENT_IS_ALREADY_DECLARED, message, start(), end()))
                    } else {
                        variables.add(arg.name)
                    }

                    val type = arg.type
                    if (type is Type.Tuple && type.elementsTypes.size == 1) {
                        val message = "'${getText()}': tuples with one elements are not supported"
                        result += Error(ErrorMessage(ONE_LENGTH_TUPLE, message, start(), end()))
                    }

                    result
                }
                .fold()
    }

    data class Arg(val name: String, val type: Type)
}
