package com.zkp.breath.kotlin.classz

fun main() {

    val intArrayOf = intArrayOf(1, 2, 3)

    // for 循环可以对任何提供迭代器（iterator）的对象进行遍历，语法如下:
    for (item in intArrayOf) print(item)

// 循环体可以是一个代码块:
    for (item: Int in intArrayOf) {
        // ……
    }

// 如果你想要通过索引遍历一个数组或者一个 list，你可以这么做：
    for (i in intArrayOf.indices) {
        print(intArrayOf[i])
    }

// 获取角标和值
    for ((index, value) in intArrayOf.withIndex()) {
        println("the element at $index is $value")
    }

}