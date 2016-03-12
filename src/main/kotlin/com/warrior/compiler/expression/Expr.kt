package com.warrior.compiler.expression

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Type
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 07.03.16.
 */
interface Expr : ASTNode<LLVM.LLVMValueRef> {
    fun getType(): Type
}

interface BoolExpr : Expr {
    override fun getType(): Type = Type.BOOL
}

interface IntExpr : Expr {
    override fun getType(): Type = Type.I32
}
