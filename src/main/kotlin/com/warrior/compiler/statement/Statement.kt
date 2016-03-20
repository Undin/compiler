package com.warrior.compiler.statement

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.expression.Expr
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 13.03.16.
 */
interface Statement : ASTNode {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                     symbolTable: SymbolTable, returnBlock: ReturnBlock?): Unit
    fun hasReturnStatement(): Boolean = false
    fun isTerminalStatement(): Boolean = false
}

data class ReturnBlock(val block: LLVMBasicBlockRef, val retValueRef: LLVMValueRef)

class Block(val statements: List<Statement>) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val localSymbolTable = SymbolTable(symbolTable)

        for (st in statements) {
            st.generateCode(module, builder, localSymbolTable, returnBlock)
            if (st.isTerminalStatement()) {
                break;
            }
        }
    }

    override fun hasReturnStatement(): Boolean = statements.any { it.hasReturnStatement() }
    override fun isTerminalStatement(): Boolean = statements.any { it.isTerminalStatement() }
}

class ExpressionStatement(val expr: Expr) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        expr.generateCode(module, builder, symbolTable)
    }
}

class Assign(val name: String, val expr: Expr) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val v = symbolTable.variables[name] ?: throw IllegalStateException("variable '$name' is not declared");
        val value = expr.generateCode(module, builder, symbolTable)
        LLVMBuildStore(builder, value, v.ref)
    }
}

class AssignDecl(val name: String, val type: Type, val expr: Expr?) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val value = condition.generateCode(module, builder, symbolTable)
        val fn = getCurrentFunction(builder)

        val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
        val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

        // create conditional jump
        LLVMBuildCondBr(builder, value, thenBasicBlock, mergeBasicBlock)

        // generate code for 'then' block
        LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
        thenBlock.generateCode(module, builder, symbolTable, returnBlock)

        // if 'then' block hasn't terminal statement
        if (!thenBlock.isTerminalStatement()) {
            // create unconditional jump to 'merge' block
            LLVMBuildBr(builder, mergeBasicBlock)
        }

        // move builder position to 'merge' block
        LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
    }

    override fun hasReturnStatement(): Boolean = thenBlock.hasReturnStatement()
}

class IfElse(val condition: Expr, val thenBlock: Block, val elseBlock: Block) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val value = condition.generateCode(module, builder, symbolTable)
        val fn = getCurrentFunction(builder)

        val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
        val elseBasicBlock = LLVMAppendBasicBlock(fn, "if_false")
        val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

        // create conditional jump
        LLVMBuildCondBr(builder, value, thenBasicBlock, elseBasicBlock)

        // generate code for 'then' block
        LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
        thenBlock.generateCode(module, builder, symbolTable, returnBlock)

        var removeMergeBlock = true
        // if 'then' block isn't terminal statement
        if (!thenBlock.isTerminalStatement()) {
            removeMergeBlock = false
            // create unconditional jump to 'merge' block
            LLVMBuildBr(builder, mergeBasicBlock)
        }

        // generate code for 'else' block
        LLVMPositionBuilderAtEnd(builder, elseBasicBlock)
        elseBlock.generateCode(module, builder, symbolTable, returnBlock)

        // if 'else' block isn't terminal statement
        if (!elseBlock.isTerminalStatement()) {
            removeMergeBlock = false
            // create unconditional jump to 'merge' block
            LLVMBuildBr(builder, mergeBasicBlock)
        }

        if (removeMergeBlock) {
            // remove 'merge' block because it is unreachable
            LLVMRemoveBasicBlockFromParent(mergeBasicBlock)
        } else {
            // move builder position to 'merge' block
            LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
        }
    }

    override fun hasReturnStatement(): Boolean = thenBlock.hasReturnStatement() || elseBlock.hasReturnStatement()
    override fun isTerminalStatement(): Boolean = thenBlock.isTerminalStatement() && elseBlock.isTerminalStatement()
}

class While(val condition: Expr, val bodyBlock: Block) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
        bodyBlock.generateCode(module, builder, symbolTable, returnBlock)

        // if 'body' block isn't terminal statement
        if (!bodyBlock.isTerminalStatement()) {
            // create unconditional jump to 'condition' block
            LLVMBuildBr(builder, condBasicBlock)
        }

        // move builder position to 'next' block
        LLVMPositionBuilderAtEnd(builder, nextBasicBlock)
    }

    override fun hasReturnStatement(): Boolean = bodyBlock.hasReturnStatement()
}

private fun getCurrentFunction(builder: LLVMBuilderRef): LLVMValueRef {
    val currentBlock = LLVMGetInsertBlock(builder)
    return LLVMGetBasicBlockParent(currentBlock)
}

class Return(val expr: Expr) : Statement {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val value = expr.generateCode(module, builder, symbolTable)
        if (returnBlock != null) {
            LLVMBuildStore(builder, value, returnBlock.retValueRef)
            LLVMBuildBr(builder, returnBlock.block)
        } else {
            LLVMBuildRet(builder, value)
        }
    }

    override fun hasReturnStatement(): Boolean = true
    override fun isTerminalStatement(): Boolean = true
}

class Print(val expr: Expr, val newLine: Boolean) : Statement {

    companion object {
        private var str: LLVMValueRef? = null
        private var strLn: LLVMValueRef? = null

        private fun getStr(builder: LLVMBuilderRef): LLVMValueRef {
            if (str == null) {
                str = LLVMBuildGlobalStringPtr(builder, "%d", "str")
            }
            return str!!
        }

        private fun getStrLn(builder: LLVMBuilderRef): LLVMValueRef {
            if (strLn == null) {
                strLn = LLVMBuildGlobalStringPtr(builder, "%d\n", "strLn")
            }
            return strLn!!
        }
    }

    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
        val s = if (newLine) getStrLn(builder) else getStr(builder)
        val value = expr.generateCode(module, builder, symbolTable)
        val printfFn = LLVMGetNamedFunction(module, "printf")
        val args = arrayOf(s, value)
        LLVMBuildCall(builder, printfFn, PointerPointer(*args), 2, "writeCall")
    }
}
