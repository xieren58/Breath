package com.zkp.breath.kotlin

import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.file.Files
import java.nio.file.Paths

/**
 * lambad和普通函数的区别：普通函数是准备好了逻辑，差参数；lambad是准备好了参数，差逻辑。
 */
class MethodClass {

    fun printlnS() {
        println("打印属性")
    }

    // 函数类型常见写法1
    fun lambad1(body: (a: Int, b: Int) -> Int) {
        println(body(3, 4))
    }

    // 函数类型写法2，函数类型的参数名可以省略。
    fun lambad2(body: (Int, Int) -> Int) {
        println(body(3, 4))
    }

    // 函数类型的参数类型为data类
    fun lambad2(body: (p: Pair<Int, Int>) -> Int): Int {
        return body(Pair<Int, Int>(1, 2))
    }

    //
    fun <T> filter(body: () -> T): T {
        return body()
    }

    fun <T> lock(t: Int, body: () -> T): T {
        return body()
    }

    // lambda 表达式，即函数类型的字面量（看例子的调用方式就知道）
    // 函数类型为可null类型，返回值为可null类型
    fun <T> lock2(t: Int, body: (() -> T)?): T? {
        if (body != null) {
            return body()
        }
        return null
    }

    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this) {
            result.add(transform(item))
        }
        return result
    }

    fun myLet(s: String): Int {
        // let : 默认当前这个对象作为闭包的it参数，返回值是函数里面的最后一行。
        s.let {
            // 其实这里省略了it : Stirng ->
            print(it)  // it指代调用者
            return 999  // 这里的返回是直接返回到外层方法
        }
    }

    fun myLet2(s: String): Int {
        // 也可以使用return 接返回值。
        return s.let {
            print(it)
            999
        }
    }

    fun with(s: String) {
        // 一个对象设置很多属性的时候势必要写多次对象名，使用with可以省略，链式模式思想
        with(s) {
            trim()
            toUpperCase()
            hashCode()
            toString()
        }
    }

    fun run() {
//        run: 和apply很像，只不过run函数可以使用最后一行作为返回，apply则返回调用者自身，run就是with和let的组合扩展

        // labdm类型，且返回值为String
        val block: ArrayList<String>.() -> String = {
            add("1")    // 可以省略书写对象
            add("2")
            add("3")
            println(this.joinToString())
            ""    // 最后一行作为返回，和labdmm类型的返回值类型一致
        }
        // run存在返回，后面可以接着跟方法
        ArrayList<String>().run(block).toUpperCase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun use() {
        val newInputStream = Files.newInputStream(Paths.get(""))
        val byte = newInputStream.use { newInputStream.read() }
    }

    fun repeat() {
        // 执行次数重复执行一个闭包（和for效果一样，比for更加简洁）
        repeat(8) { println("重复执行8次") }
    }

}


fun isEven(a: Int) = a % 2 == 0

fun comboTwoValue(a: Int, b: Int, method: (a: Int, b: Int) -> Int): Int {
    return method(a, b)
}


/**
 * Kotlin支持局部函数,也就是说函数可以嵌套。
 * 局部函数可以访问外部函数（即：闭包）的局部变量。
 */
fun dfs(s: String, i: Int) {
    fun dfs(i: Int): String {
        return i.toString() + s
    }
    dfs(1)
}

fun main() {

    val methodClass = MethodClass()

    methodClass.lambad1 { s, s1 -> s + s1 }     // 知道了类型，可以省略
    methodClass.lambad1 { s: Int, s1: Int -> s + s1 }     // 最完整的写法，类型也写上
    methodClass.lambad2 { (s, s1) -> s + s1 }   // 知道了类型，可以省略。注意这里加了圆括号，因为Pair有组建函数ComponetN,所以这里是解构声明的写法
    methodClass.lambad2 { (s, s1): Pair<Int, Int> -> s + s1 }   // 最完整的写法，类型也写上。注

    val body = { "我们" }
    val filter = methodClass.filter(body)
    println(filter)


    val xxww1 = methodClass.filter {
        val s: String = "我们"
        val s1: String = "你们"
        s + s1
    }
    println(xxww1)

    val xxww2 = methodClass.filter {
        val s: String = "我们"
        val s1: String = "你们"
        // 返回值，作用的范围为filter方法
        return@filter s + s1
    }
    println(xxww2)

    // 普通的调用方式
    val lock = methodClass.lock(1, { "我们" })
    // lambd表达式也是一个函数类型，也可以如下调用
    // 变量声明的形式
    val lockBody: () -> String = { "我们" }
    val lock1 = methodClass.lock(1, lockBody)
    methodClass.lock(1) { 1 }
    println()

    // 返回类型为可null，能够自动推出类型
    val bodyLock2: String? = methodClass.lock2(1, { "我们" })
    // 传入函数参数null.
    val lock22 = methodClass.lock2<String>(1, null)
    // 函数类型声明，函数类型字面量。
    val body1: (() -> String)? = null
    val body2: (() -> String)? = { "我们" }
    val lock2 = methodClass.lock2(1, body1)
    val lock21 = methodClass.lock2(1, body2)
    val lock23 = methodClass.lock2(1) { "我们" }
    val lock24 = methodClass.lock2(1) { null }
    println()

    // apply: 可以认为是进行初始化操作的书写区域，返回值是调用者本身，有点链式模式的意味
    methodClass.apply { "字符串" }.printlnS()


    // 引用方式1, ”::"一元操作符要写在函数名前面，圆括号包裹
    arrayOf(3, 4, 5).filter(::isEven)
    // 引用方式2，花阔号包裹
    arrayOf(3, 4, 5).filter { isEven(it) }
    println()
    arrayOf("你", "我", "ta").filter { it.equals("你") }
    arrayOf("你", "我", "ta").filter("你"::equals)
    println()
    comboTwoValue(3, 4, Math::min)
    comboTwoValue(3, 4, { a, b -> Math.min(a, b) })
    println()

}