package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.TYPE_MISMATCH
import com.warrior.compiler.validation.ErrorType.VARIABLE_IS_ALREADY_DECLARED
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 26.03.16.
 */
class GlobalDeclaration(ctx: ParserRuleContext, val name: String, val type: Type, val expr: Expr?) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<VariableAttrs>) {
        val globalVarRef = LLVMAddGlobal(module, type.toLLVMType(), name)
        val value = if (expr != null) {
            expr.generateCode(module, builder, symbolTable)
        } else {
            LLVMConstInt(type.toLLVMType(), 0L, 1)
        }
        LLVMSetInitializer(globalVarRef, value)
        symbolTable.putGlobal(name, VariableAttrs(name, type, globalVarRef))
    }

    fun validate(symbolTable: SymbolTable<Type> = SymbolTable()): Result {
        var result: Result = Ok
        if (name in symbolTable.getGlobal()) {
            val message = "'${getText()}': global variable '$name' is already declared"
            result = Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
        }
        var exprResult: Result = Ok
        if (expr != null) {
            val exprType = expr.getType(emptyMap(), symbolTable)
            if (!exprType.match(type)) {
                val message = "'${getText()}': variable and expression types don't match"
                exprResult = Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            }
        }
        if (result == Ok) {
            symbolTable.putGlobal(name, type)
        }
        return result + exprResult
    }
}
