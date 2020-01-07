package com.zkp.breath.kotlin

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

    // ================= 区间 =================
    // ================= 区间 =================

    for (i in 1..4) print(i) // 输出“1234”

    for (i in 4 downTo 1) print(i)  // 逆向输出4321

    val x = 20
    if (x in 1..10) { // 等同于 1 <= i && i <= 10
        println(x)
    }

// 使用 step 指定步长
    for (i in 1..4 step 2) print(i) // 输出“13”

    for (i in 4 downTo 1 step 2) print(i) // 输出“42”，

// 使用 until 函数排除结束元素
    for (i in 1 until 10) {   // i in [1, 10) 排除了 10
        println(i)
    }

}