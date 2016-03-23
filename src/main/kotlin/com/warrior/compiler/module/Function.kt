package com.warrior.compiler.module

import com.warrior.compiler.*
import com.warrior.compiler.statement.Statement.Block
import com.warrior.compiler.statement.Statement.Return
import com.warrior.compiler.statement.ReturnBlock
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 10.03.16.
 */
class Function(ctx: ParserRuleContext, val prototype: Prototype, val body: Block) : ASTNode(ctx) {
    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef) {
        val fn = LLVMGetNamedFunction(module, prototype.name) ?: throw IllegalStateException("Function is not declared")

        // create basic block
        val entry = LLVMAppendBasicBlock(fn, "entry")
        LLVMPositionBuilderAtEnd(builder, entry)

        if (!body.isTerminalStatement()) {
            throw IllegalStateException("Function ${prototype.name} may not call 'return'")
        }

        var returnBlock: ReturnBlock? = null
        if (body.statements.last() !is Return || body.statements.count { it.hasReturnStatement() } > 1) {
            val returnBasicBlock = LLVMAppendBasicBlock(fn, "return")
            val returnValueRef = LLVMBuildAlloca(builder, prototype.returnType.toLLVMType(), "_return")
            returnBlock = ReturnBlock(returnBasicBlock, returnValueRef)
        }

        val symbolTable = SymbolTable()
        // allocate variables for arguments
        for ((i, arg) in prototype.args.withIndex()) {
            val value = LLVMGetParam(fn, i)
            val ref = LLVMBuildAlloca(builder, arg.type.toLLVMType(), arg.name)
            LLVMBuildStore(builder, value, ref)
            symbolTable.variables[arg.name] = VariableAttrs(arg.name, arg.type, ref)
        }

        // generate code for 'body' block
        body.generateCode(module, builder, symbolTable, returnBlock)

        if (returnBlock != null) {
            val lastBlock = LLVMGetLastBasicBlock(fn)
            LLVMMoveBasicBlockAfter(returnBlock.block, lastBlock)

            LLVMPositionBuilderAtEnd(builder, returnBlock.block)
            val returnValue = LLVMBuildLoad(builder, returnBlock.retValueRef, "_return")
            LLVMBuildRet(builder, returnValue)
        }

        // verify function
        LLVMVerifyFunction(fn, LLVMPrintMessageAction)
    }
}
