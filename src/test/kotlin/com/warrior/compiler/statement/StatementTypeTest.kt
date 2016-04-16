package com.warrior.compiler.statement

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
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
    fun assignEmptyArrayTest() {
        val statement = "a = [];"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(I32, 0))
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setTupleElementTest() {
        val statement = "a.1 = b;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Tuple(Bool, I32))
            putLocal("b", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setTupleElementEmptyArrayTest() {
        val statement = "a.1 = [];"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Tuple(Bool, Type.Array(I32, 0)))
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setArrayElementTest() {
        val statement = "a[1] = b;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(I32, 2))
            putLocal("b", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setArrayElementEmptyArrayTest() {
        val statement = "a[1] = [];"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(Type.Array(I32, 0), 2))
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setElementTest1() {
        val statement = "a[1].0 = b;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Type.Array(Tuple(I32, Bool), 2))
            putLocal("b", I32)
        }
        st.validate(functions, variables, "main")
        st.checkTypes()
    }

    @Test
    fun setElementTest2() {
        val statement = "a.1[0] = b;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Tuple(I32, Type.Array(I32, 3)))
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
    fun assignDeclEmptyArrayTest() {
        val statement = "let a: [i32; 0] = [];"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        st.validate(functions, fnName = "main")
        st.checkTypes()
    }

    @Test
    fun destructiveDeclarationTest() {
        val statement = "let (b, c) = a;"
        val st = parseStatement(statement)
        val functions = mapOf("main" to Fn(emptyList(), I32))
        val variables = SymbolTable<Type>().apply {
            putLocal("a", Tuple(I32, Type.Array(I32, 2)))
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
