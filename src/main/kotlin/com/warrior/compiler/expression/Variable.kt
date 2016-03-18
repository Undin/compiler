package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.TypedValue
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class Variable(val name: String): Expr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val ref = symbolTable.variables[name]?.ref ?: throw IllegalStateException("variable '$name' is not declared")
        return LLVM.LLVMBuildLoad(builder, ref, name)
    }

    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun calculate(env: Map<String, TypedValue>): TypedValue {
        return env[name] ?: throw IllegalStateException("variable '$name' isn't declared")
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Variable) {
            return false
        }
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name
}
