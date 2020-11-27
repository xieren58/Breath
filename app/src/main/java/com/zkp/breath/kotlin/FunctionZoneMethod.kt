package com.zkp.breath.kotlin

import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.random.Random

/**
 * 作用域函数:let、run、with、apply、also。
 * 作用域函数的区别：1.引用上下文对象的方式（上下文对象：this 还是 it） 2.返回值。
 *
 * 注意： 避免过度使用它们，这会降低代码的可读性并可能导致错误，避免嵌套作用域函数。
 *
 * 使用选择：
 * 函数	    对象引用	    返回值	              是否是扩展函数
 * let	    it	        Lambda 表达式结果          	是
 * run	    this	    Lambda 表达式结果          	是
 * run	    -	        Lambda 表达式结果  	不是：调用无需上下文对象        非作用域函数
 * with	    this	    Lambda 表达式结果  	不是：把上下文对象当做参数      在源码中，注意和作用域函数run的写法区别，但两者的作用是相同的
 * apply	this        上下文对象	                是
 * also	    it	        上下文对象	                是
 *
 *
 * takeIf 与 takeUnless：返回值是可空类型
 */

class FunctionZoneMethodClass(var name: String = "", var age: Int = 10, var city: String = "") {

    fun moveTo(s: String) {
        city = s
    }

    fun incrementAge() {
        age++
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun use() {
    val newInputStream = Files.newInputStream(Paths.get(""))
    val byte = newInputStream.use { newInputStream.read() }
}

fun main() {

    // 不使用作用域，需要使用对象的变量名去调用该类的方法或者属性
    val alice = FunctionZoneMethodClass("Alice", 20, "Amsterdam")
    println(alice)
    alice.moveTo("London")
    alice.incrementAge()
    println(alice)

    letDemo()
    runDemo()
    alsoDemo()
    applyDemo()
    withDemo()

    takeDemo()
    repeatDemo()

}

private fun repeatDemo() {
    // 执行次数重复执行一个闭包（和for效果一样，比for更加简洁）
    repeat(8) { println("重复执行8次") }
}

private fun takeDemo() {
    val number1 = Random.nextInt(100)
    println("it：{$number1}")
    // takeIf（如果）：如果lambda返回true则返回调用者，否则返回null
    val evenOrNull = number1.takeIf { it % 2 == 0 }
    // takeUnless（除非）：如果lambda返回true则返回null，否则返回对象
    val oddOrNull = number1.takeUnless { it % 2 == 0 }
    println("even: $evenOrNull, odd: $oddOrNull")
}

/**
 * with扩展函数和run扩展函数其实是一样的，就是写法不同而已。
 */
private fun withDemo() {
    val mutableListOf1 = mutableListOf("one", "two", "three")
    with(mutableListOf1) {
        val firstItem = first()
        val lastItem = last()
        println("First item: $firstItem, last item: $lastItem")
    }
}

private fun applyDemo() {
    "apply".apply {
        println(length)
        println(first())
        println(last())
    }.length
}

private fun alsoDemo() {
    // 使用also在最后一句返回it能达到相同的效果
    "also".also {
        println(it.length)
        println(it.first())
        println(it.last())
    }.length


    // 因为also最终返回的是上下文对象，所以可以配合return
    fun getRandomInt(): Int {
        return Random.nextInt(100).also {
            println("getRandomInt() generated value $it")
        }
    }

    val i = getRandomInt()
}

private fun runDemo() {
    val str = "Hello"
    str.run {
        println("The receiver string length: $length")
    }
    // 上面的调用方式相当于下面的调用方式
    val block: String.() -> Unit = {
        println("The receiver string length: $length")
    }
    str.run(block)


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


    /**
     * run作用域函数类似于runTemp的定义
     * 1.lambda表达式的参数前"类型." ，那么该函数要么是扩展函数且类型和lambda表达式的参数前的了警一致；要么
     *   定义多一个参数，参数类型也要和lambda表达式的参数前的了警一致。其实这种写法是一种简写，相当于把lambda表达式
     *   参数前的类型移入了()中，函数内调用该lambda表达式的时可以用一种简写的方式调用（其实就是为了偷懒）。
     * 2.runTemp1等价runTemp2，只是写法不同而已。
     * 3.runTemp3等价runTemp4，只是写法不同而已。
     */
    fun <T, R> T.runTemp1(block: T.() -> R) {
        // 这三种调用方式等价，只是写法不同
//        block(this)
//        this.block()
        block() // 简写（偷懒）方式
    }

    // 这种写法也是with扩展函数的写法
    fun <T, R> runTemp2(t: T, block: T.() -> R) {
        // 这两种调用方式等价，最后一种方式是kotlin的一种简写方式
//        block(t)
        t.block()
    }

    fun <T, R> runTemp3(t: T, block: T.(s: String) -> R) {
        t.block("哈哈哈")
    }

    fun <T, R> runTemp4(t: T, block: (t: T, s: String) -> R) {
        block(t, "哈哈哈")
    }

}

// let功能域函数的demo
private fun letDemo() {
    FunctionZoneMethodClass("Alice", 20).let {
        // 可以省去变量名，上下文对象为it。
        println(it) // Person(name=Alice, age=20, city=Amsterdam)
        it.moveTo("London")
        it.incrementAge()
        println(it)  //Person(name=Alice, age=21, city=London)
    }

    val letParams: (FunctionZoneMethodClass) -> Unit = {
        it.moveTo("2")
        it.incrementAge()
        println(it) // 返回值。打印方法无返回值所以该lambda表达式的返回值为Unit
    }
    FunctionZoneMethodClass("1", 20).let(letParams)

    val let = FunctionZoneMethodClass("1", 20).let {
        it.moveTo("2")
        it.incrementAge()
        println(it)
        it  // 返回值
    }
}