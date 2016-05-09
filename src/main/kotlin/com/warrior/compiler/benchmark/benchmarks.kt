package com.warrior.compiler.benchmark

import com.warrior.compiler.Compiler
import java.io.File
import kotlin.system.measureNanoTime

/**
 * Created by warrior on 07/05/16.
 */
fun main(args: Array<String>) {
    fibTailRec()
    fact()
    fib()
}

private fun fibTailRec() {
    val function = """
        fn fib(n: i32, a: i32, b: i32) -> i32 {
            if (n == 0) {
                return a;
            } else {
                return fib(n - 1, b, (a + b) % 10000);
            }
        }
    """
    val call = "fib(100000, 0, 1);"
    bench("fibTailRec", function, call, 10000)
}

private fun fact() {
    val function = """
        fn fact(n: i32) -> i32 {
            if (n == 0) {
                return 1;
            } else {
                return n * fact(n - 1);
            }
        }
    """
    val call = "fact(100000);"
    bench("fact", function, call, 10000)
}

private fun fib() {
    val function = """
        fn fib(n: i32) -> i32 {
            if (n == 0 || n == 1) {
                return n;
            } else {
                return fib(n - 1) + fib(n - 2);
            }
        }
    """
    val call = "fib(30);"
    bench("fib", function, call, 1000)
}

private fun bench(name: String, function: String, call: String, iteration: Int) {
    val program = """
        fn main() -> i32 {
            let n = readI32();
            let i = 0;
            while (i < n) {
                $call
                i = i + 1;
            }
            return 0;
        }

        $function
    """

    val withTailRecEliminationName = "${name}_without_tail_rec_elimination"
    val withoutTailRecEliminationName = "${name}_with_tail_rec_elimination"

    try {
        var success = Compiler(program).use {
            it.compile(withTailRecEliminationName, true)
        }
        success = success and Compiler(program).use {
            it.compile(withoutTailRecEliminationName, false)
        }
        if (success) {
            println(name)
            val withTailRecElimination = launch(withTailRecEliminationName, iteration)
            println("with tail rec elimination: ${withTailRecElimination.toDouble() / iteration / 1000000} ms/it")
            val withoutTailRecElimination = launch(withoutTailRecEliminationName, iteration)
            println("without tail rec elimination: ${withoutTailRecElimination.toDouble() / iteration / 1000000} ms/it")
            println("---------------------------------------------------------------")
        }

    } finally {
        File(withTailRecEliminationName).delete()
        File(withoutTailRecEliminationName).delete()
    }
}

private fun launch(name: String, iteration: Int): Long {
    return measureNanoTime {
        val process = ProcessBuilder("./$name").start()
        process.outputStream.write("$iteration\n".toByteArray())
        process.outputStream.flush()
        process.waitFor()
    }
}
