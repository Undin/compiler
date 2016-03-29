# Compiler

## Features
* supported types: `i32` and `bool`
* functions
* global and local variables
* expressions:
  * arithmetic for `i32` type: `+`, `-`, `*`, `/`, `%`
  * comparison for `i32` type: `<`, `<=`, `>`, `>=`
  * equality: `==`, `!=`
  * lazy boolean for `bool` type: `&&`, `||`, `!`
* control flow statements: `if`, `if/else`, `while`
* I/O statements: `read(a)`, `print(a)` and `println(a)`
* simple type inference

See [example](https://github.com/Undin/compiler/blob/master/sample.y)

## Requirements
* `java 7` or `java 8`
* `llvm 3.7.0` (`lli` util needs for test)

## Build
* download jar for you OS to `libs/` folder from http://search.maven.org/#artifactdetails|org.bytedeco.javacpp-presets|llvm|3.7.0-1.1|jar
* run `./gradlew build` for *nix or `gradlew.bat build` for Windows

## Run
`java -jar <compiler-jar-name>.jar <compiled-file-name>`
