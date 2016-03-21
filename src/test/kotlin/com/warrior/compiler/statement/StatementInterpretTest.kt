package com.warrior.compiler.statement

import com.warrior.compiler.ASTVisitor
import com.warrior.compiler.GrammarLexer
import com.warrior.compiler.GrammarParser
import com.warrior.compiler.TypedValue
import com.warrior.compiler.TypedValue.*
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Assert
import org.junit.Test

/**
 * Created by warrior on 21.03.16.
 */
class StatementInterpretTest {

    @Test
    fun expressionTest() {
        val a = IntValue(1)
        val env = mutableMapOf<String, TypedValue>("a" to a)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("1 + a;").interpret(env = env)
        )
    }

    @Test
    fun assignTest() {
        val a = IntValue(1)
        val b = IntValue(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a = b + 4;").interpret(env)
        )
    }

    @Test
    fun assignDeclTest() {
        val b = IntValue(2)
        val env = mutableMapOf<String, TypedValue>("b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("a: i32 = b + 4;").interpret(env)
        )
    }


    @Test
    fun printTest() {
        val a = IntValue(1)
        val b = IntValue(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf(a),
                parseStatement("print(a);").interpret(env)
        )
    }

    @Test
    fun readTest() {
        val a = IntValue(1)
        val b = IntValue(2)
        val env = mutableMapOf<String, TypedValue>("a" to a, "b" to b)
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement("read(a);").interpret(env, input = mutableListOf(IntValue(4)))
        )
    }

    @Test
    fun blockTest() {
        val statement = """
            {
                a: i32;
                b: i32;
                read(a);
                read(b);
                print(a + b);
            }
        """
        val input = mutableListOf<TypedValue>(IntValue(-1), IntValue(11));
        Assert.assertEquals(
                listOf(IntValue(10)),
                parseStatement(statement).interpret(input = input)
        )
    }

    @Test
    fun ifTest() {
        val statement = """
            if (a > 10) {
                print(a);
            }
        """
        Assert.assertEquals(
                listOf<TypedValue>(),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to IntValue(0)))
        )
        Assert.assertEquals(
                listOf(IntValue(12)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to IntValue(12)))
        )
    }

    @Test
    fun ifElseTest() {
        val statement = """
            if (a > 0) {
                print(a);
            } else {
                print(-a);
            }
        """
        Assert.assertEquals(
                listOf(IntValue(1)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to IntValue(1)))
        )
        Assert.assertEquals(
                listOf(IntValue(1)),
                parseStatement(statement).interpret(mutableMapOf<String, TypedValue>("a" to IntValue(1)))
        )
    }

    @Test
    fun whileTest() {
        val a = IntValue(13)
        val env = mutableMapOf<String, TypedValue>("a" to a)
        val statement = """
            while (a > 10) {
                print(a);
                a = a - 1;
            }
        """
        Assert.assertEquals(
                listOf(IntValue(13), IntValue(12), IntValue(11)),
                parseStatement(statement).interpret(env)
        )
    }

    @Test
    fun test() {
        val statement = """
            {
                a: i32;
                pow: i32;
                read(a);
                read(pow);
                result: i32 = 1;
                while (pow != 0) {
                    if (pow % 2 == 0) {
                        a = a * a;
                        pow = pow / 2;
                    } else {
                        result = result * a;
                        pow = pow - 1;
                    }
                }
                print(result);
            }
        """
        val input = mutableListOf<TypedValue>(IntValue(2), IntValue(20))
        Assert.assertEquals(
                listOf(IntValue(1 shl 20)),
                parseStatement(statement).interpret(input = input)
        )
    }

}

fun parseStatement(statement: String): Statement {
    val stream = ANTLRInputStream(statement);
    val lexer = GrammarLexer(stream);
    val tokens = CommonTokenStream(lexer);
    val parser = GrammarParser(tokens);
    val tree = parser.statement();
    val visitor = ASTVisitor()
    return visitor.visitStatement(tree)
}