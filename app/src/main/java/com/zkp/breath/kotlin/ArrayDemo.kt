package com.zkp.breath.kotlin

import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringArrayList

/**
 * 1.Kotlin 中的数组是一个拥有泛型的类，相对于java来说就拥有了和集合一样的功能。
 * 2.Kotlin 的数组编译成字节码时使用的仍然是 Java 的数组，但在语言层面是泛型实现，这样会失去协变 (covariance) 特性，
 * 就是子类数组对象不能赋值给父类的数组变量（多态）：
 *
 * 数组，即便有自动推断类型，最好还是指定其泛型类型或者使用非包装类型的函数（intArrayOf）进行创建。这样才能
 * 避免类型强转出错。
 */
fun main() {

    // kotlin的数组语言层面支持泛型，而泛型不支持协变。
    val strs: Array<String> = arrayOf("a", "b", "c")
//    val anys: Array<Any> = strs // compile-error: Type mismatch，

    // 可空类型
    val arrayOfNulls = arrayOfNulls<String>(3)
    val arrayOfNulls2: Array<String?> = arrayOfNulls(3) // 另外一种写法

    // 设置对应角标的值的不同写法
    arrayOfNulls.set(0, "a")
    arrayOfNulls[1] = "b"   // 重置了get/set方法
    arrayOfNulls[2] = "c"
    val string = arrayOfNulls.get(1)
    val string1 = arrayOfNulls[1]
    val first = arrayOfNulls.first()
    val last = arrayOfNulls.last()
    val find = arrayOfNulls.find { it.equals("b") }


    // 遍历
    arrayOfNulls.forEach { println("字符串数组内容: " + it) }
    println()

    // 自动推断类型
    val arrayOf = arrayOf(1, 2, 3)
    arrayOf.forEach { println("int数组内容: " + it) }

    // 指定类型
    val intArrayOf = intArrayOf(1, 2, 3)
    intArrayOf.forEach { println("无封箱int数组内容: " + it) }

    // 指定类型，通过lambda表达式赋值
    val intArray = IntArray(5) { index -> index * 2 }
    intArray.forEach { print("无封箱int数组内容创建方式2：$it") }

    // 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
    // index表示角标
    val asc = Array(5, { index -> (index * index).toString() })
    asc.forEach { println(it) }

}


