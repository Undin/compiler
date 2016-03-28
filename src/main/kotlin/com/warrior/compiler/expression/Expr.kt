package com.warrior.compiler.expression

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 07.03.16.
 */
abstract class Expr(ctx: ParserRuleContext) : ASTNode(ctx) {
    abstract fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef
    abstract fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type
    abstract fun validate(functions: Map<String, Type.Fn> = emptyMap(), variables: SymbolTable<Type> = SymbolTable()): Result
    abstract fun calculate(functions: Map<String, Fn> = emptyMap(), variables: Map<String, TypedValue> = emptyMap()): TypedValue
}
