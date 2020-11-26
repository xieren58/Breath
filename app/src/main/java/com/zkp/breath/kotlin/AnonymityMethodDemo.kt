package com.zkp.breath.kotlin

/**
 * 匿名函数：没有名字的函数，其他语法和常规函数一样。其实匿名函数也可以看成是lambda表达式
 * 但是注意必须要有val/var修饰的字段指向（都没方法名了,还没有变量名的话怎么调用），否则就需要添加函数名。
 */

val anonymityMethod1 = fun(x: Int, y: Int): Int = x + y
var anonymityMethod2 = fun(x: Int, y: Int) = x + y
val anonymityMethod3 = fun(x: Int, y: Int): Int {
    return x + y
}
val anonymityMethod4: (Int, Int) -> Int = fun(x: Int, y: Int) = x + y

fun lambdaAnonymityMethod(body: (Int, Int) -> Int, x: Int, y: Int) {
    body(x, y)
}

fun anonymityMethod() {

    // anonymityMethod1指向匿名函数的变量名，而不是方法名，但下面的调用看起来和普通的函数调用一样。
    val anonymityMethod11 = anonymityMethod1(1, 2)

    // 使用匿名函数作为本地函数（嵌套函数）
    val anonymityMethod2 = fun(x: Int, y: Int): Int {
        return x + y
    }

    // 使用普通函数作为本地函数（嵌套函数）
    fun localMethod(x: Int, y: Int): Int {
        return x + y
    }

    // 使用匿名函数作为lambda表达式的参数
    lambdaAnonymityMethod(anonymityMethod1, 1, 2)
}