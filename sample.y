let pow: i32;

fn main() -> i32 {
    let a: i32;
    read(a);
    pow = readI32();
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
