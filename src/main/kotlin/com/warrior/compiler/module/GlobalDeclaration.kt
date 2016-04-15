package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 26.03.16.
 */
class GlobalDeclaration(ctx: ParserRuleContext, val name: String, val type: Type?, val expr: Expr) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>) {
        val value = expr.generateConstValue()
        val globalVarRef = LLVMAddGlobal(module, expr.type.toLLVMType(), name)
        LLVMSetInitializer(globalVarRef, value)
        symbolTable.putGlobal(name, globalVarRef)
    }

    fun validate(variables: SymbolTable<Type> = SymbolTable()): Result {
        var result = expr.validate(variables = variables)

        if (!expr.isConstant()) {
            val message = "'${getText()}': '${expr.getText()} must be constant"
            result += Error(ErrorMessage(NON_CONST_EXPRESSION, message, expr.start(), expr.end()))
        }

        if (name in variables.getGlobal()) {
            val message = "'${getText()}': global variable '$name' is already declared"
            result += Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
        }

        val exprType = expr.determineType(emptyMap(), variables)

        val variableType = if (type is Type.Tuple && type.elementsTypes.size == 1) {
            val message = "'${getText()}': tuples with one elements are not supported"
            result += Error(ErrorMessage(ONE_LENGTH_TUPLE, message, start(), end()))
            Type.Unknown
        } else {
            type ?: exprType
        }

        if (!variableType.match(exprType)) {
            val message = "'${getText()}': variable and expression types don't match"
            result += Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
        }

        if (name !in variables.getGlobal()) {
            variables.putGlobal(name, variableType)
        }

        return result
    }
}
