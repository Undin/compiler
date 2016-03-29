package com.warrior.compiler

import java.io.File
import java.io.PrintWriter

/**
 * Created by warrior on 29.03.16.
 */
fun main(args: Array<String>) {
    if (args.size == 0) {
        println("missed compiled file")
        return
    }
    val file = File(args[0])
    val program = file.readText()

    Compiler(program).use {
        if (it.compile()) {
            val compiledCode = it.getAsm()
            PrintWriter(file.nameWithoutExtension + ".ll").use {
                it.print(compiledCode)
            }
        }
    }
}
