package com.warrior.compiler.prebuilds

/**
 * Created by warrior on 28.03.16.
 */

fun readI32Function(): String {
    return """
        fn readI32() -> i32 {
            let a: i32;
            read(a);
            return a;
        }
    """
}

fun readBoolFunction(): String {
    return """
        fn readBool() -> bool {
            let a: bool;
            read(a);
            return a;
        }
    """
}
