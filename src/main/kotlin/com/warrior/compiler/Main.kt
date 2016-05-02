package com.warrior.compiler

import java.io.File

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
        it.compile(file.nameWithoutExtension)
    }
}
