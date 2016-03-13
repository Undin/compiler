package com.warrior.compiler.statement

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.expression.Expr
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 13.03.16.
 */
interface Statement : ASTNode {
    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): Unit
}

class ExpressionStatement(val expr: Expr) : Statement {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable) {
        expr.generateCode(module, builder, symbolTable)
    }
}

class Assign(val name: String, val expr: Expr) : Statement {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable) {
        val v = symbolTable.variables[name] ?: throw IllegalStateException("variable name is not declared");
        val value = expr.generateCode(module, builder, symbolTable)
        LLVM.LLVMBuildStore(builder, value, v.ref)
    }
}

class AssignDecl(val name: String, val type: Type, val expr: Expr?) : Statement {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable) {
        if (name in symbolTable.variables) {
            throw IllegalStateException("$name is already declared")
        }
        val ref = LLVM.LLVMBuildAlloca(builder, type.toLLVMType(), name)
        symbolTable.variables[name] = VariableAttrs(name, type, ref)
        if (expr != null) {
            val value = expr.generateCode(module, builder, symbolTable)
            LLVM.LLVMBuildStore(builder, value, ref)
        }
    }
}
