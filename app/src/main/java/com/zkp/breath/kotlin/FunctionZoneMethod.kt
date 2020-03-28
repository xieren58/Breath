package com.zkp.breath.kotlin

import kotlin.random.Random

/**
 * 作用域函数:let、run、with、apply、also。
 * 作用域函数的区别：1.引用上下文对象的方式（上下文对象：this 还是 it） 2.返回值。
 *
 * 上下文对象：this 还是 it？（其实都可以，只是要不要进一步偷懒而已）
 * 在作用域函数的 lambda 表达式里，上下文对象可以不使用其实际名称而是使用一个更简短的引用来访问。每个作用域函数都
 * 使用以下两种方式之一来访问上下文对象：作为 lambda 表达式的接收者（this）或者作为 lambda 表达式的参数（it）。
 *
 * run、with、apply 通过关键字 this 引用上下文对象，主要对对象成员进行操作（调用其函数或赋值其属性）的 lambda
 * 表达式，则this更好；let 、also 将上下文对象作为 lambda 表达式参数，如果该上下文对象
 * 用于其他函数的调用时作为参数传入，则it更好。
 *
 * 返回值（根据后续操作是针对上下文对象还是lambda表达式结果选择即可）
 * apply 及 also 返回上下文对象。
 * let、run 及 with 返回 lambda 表达式结果.
 */

class FunctionZoneMethodClass(var name: String = "", var age: Int = 10, var city: String = "") {

    fun moveTo(s: String) {
        city = s
    }

    fun incrementAge() {
        age++
    }

}

fun main() {

    // 不使用作用域，需要使用对象的变量名去调用该类的方法或者属性
    val alice = FunctionZoneMethodClass("Alice", 20, "Amsterdam")
    println(alice)
    alice.moveTo("London")
    alice.incrementAge()
    println(alice)

    // 可以省去变量名，上下文对象为it。
    FunctionZoneMethodClass("Alice", 20).let {
        println(it) // Person(name=Alice, age=20, city=Amsterdam)
        it.moveTo("London")
        it.incrementAge()
        println(it)  //Person(name=Alice, age=21, city=London)
    }


    val str = "Hello"
    // this（调用方法直接写方法名字即可）
    str.run {
        println("The receiver string length: $length")
        //println("The receiver string length: ${this.length}") // 和上句效果相同
    }
    // 上面的调用方式相当于下面的调用方式
//    val block: String.() -> Unit = {
//        println("The receiver string length: $length")
//    }
//    str.run(block)


    // it（调用方法名还是要用it.方法名）
//    str.let(::println)  // 若代码块仅包含以 it 作为参数的单个函数，则可以使用方法引用(::)代替 lambda 表达式
    str.let {
        println("The receiver string's length is ${it.length}")
    }
    // 上面的调用方式相当于下面的调用方式
//    val block: (String) -> Unit = {
//        println("The receiver string's length is ${it.length}")
//    }
//    str.let(block)


    // ==================根据返回值的选择对应的功能与函数==================
    // ==================根据返回值的选择对应的功能与函数==================
    // 有点rx的操作符的味道
    val numberList = mutableListOf<Double>()
    numberList.also {
        println("Populating the list")
    }.apply {
        add(2.71)
        add(3.14)
        add(1.0)
    }.also {
        println("Sorting the list")
    }.sort()


    // 因为also最终返回的是上下文对象，所以可以配合return
    fun getRandomInt(): Int {
        return Random.nextInt(100).also {
            println("getRandomInt() generated value $it")
        }
    }

    val i = getRandomInt()


    val mutableListOf = mutableListOf("A", "B", "C")
    val number = mutableListOf.run {
        add("D")
        add("E")
        add("F")
        // 最有一句最为返回值，所以看count功能域函数的返回值
        count {
            it.endsWith("e", true)
        }
    }
    println("There are $number elements that end with e.")


    // with:仅使用作用域函数为变量创建一个临时作用域
    val mutableListOf1 = mutableListOf("one", "two", "three")
    with(mutableListOf1) {
        val firstItem = first()
        val lastItem = last()
        println("First item: $firstItem, last item: $lastItem")
    }

}