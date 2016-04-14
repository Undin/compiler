package com.warrior.compiler.module

import com.warrior.compiler.checkTypes
import com.warrior.compiler.parseModule
import org.junit.Test

/**
 * Created by warrior on 06.04.16.
 */
class ModuleTypeTest {

    @Test
    fun test() {
        val program = """
            let a = 5;
            let b = [(1, 2), (3, 4)];

            fn main() -> i32 {
                a + 5;
                let pow: i32 = 0;
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
}
