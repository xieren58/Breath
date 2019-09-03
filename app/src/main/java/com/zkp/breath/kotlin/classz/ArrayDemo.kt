package com.zkp.breath.kotlin.classz

fun main() {
    // 示例
    val arrayOfNulls = arrayOfNulls<String>(3)
    // val arrayOfNulls: Array<String?> = arrayOfNulls(3) // 另外一种写法
    arrayOfNulls.set(0, "a")
    arrayOfNulls[1] = "b"
    arrayOfNulls[2] = "c"
    arrayOfNulls.forEach { println("字符串数组内容: " + it) }
    println()

    val arrayOf = arrayOf(1, 2, 3)
    arrayOf.forEach { println("int数组内容: " + it) }
    val intArrayOf = intArrayOf(1, 2, 3)
    intArrayOf.forEach { println("无封箱int数组内容: " + it) }
    println()

    // 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
    val asc = Array(5, { i -> (i * i).toString() })
    asc.forEach { println(it) }
    println()


}


