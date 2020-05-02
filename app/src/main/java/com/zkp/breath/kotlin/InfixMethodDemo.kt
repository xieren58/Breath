package com.zkp.breath.kotlin

/**
 * 中缀表达式
 *
 * 用 infix 关键字标记的函数可以使用中缀符号调用（调用可省略点号和括号）。中缀函数需要满足如下条件：
 * 1.函数是成员函数或扩展函数；
 * 2.函数只有一个参数
 * 3.参数不能接受可变数量的参数并且不能拥有默认值。
 */

// 全局中缀表达式的声明样式
infix fun Int.infixMethod(x: Int): Int {
    return this * x
}

class InfixClass {

    // 中缀表达式（成员函数的声明样式)
    infix fun add(s: String) {

    }

    fun build() {
        this add "abc"  // 省略了点号和括号的调用方式
        add("abc")    // 和调用普通方法一样的方式
//        add "abc"   // 如果是中缀函数的调用方式不能省略调用方，而此处的调用方是this
    }

}

fun main() {
    val demo = 1
    demo.infixMethod(1) // 和调用普通方法一样的调用方式
    demo infixMethod 1  // 省略"."和"（）"的调用方式a
    demo infixMethod 3 + 2
}