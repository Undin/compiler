package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.TypedValue
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 10.03.16.
 */
class Call(val fnName: String, val args: List<Expr>) : Expr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val fn = LLVM.LLVMGetNamedFunction(module, fnName) ?: throw IllegalStateException("function must be declared")
        val argsValues = args.map { it.generateCode(module, builder, symbolTable) }.toTypedArray()
        return LLVM.LLVMBuildCall(builder, fn, PointerPointer(*argsValues), argsValues.size, "${fnName}Call")
    }

    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun calculate(env: Map<String, TypedValue>): TypedValue {
        throw UnsupportedOperationException()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Call) {
            return false
        }
        return fnName == other.fnName && args == other.args
    }

    override fun hashCode(): Int{
        var result = fnName.hashCode()
        result += 31 * result + args.hashCode()
        return result
    }

    override fun toString(): String{
        return "$fnName(${args.joinToString()})"
    }
}
