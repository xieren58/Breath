package com.zkp.breath.kotlin.classz

fun main() {
    val s = "abc"
    // 重写了get/set方法
    print(s[0])
    print(s.get(0))
    for (c in s) {
        println("遍历字符: $c")
    }

    // 转义字符串
    val s1 = "Hello, world!\n"

    // 原始字符串
    val text = """
    for (c in "foo")
        print(c)
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