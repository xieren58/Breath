package com.zkp.breath.kotlin.classz

/**
 * 常见数据类型，kotlin无java的基本类型
 */
fun main() {
    val i: Int = 1
    val l: Long = 1L
    val f: Float = 1f
    val d: Double = 1.0

    // 下划线写法
    val line: Int = 1_000_000

    // 不支持八进制,都支持下划线写法
    val i1: Int = 123    // 十进制
    val i2: Int = 0xFF  // 十六进制
    val i3: Int = 0b001 // 二进制

    compareValueOrMemory()
    typeConversion()
    char()
}

// 比较值和比较内存地址
fun compareValueOrMemory() {

    val a: Int = 10000

    //经过了装箱，创建了两个不同的对象
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a

    //虽然经过了装箱，但是值是相等的，都是10000
    println(boxedA === anotherBoxedA) //  false，值相等，对象地址不一样
    println(boxedA == anotherBoxedA) // true，值相等
}

// 类型转换
fun typeConversion() {
    val b: Byte = 1 // OK, 字面值是静态检测的
    val i: Int = b.toInt()    //  无法像java的基本类型把int复制给long，因为kotlin的数据类型都是对象
    val l = 1L + 3 // Long + Int => Long，内部其实调用了Long做相应的数学操作符重载
}

// 不同于 Java 的是，字符不属于数值类型，是一个独立的数据类型
fun char() {
    val c: Char = '我'
    // 支持的几个转义字符
    val c1: Char = '\t'
    val c2: Char = '\b'
    val c3: Char = '\n'
    val c4: Char = '\''
    val c5: Char = '\\'
    val c6: Char = '\$'
    //  编码其他字符要用 Unicode 转义序列语法：'\uFF00'。
    val s = "我转义字符：" + c1 + "xxx" + c2 + c3
    println(s)
}