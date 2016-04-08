package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 26.03.16.
 */
class GlobalDeclaration(ctx: ParserRuleContext, val name: String, val type: Type?, val expr: Expr?) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>) {
        val value: LLVMValueRef
        if (type == null) {
            if (expr == null) {
                throw IllegalArgumentException("can't determine type of '$name' variable")
            }
            value = expr.generateCode(module, builder, symbolTable)
        } else {
            value = expr?.generateCode(module, builder, symbolTable) ?: LLVMConstInt(type.toLLVMType(), 0L, 1)
        }

        val globalVarRef = LLVMAddGlobal(module, LLVMTypeOf(value), name)
        LLVMSetInitializer(globalVarRef, value)
        symbolTable.putGlobal(name, globalVarRef)
    }

    fun validate(variables: SymbolTable<Type> = SymbolTable()): Result {
        if (name in variables.getGlobal()) {
            val message = "'${getText()}': global variable '$name' is already declared"
            return Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
        }
        val exprType: Type
        val variableType: Type
        if (type == null){
            if (expr == null) {
                variables.putGlobal(name, Type.Unknown)
                val message = "'${getText()}': can't determine type of variable '$name'"
                return Error(ErrorMessage(UNKNOWN_VARIABLE_TYPE, message, start(), end()))
            }
            exprType = expr.determineType(emptyMap(), variables)
            variableType = exprType
        } else {
            variableType = type
            exprType = expr?.determineType(emptyMap(), variables) ?: type
        }
        variables.putGlobal(name, variableType)

        val result = if (!variableType.match(exprType)) {
            val message = "'${getText()}': variable and expression types don't match"
             Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
        } else {
            Ok
        }
        return result
    }
}
