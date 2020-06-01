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
 *
 *注意： 避免过度使用它们，这会降低代码的可读性并可能导致错误，避免嵌套作用域函数。
 *
 *
 * 使用选择：
 * 函数	    对象引用	    返回值	              是否是扩展函数
 * let	    it	        Lambda 表达式结果          	是
 * run	    this	    Lambda 表达式结果          	是
 * run	    -	        Lambda 表达式结果  	不是：调用无需上下文对象
 * with	    this	    Lambda 表达式结果  	不是：把上下文对象当做参数
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


    val number1 = Random.nextInt(100)
    println("it：{$number1}")
    // takeIf（如果）：如果lambda返回true则返回调用者，否则返回null
    val evenOrNull = number1.takeIf { it % 2 == 0 }
    // takeUnless（除非）：如果lambda返回true则返回null，否则返回对象
    val oddOrNull = number1.takeUnless { it % 2 == 0 }
    println("even: $evenOrNull, odd: $oddOrNull")

    // 执行次数重复执行一个闭包（和for效果一样，比for更加简洁）
    repeat(8) { println("重复执行8次") }

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