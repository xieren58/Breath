package com.zkp.breath.kotlin

import kotlin.reflect.full.*

class ReflectionClass {
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
    // 获取Kotlin的Class实例
    val kClass = ReflectionClass::class
    // 获取Java的Class实例
    val java = ReflectionClass::class.java

    // 根据实例获取kotlin的类对象和java的类对象
    val reflectionClass = ReflectionClass()
    val kClass1 = reflectionClass::class
    val java1 = reflectionClass::class.java
    val java2 = reflectionClass.javaClass


    // =======================构造函数Constructor=======================
    // =======================构造函数Constructor=======================
    // 获取所有的构造函数
    val constructors = kClass1.constructors
    // 获取主构造函数
    val primaryConstructor = kClass1.primaryConstructor


    // =======================成员变量和成员函数=======================
    // =======================成员变量和成员函数=======================
    //返回类声明的所有函数
    val declaredFunctions = kClass1.declaredFunctions
    //返回类的扩展函数
    val declaredMemberExtensionFunctions = kClass1.declaredMemberExtensionFunctions
    // 返回类的扩展属性
    val declaredMemberExtensionProperties = kClass1.declaredMemberExtensionProperties
    //返回类自身声明的成员函数
    val declaredMemberFunctions = kClass1.declaredMemberFunctions
    //返回类自身声明的成员变量（属性）
    val declaredMemberProperties = kClass1.declaredMemberProperties


}

