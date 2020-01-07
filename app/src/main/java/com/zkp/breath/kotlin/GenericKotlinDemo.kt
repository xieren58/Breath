package com.zkp.breath.kotlin

import java.io.Serializable

class Producer<out T> {
    fun produce(): T? {
        return null
    }
}

class Consumer<in T> {
    fun consume(t: T) {

    }
}

class Consumer1<T : Number>

class Consumer2<T> where T : Number, T : Serializable

class Consumer3<T> {
    fun ff(): T? {
        return null
    }
}


fun main() {
    // 一般允许这种写法的话就表示在定义类里面已经存在out/in关键字，否则按照java的规则泛型是不允许多态的。
    val producer1: Producer<Number> = Producer<Int>()    //  // 👈 这里不写 out 也不会报错
    val producer2: Producer<out Number> = Producer<Int>() // 👈 out 可以但没必要
    val producer3 = Producer<Int>()
    val producer4: Producer<*> = Producer<Int>()

    val produce = producer1.produce()
    val produce1 = producer3.produce()
    val produce2 = producer4.produce()

    // ========================================================
    // ========================================================

    val consumer1: Consumer<Int> = Consumer<Number>() // 👈 这里不写 in 也不会报错
    val consumer2: Consumer<in Int> = Consumer<Number>() // 👈 in 可以但没必要
    val consumer3: Consumer<Int> = Consumer()       // 只写前面，不写后面，已前面类型为准
    val consumer4: Consumer<Int> = Consumer<Int>()      // 前后都一致类型，后面的可以省略不写
    val consumer5 = Consumer<Int>()     // 根据后面的声明自动类型推断
    val consumer6: Consumer<*> = Consumer<Int>()    // Nothing类型

    consumer1.consume(2)

    val c1: Consumer3<*> = Consumer3<Int>() // 相当于下面的写法
    val c: Consumer3<out Any> = Consumer3<Int>()
    val ff = c.ff()

}