package com.warrior.compiler

import com.warrior.compiler.expression.Expr
import com.warrior.compiler.module.Function
import com.warrior.compiler.module.GlobalDeclaration
import com.warrior.compiler.module.Module
import com.warrior.compiler.statement.Statement
import org.junit.Test

/**
 * Created by warrior on 06.04.16.
 */
class TypeTest {

    @Test
    fun test() {
        val program = """
            let a = 5;

            fn main() -> i32 {
                let pow: i32;
                read(pow);
                if (pow < 0) {
                    println(-1);
                    return 0;
                }
                println(fast(a, pow));
                return 0;
            }

            fn fast(a: i32, pow: i32) -> i32 {
                let result = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                return result;
            }
        """
        val module = parseModule(program)
        module.validate()
        module.checkTypes()
    }

    private fun Module.checkTypes(): Unit {
        globals.forEach { it.checkTypes() }
        functions.forEach { it.checkTypes() }
    }

    private fun Function.checkTypes(): Unit = body.checkTypes()
    private fun GlobalDeclaration.checkTypes(): Unit = expr?.checkType() ?: Unit

    private fun Statement.checkTypes(): Unit = when (this) {
        is Statement.Block -> statements.forEach { it.checkTypes() }
        is Statement.ExpressionStatement -> expr.checkType()
        is Statement.Assign -> expr.checkType()
        is Statement.AssignDecl -> expr?.checkType() ?: Unit
        is Statement.If -> {
            condition.checkType()
            thenBlock.checkTypes()
        }
        is Statement.IfElse -> {
            condition.checkType()
            thenBlock.checkTypes()
            elseBlock.checkTypes()
        }
        is Statement.While -> {
            condition.checkType()
            bodyBlock.checkTypes()
        }
        is Statement.Return -> expr.checkType()
        is Statement.Print -> expr.checkType()
        is Statement.Read -> Unit
    }

    private fun Expr.checkType() {
        if (type == Type.Unknown) {
            throw IllegalStateException("Unknown type")
        }
    }
}
