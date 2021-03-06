package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
import com.warrior.compiler.statement.Info
import com.warrior.compiler.statement.ReturnBlockInfo
import com.warrior.compiler.statement.Statement
import com.warrior.compiler.statement.Statement.Block
import com.warrior.compiler.statement.Statement.Return
import com.warrior.compiler.statement.TailRecInfo
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.RETURN_EXPRESSION
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import java.util.*

/**
 * Created by warrior on 10.03.16.
 */
class Function(ctx: ParserRuleContext, val prototype: Prototype, val body: Block) : ASTNode(ctx) {

    private val RETURN_VAR_NAME = ".return"

    var accDeclaration: Statement.AssignDecl? = null

    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                     symbolTable: SymbolTable<LLVMValueRef>, eliminateTailRec: Boolean = false) {
        val fn = LLVMGetNamedFunction(module, prototype.name) ?: throw IllegalStateException("Function is not declared")

        // create basic block
        val entry = LLVMAppendBasicBlock(fn, "entry")
        LLVMPositionBuilderAtEnd(builder, entry)

        if (!body.isTerminalStatement()) {
            throw IllegalStateException("Function ${prototype.name} may not call 'return'")
        }

        val lastStatement = body.statements.last()
        var returnBlockInfo: ReturnBlockInfo? = null
        if (lastStatement !is Return || body.statements.count { it.hasReturnStatement() } > 1) {
            val returnBasicBlock = LLVMAppendBasicBlock(fn, "return")
            val returnValueRef = if (prototype.returnType.isPrimitive()) {
                LLVMBuildAlloca(builder, prototype.returnType.toLLVMType(), RETURN_VAR_NAME)
            } else {
                LLVMGetParam(fn, prototype.args.size)
            }
            returnBlockInfo = ReturnBlockInfo(returnBasicBlock, returnValueRef)
        } else if (lastStatement is Return) {
            lastStatement.returnValuePtr = LLVMGetParam(fn, prototype.args.size)
        }

        val localSymbolTable = SymbolTable(symbolTable)
        val argsPointers = ArrayList<LLVMValueRef>(prototype.args.size)
        // allocate variables for arguments
        for ((i, arg) in prototype.args.withIndex()) {
            val value = LLVMGetParam(fn, i)
            val ptr = LLVMBuildAlloca(builder, arg.type.toLLVMType(), arg.name)
            argsPointers.add(ptr)
            LLVMBuildStore(builder, value, ptr)
            localSymbolTable.putLocal(arg.name, ptr)
        }

        var tailRecInfo: TailRecInfo? = null
        if (eliminateTailRec) {
            // allocate accumulator variable if it needs to eliminate tail recursion
            accDeclaration?.generateCode(module, builder, localSymbolTable, Info())
            // create basic block to transform tail recursion to loop
            val tailRecBlock = LLVMAppendBasicBlock(fn, "tail_rec_block")
            LLVMBuildBr(builder, tailRecBlock)
            LLVMPositionBuilderAtEnd(builder, tailRecBlock)
            tailRecInfo = TailRecInfo(prototype.name, argsPointers, tailRecBlock)
        }

        // generate code for 'body' block
        body.generateCode(module, builder, localSymbolTable, Info(returnBlockInfo, tailRecInfo))

        if (returnBlockInfo != null) {
            val lastBlock = LLVMGetLastBasicBlock(fn)
            LLVMMoveBasicBlockAfter(returnBlockInfo.block, lastBlock)

            LLVMPositionBuilderAtEnd(builder, returnBlockInfo.block)

            if (prototype.returnType.isPrimitive()) {
                val returnValue = LLVMBuildLoad(builder, returnBlockInfo.retValueRef, RETURN_VAR_NAME)
                LLVMBuildRet(builder, returnValue)
            } else {
                LLVMBuildRetVoid(builder)
            }
        }

        // verify function
        LLVMVerifyFunction(fn, LLVMPrintMessageAction)
    }

    fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type> = SymbolTable()): Result {
        val prototypeResult = prototype.validate()
        val localVariables = SymbolTable(variables)
        prototype.args.forEach {
            var type = it.type
            if (type is Tuple && type.elementsTypes.size == 1) {
                type = Unknown
            }
            localVariables.putLocal(it.name, type)
        }
        val bodyResult = body.validate(functions, localVariables, prototype.name)
        if (!body.isTerminalStatement()) {
            val message = "function '${prototype.name}' may not return result"
            val error = Error(ErrorMessage(RETURN_EXPRESSION, message, start(), end()))
            return bodyResult + error
        }
        return prototypeResult + bodyResult
    }

    fun getType(): Type.Fn = Fn(prototype.args.map { it.type }, prototype.returnType, prototype is Prototype.ExtensionPrototype)
}
