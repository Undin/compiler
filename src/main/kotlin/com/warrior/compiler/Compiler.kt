package com.warrior.compiler

import com.warrior.compiler.module.Module
import com.warrior.compiler.buildin.readBoolFunction
import com.warrior.compiler.buildin.readI32Function
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import org.antlr.v4.runtime.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.Pointer
import java.io.Closeable
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Created by warrior on 22.03.16.
 */
class Compiler(val program: String): Closeable {

    companion object {
        var str: LLVMValueRef? = null
        var strLn: LLVMValueRef? = null

        fun getStr(builder: LLVMBuilderRef): LLVMValueRef {
            if (str == null) {
                str = LLVMBuildGlobalStringPtr(builder, "%d", "str")
            }
            return str!!
        }

        fun getStrLn(builder: LLVMBuilderRef): LLVMValueRef {
            if (strLn == null) {
                strLn = LLVMBuildGlobalStringPtr(builder, "%d\n", "strLn")
            }
            return strLn!!
        }
    }

    private val module: LLVMModuleRef = LLVMModuleCreateWithName("module")
    private val builder: LLVMBuilderRef = LLVMCreateBuilder()

    fun compile(optimize: Boolean = false): Boolean {
        val ast = buildAST(program)
        if (ast != null) {
            val result = ast.validate()
            return when (result) {
                Ok -> {
                    generateCode(ast, optimize)
                    true
                }
                is Error -> {
                    printError(result)
                    false
                }
            }
        }
        return false
    }

    private fun generateCode(ast: Module, optimize: Boolean) {
        ast.generateCode(module, builder)

        if (optimize) {
            val pass = LLVMCreatePassManager()
            try {
                LLVMAddConstantPropagationPass(pass)
                LLVMAddInstructionCombiningPass(pass)
                LLVMAddPromoteMemoryToRegisterPass(pass)
                LLVMAddGVNPass(pass)
                LLVMAddCFGSimplificationPass(pass)
                LLVMRunPassManager(pass, module)
            } finally {
                LLVMDisposePassManager(pass)
            }
        }

        val error = BytePointer(null as Pointer?)
        try {
            LLVMVerifyModule(module, LLVMPrintMessageAction, error)
            val errorMessage = error.string
            if (!errorMessage.isNullOrEmpty()) {
                System.err.println(error.string)
            }
        } finally {
            LLVMDisposeMessage(error)
        }
    }

    private fun printError(error: Error) {
        error.messages.forEach {
            System.err.println("$it\n")
        }
    }

    private fun buildAST(code: String): Module? {
        val fullCode = addBuildinCode(code);
        val stream = ANTLRInputStream(fullCode);
        val lexer = GrammarLexer(stream);
        val tokens = CommonTokenStream(lexer);
        val parser = GrammarParser(tokens);
        val errorListener = ErrorListener()
        parser.addErrorListener(errorListener)
        try {
            val tree = parser.module();
            if (!errorListener.hasSyntaxError) {
                val visitor = ASTVisitor()
                return visitor.visitModule(tree)
            }
        } catch (e: RecognitionException) {
            e.printStackTrace()
        }
        return null
    }

    private fun addBuildinCode(code: String): String {
        return StringBuilder(code)
                .append("\n")
                .append(readBoolFunction())
                .append(readI32Function())
                .toString()
    }

    fun getAsm(): String = LLVMPrintModuleToString(module).string

    fun interpret(input: String): String {
        val llvmCodePath = Files.createTempFile("llvm", ".ll")
        try {
            Files.copy(getAsm().byteInputStream(), llvmCodePath, StandardCopyOption.REPLACE_EXISTING)
            val process = ProcessBuilder()
                    .command("lli", llvmCodePath.toString())
                    .start()

            process.outputStream.write(input.toByteArray())
            process.outputStream.flush()
            process.waitFor()
            val out = process.inputStream.readBytes().toString(Charsets.UTF_8)
            return out
        } finally {
            Files.deleteIfExists(llvmCodePath)
        }
    }

    override fun close() {
        str = null
        strLn = null
        LLVMDisposeBuilder(builder)
        LLVMDisposeModule(module)
    }

    private class ErrorListener : BaseErrorListener() {

        var hasSyntaxError = false
            private set

        override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?,
                                 line: Int, charPositionInLine: Int, msg: String?, e: RecognitionException?) {
            hasSyntaxError = true
        }
    }
}
