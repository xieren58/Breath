package com.zkp.breath.kotlin

/**
 * 可变参数：vararg关键字修饰的参数，其实相当于一个数组（Array<out T>），修饰的参数只能是参数列表的最后一个。
 *
 * 伸展操作符（spread operator）：在数组名前加一个“*”，一般的应用场景是传递数组的元素到可变参数中。
 */


// 可变参数，相当于Array<out T>
fun <T> varargMethod(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts)
        result.add(t)
    return result
}


fun main() {
    val arrayOf = arrayOf(1, 2, 3, 4)
    val varargMethod1 = varargMethod(1f, 2f, 3f)
    // spread 操作符（数组前面加个 *）,相当于遍历获取每个元素的作用
    val varargMethod2 = varargMethod(*arrayOf)
}