package com.warrior.compiler.expression

import com.warrior.compiler.*
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 07.03.16.
 */
interface Expr : ASTNode {
    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef
    fun getType(): Type
    fun calculate(env: Map<String, TypedValue> = emptyMap(), functions: Map<String, Fn> = emptyMap()): TypedValue
}

interface BoolExpr : Expr {
    override fun getType(): Type = Type.BOOL
    override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.BoolValue
}

interface IntExpr : Expr {
    override fun getType(): Type = Type.I32
    override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.IntValue
}
