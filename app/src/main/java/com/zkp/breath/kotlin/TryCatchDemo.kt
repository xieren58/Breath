package com.zkp.breath.kotlin

import java.io.IOException
import java.lang.Integer.parseInt


/**
 *  在java中一个方法声明抛出异常调用的时候IDE会提示：Unhandled exception: java.io.XXXXXX. 我们需要进行
 *  try-catch的操作。而Kotlin 中的异常是不会被检查的，只有在运行时如果抛出异常，才会出错，我们直接调用
 *  IDE是不会进行任何提示的。
 */
@Throws(IOException::class)
fun sayHi() {
}

fun main() {
    sayHi()

    // Kotlin 中 try-catch 语句也可以是一个表达式，允许代码块的最后一行作为返回值
    val a: Int? = try {
        parseInt("")
    } catch (e: NumberFormatException) {
        null
    }
}