package com.zkp.breath.kotlin

/**
 * 字符串demo
 *
 * kt的== 和 ===：
 *  1. == ：可以对基本数据类型以及 String（相当于 Java 中的 equals） 等类型进行内容比较,其实 Kotlin 中的
 *    equals 函数是 == 的操作符重载
 *  2. === ：对引用的内存地址进行比较，相当于 Java 中的 ==
 */
fun main() {
    val s = "abc"

    // 相当于java的String的index()
    print(s[0]) // 相当于调用了下面的方法
    print(s.get(0))

    for (c in s) {
        println("遍历字符: $c")
    }

    // 转义字符串
    val s1 = "Hello, world!\n"

    // 原始字符串
    // 有时候我们不希望写过多的转义字符，这种情况 Kotlin 通过「原生字符串」来实现，用法就是使用一对 """ 将字符串括起来：
    // 这里有几个注意点：
    //    1. \n 并不会被转义
    //    2. 最后输出的内容与写的内容完全一致，包括实际的换行
    //    3. $ 符号引用变量仍然生效
    val name = "world"
    val myName = "kotlin"
    val text = """
      Hi $name!
    My name is $myName.\n
    """
    println(text)

    // 去除前空格
    val text1 = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
    println("去除|前面的空格: $text1")

    val price = """
    $9.99
    """

    // 原始字符串也支持字符串模板
    val price1 = """
    ${'$'}9.99
    """
    println(price)
    println(price1)

}