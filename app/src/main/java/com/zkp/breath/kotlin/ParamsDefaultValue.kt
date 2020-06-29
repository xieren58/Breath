package com.zkp.breath.kotlin

/**
 * 参数默认值
 */

fun sayHi(name: String = "world") = println("Hi $name")

fun sayHi(name: String = "world", age: Int) {
}

fun main() {
    // 调用一个参数方法
    sayHi("")

    // 调用两个参数的方法
    // sayHi(10)  // 这种写法是不被允许的
    // sayHi(name = "", 10) // 这种写法是不被允许的
    // sayHi(age = 10, "")  // 这种写法是不被允许的
    sayHi(age = 10)
    sayHi(age = 10, name = "")
    sayHi(name = "", age = 10)  // 命名参数赋值，顺序可随意
    sayHi("", 10)

}