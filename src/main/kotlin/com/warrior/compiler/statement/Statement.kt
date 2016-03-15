package com.warrior.compiler.statement

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.expression.Expr
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 13.03.16.
 */
interface Statement : ASTNode {
    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): Unit
}

class Block(val statements: List<Statement>) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable) {
        statements.forEach { it.generateCode(module, builder, symbolTable) }
    }
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
        LLVMBuildStore(builder, value, v.ref)
    }
}

class AssignDecl(val name: String, val type: Type, val expr: Expr?) : Statement {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable) {
        if (name in symbolTable.variables) {
            throw IllegalStateException("$name is already declared")
        }
        val ref = LLVMBuildAlloca(builder, type.toLLVMType(), name)
        symbolTable.variables[name] = VariableAttrs(name, type, ref)
        if (expr != null) {
            val value = expr.generateCode(module, builder, symbolTable)
            LLVMBuildStore(builder, value, ref)
        }
    }
}

class If(val condition: Expr, val thenBlock: Block) : Statement {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable) {
        val value = condition.generateCode(module, builder, symbolTable)
        val fn = getCurrentFunction(builder)

        val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
        val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

        // create conditional jump
        LLVMBuildCondBr(builder, value, thenBasicBlock, mergeBasicBlock)

        // generate code for 'then' block
        LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
        thenBlock.generateCode(module, builder, symbolTable)
        // create unconditional jump to 'merge' block
        LLVMBuildBr(builder, mergeBasicBlock)

        // move builder position to 'merge' block
        LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
    }
}

class IfElse(val condition: Expr, val thenBlock: Block, val elseBlock: Block) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable) {
        val value = condition.generateCode(module, builder, symbolTable)
        val fn = getCurrentFunction(builder)

        val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
        val elseBasicBlock = LLVMAppendBasicBlock(fn, "if_false")
        val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

        // create conditional jump
        LLVMBuildCondBr(builder, value, thenBasicBlock, elseBasicBlock)

        // generate code for 'then' block
        LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
        thenBlock.generateCode(module, builder, symbolTable)
        // create unconditional jump to 'merge' block
        LLVMBuildBr(builder, mergeBasicBlock)

        // generate code for 'else' block
        LLVMPositionBuilderAtEnd(builder, elseBasicBlock)
        elseBlock.generateCode(module, builder, symbolTable)
        // create unconditional jump to 'merge' block
        LLVMBuildBr(builder, mergeBasicBlock)

        // move builder position to 'merge' block
        LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
    }
}

class While(val condition: Expr, val bodyBlock: Block) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable) {
        val fn = getCurrentFunction(builder)

        val condBasicBlock = LLVMAppendBasicBlock(fn, "while_cond")
        val bodyBasicBlock = LLVMAppendBasicBlock(fn, "while_body")
        val nextBasicBlock = LLVMAppendBasicBlock(fn, "while_next")

        // create unconditional jump to 'condition' block
        LLVMBuildBr(builder, condBasicBlock)

        // generate code for 'condition' block
        LLVMPositionBuilderAtEnd(builder, condBasicBlock)
        val value = condition.generateCode(module, builder, symbolTable)
        // create conditional jump
        LLVMBuildCondBr(builder, value, bodyBasicBlock, nextBasicBlock)

        // generate code for 'body' block
        LLVMPositionBuilderAtEnd(builder, bodyBasicBlock)
        bodyBlock.generateCode(module, builder, symbolTable)
        // create unconditional jump to 'condition' block
        LLVMBuildBr(builder, condBasicBlock)

        // move builder position to 'next' block
        LLVMPositionBuilderAtEnd(builder, nextBasicBlock)
    }
}

private fun getCurrentFunction(builder: LLVMBuilderRef): LLVMValueRef {
    val currentBlock = LLVMGetInsertBlock(builder)
    return LLVMGetBasicBlockParent(currentBlock)
}
