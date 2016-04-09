package com.warrior.compiler.buildin

/**
 * Created by warrior on 28.03.16.
 */

fun readI32Function(): String {
    return """
        fn readI32() -> i32 {
            let a: i32 = 0;
            read(a);
            return a;
        }
    """
}

fun readBoolFunction(): String {
    return """
        fn readBool() -> bool {
            let a: bool = false;
            read(a);
            return a;
        }
    """
}
