package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.FUNCTION_IS_ALREADY_DECLARED
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import com.warrior.compiler.validation.fold
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import java.util.*

/**
 * Created by warrior on 19.03.16.
 */
class Module(ctx: ParserRuleContext, val globals: List<GlobalDeclaration>, val functions: List<Function>) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef) {
        val symbolTable = SymbolTable<VariableAttrs>()
        globals.forEach { it.generateCode(module, builder, symbolTable) }
        functions.forEach { it.prototype.generateCode(module) }
        declarePrintf(module)
        declareScanf(module)
        functions.forEach { it.generateCode(module, builder, symbolTable) }
    }

    private fun declarePrintf(module: LLVMModuleRef) {
        val i8Pointer = LLVMPointerType(LLVMInt8Type(), 0)
        val fnType = LLVMFunctionType(LLVMInt32Type(), i8Pointer, 1, 1)
        val fn = LLVMAddFunction(module, "printf", fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }

    private fun declareScanf(module: LLVMModuleRef) {
        val i8Pointer = LLVMPointerType(LLVMInt8Type(), 0)
        val fnType = LLVMFunctionType(LLVMInt32Type(), i8Pointer, 1, 1)
        val fn = LLVMAddFunction(module, "scanf", fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }

    fun validate(): Result {
        val symbolTable = SymbolTable<Type>()
        val globalResult = globals
            .map { it.validate(symbolTable) }
            .fold()

        val functionMap = HashMap<String, Type.Fn>()
        val prototypeResult = functions.map {
            val proto = it.prototype
            if (proto.name in functionMap) {
                val message = "'${proto.getText()}': function '${proto.name}' is already declared"
                Error(ErrorMessage(FUNCTION_IS_ALREADY_DECLARED, message, proto.start(), proto.end()))
            } else {
                functionMap[proto.name] = it.getType()
                Ok
            }
        }
        .fold()
        val functionsResult = functions
                .map { it.validate(functionMap, symbolTable) }
                .fold()
        return globalResult + prototypeResult + functionsResult
    }
}
