package com.zkp.breath.kotlin

class Box<T>(t: T) {
    // 类型为泛型
    var value = t

    // 泛型的类型为Comparable或其子类
    fun <T : Comparable<T>> sort(list: List<T>) {
        // ……
    }

    // 对于多个上界约束条件，可以用 where 子句
    fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
            where T : CharSequence,
                  T : Comparable<T> {
        return list.filter { it > threshold }.map { it.toString() }
    }
}

fun main(args: Array<String>) {
    //创建方式1，as其实有提示，但是离开as可读性不好
    val fx = Box("")
    // 创建方式2，可读性好，但是太长
    val fx1: Box<String> = Box<String>("")
    // 创建方式3，强烈推荐，可读性好
    val fx2: Box<String> = Box("")
}