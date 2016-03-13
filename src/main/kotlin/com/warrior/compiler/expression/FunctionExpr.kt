package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.statement.Statement
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class FunctionExpr(val prototype: PrototypeExpr, val statements: List<Statement>, val expr: Expr) : Expr {
    override fun getType(): Type = prototype.getType()

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val fn = prototype.generateCode(module, builder, symbolTable)

        // create basic block
        val entry = LLVM.LLVMAppendBasicBlock(fn, "entry")
        LLVM.LLVMPositionBuilderAtEnd(builder, entry)

        // allocate variables for arguments
        for ((i, arg) in prototype.args.withIndex()) {
            val value = LLVM.LLVMGetParam(fn, i)
            val ref = LLVM.LLVMBuildAlloca(builder, arg.type.toLLVMType(), arg.name)
            LLVM.LLVMBuildStore(builder, value, ref)
            symbolTable.variables[arg.name] = VariableAttrs(arg.name, arg.type, ref)
        }

        // generate code for all statements
        statements.forEach { it.generateCode(module, builder, symbolTable) }

        // create return expression
        val retValue = expr.generateCode(module, builder, symbolTable)
        LLVM.LLVMBuildRet(builder, retValue)

        // verify function
        LLVM.LLVMVerifyFunction(fn, LLVM.LLVMAbortProcessAction)
        return fn
    }
}
