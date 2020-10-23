package com.zkp.breath.kotlin

/**
 * 中缀表达式 :
 * 1. infix关键字修饰方法，调用的时候可省略"."和"()"
 * 2. 扩展函数
 * 3. 函数参数只有一个且不能是可变参数(如果可变则违反2的定义，因为可变参数本身是一个数组，可以看成是多个参数)，并且不能拥有默认值
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
//        add "abc"   // 如果是中缀函数的调用方式则不能省略调用方，而此处的调用方是this
    }

}

fun main() {
    val demo = 1
    demo.infixMethod(1) // 和调用普通方法一样的调用方式
    demo infixMethod 1  // 省略"."和"（）"的调用方式a
    demo infixMethod 3 + 2
}