package com.zkp.breath.kotlin

/**
 * 内联函数：调用一个方法会进行压栈进栈的操作（这里的栈指方法栈），这个压栈和出栈的过程会耗费资源。
 * 而一个函数添加inline关键字后表示调用它的时候会把这个函数方法体中的所有代码移动到调用的地方从而
 * 避免因为通过方法间压栈进栈的方式造成资源消耗。
 *
 * 什么函数才用inline修饰：inline 适合在包含 lambda 参数的函数上，否则as会提示在这个方法使用inline
 * 不会有很大的提高。原因是因为每一个lambda参数都会额外生成一个对象（这就说明为什么lambda是一个函数引用，
 * 因为也能作为参数传递给函数或者赋值给变量）, 而一旦使用inline关键字其实就相当于不会去调用一个带有lambda表达式
 * 的函数，那么就可以避免因为这个lambda表达式会被编译成一个对象导致增加内存分配和调用时进出栈带来的时间消耗。
 *
 */

fun main() {
    println("start")
    makeTest()
    println("end")
}

// 内联函数 makeTest()
private inline fun makeTest() {
    println("inline method: makeTest")
}

// =========================调用inline方法，编译后的java代码=========================
// =========================调用inline方法，编译后的java代码=========================
//fun main() {
//    println("start")
//    println("inline method: makeTest")
//    println("end")
//}

