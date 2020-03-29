package com.zkp.breath.kotlin

class ReflectionClass() {
    var name: String = "tom"
        get() = field
        set(value) {
            field = value
        }

    fun test() {

    }
}

fun main() {
    classReference()


    fun isOdd(x: Int) = x % 2 != 0
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))
}


// 类引用
private fun classReference() {
    // 获取Kotlin的类对象
    val kClass = ReflectionClass::class
    // 获取Java的类对象
    val java = ReflectionClass::class.java

    // 根据实例获取kotlin的类对象和java的类对象
    val reflectionClass = ReflectionClass()
    val kClass1 = reflectionClass::class
    val java1 = reflectionClass::class.java
}

