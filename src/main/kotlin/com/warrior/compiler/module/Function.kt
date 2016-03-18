package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.statement.Block
import com.warrior.compiler.statement.Return
import com.warrior.compiler.statement.ReturnBlock
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class Function(val prototype: Prototype, val body: Block) : ASTNode {
    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef) {
        val fn = LLVM.LLVMGetNamedFunction(module, prototype.name) ?: throw IllegalStateException("Function is not declared")

        // create basic block
        val entry = LLVM.LLVMAppendBasicBlock(fn, "entry")
        LLVM.LLVMPositionBuilderAtEnd(builder, entry)

        if (!body.isTerminalStatement()) {
            throw IllegalStateException("Function ${prototype.name} may not call 'return'")
        }
        var returnBlock: ReturnBlock? = null
        if (body.statements.last() !is Return || body.statements.count { it.hasReturnStatement() } > 1) {
            val returnBasicBlock = LLVM.LLVMAppendBasicBlock(fn, "return")
            val returnValueRef = LLVM.LLVMBuildAlloca(builder, prototype.returnType.toLLVMType(), "_return")
            returnBlock = ReturnBlock(returnBasicBlock, returnValueRef)
        }

        val symbolTable = SymbolTable()
        // allocate variables for arguments
        for ((i, arg) in prototype.args.withIndex()) {
            val value = LLVM.LLVMGetParam(fn, i)
            val ref = LLVM.LLVMBuildAlloca(builder, arg.type.toLLVMType(), arg.name)
            LLVM.LLVMBuildStore(builder, value, ref)
            symbolTable.variables[arg.name] = VariableAttrs(arg.name, arg.type, ref)
        }

        // generate code for 'body' block
        body.generateCode(module, builder, symbolTable, returnBlock)

        if (returnBlock != null) {
            val lastBlock = LLVM.LLVMGetLastBasicBlock(fn)
            LLVM.LLVMMoveBasicBlockAfter(returnBlock.block, lastBlock)

            LLVM.LLVMPositionBuilderAtEnd(builder, returnBlock.block)
            val returnValue = LLVM.LLVMBuildLoad(builder, returnBlock.retValueRef, "_return")
            LLVM.LLVMBuildRet(builder, returnValue)
        }

        // verify function
        LLVM.LLVMVerifyFunction(fn, LLVM.LLVMAbortProcessAction)
    }
}
