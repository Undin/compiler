package com.warrior.compiler.statement

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.Fn
import com.warrior.compiler.Type.I32
import com.warrior.compiler.checkTypes
import com.warrior.compiler.parseStatement
import org.junit.Test

/**
 * Created by warrior on 08.04.16.
 */
class StatementTypeTest {

    @Test
    fun expressionTest() {
        val statement = "a;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun returnTest() {
        val statement = "return a;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun printTest() {
        val statement = "print(a);"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun assignTest() {
        val statement = "b = a + 1;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
            putLocal("b", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun assignDeclTest() {
        val statement = "let b = a + 1;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun blockTest() {
        val statement = """
        {
            let a = 5;
            let b = 10;
            let c = a * a + b * b;
        }
        """
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        st.validate(functions, fnName = "main")
        st.checkTypes()
    }

    @Test
    fun ifTest() {
        val statement = """
            if (a < 0) {
                println(-1);
                return 0;
            }
        """
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun ifElseTest() {
        val statement = """
            if (pow % 2 == 0) {
                a = a * a;
                pow = pow / 2;
            } else {
                result = result * a;
                pow = pow - 1;
            }
        """
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
            putLocal("pow", I32)
            putLocal("result", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun whileTest() {
        val statement = """
            while (a > 0) {
                a = a - 1;
            }
        """
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }
}
