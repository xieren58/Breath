package com.zkp.breath.kotlin

/**
 * kotlin的区间（在 Java 语言中并没有 Range 的概念）
 */
fun main() {
    val intRange = 0..10    // [0,10]
    val intRange2 = 0 until 10  //[0,10)

    // 遍历区间
    for (i in intRange2) {
        println("当前i为:$i")
    }

    // 通过 step 设置步长(递增)
    // 0，2，4，6，8
    for (i in intRange2 step 2) {
        println("当前i为:$i")
    }

    // 递减区间 downTo（不过递减没有半开区间的用法）
    // 4，3，2，1
    for (i in 4 downTo 1) {
        print("$i, ")
    }

    // float只有闭合区间，没有半开区间
    val floatRange = 0f..10f

    val longRange = 0L..10L
    val longRange2 = 0L until 10L
}