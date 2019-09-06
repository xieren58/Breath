package com.zkp.breath.kotlin.classz

class MethodClass {

    // 可变参数
    fun f1(vararg range: Int) {
        for (i in range) {
            println("元素：${i}")
        }
    }

    fun f2() {
        // 参数类型为可变参数，不能传入一整个数组
        // 下面的写法相当于把数组的元素一个个传了进去
        val intArrayOf = intArrayOf(1, 2, 3)
        f1(*intArrayOf)
    }

    fun lambad1(body: (a: Int, b: Int) -> Int) {   // 函数类型，lambda表达式
        // 传入相应的参数调用该参数（函数类型）
        println(body(3, 4))
    }

    // 一个参数。解构声明的调用方式其实也是一个参数，注意圆括号，不能看成是两个参数
    fun lambad2(body: (p: Pair<Int, Int>) -> Int) {
        val body1 = body(Pair<Int, Int>(1, 2))
    }

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

    /**
     * 匿名函数，没有名字，其他语法和常规函数类似
     *
     * 声明一个匿名函数，这里用表达式来表示函数体
     */
    var test3 = fun(x: Int, y: Int): Int = x + y
    /**
     * 声明一个匿名函数，这里用代码块来表示函数体
     */
    var test4 = fun(x: Int, y: Int): Int {
        return x + y
    }
    /**
     * 声明一个匿名函数，当返回值类型可以推断出，可以省略
     */
    var test5 = fun(x: Int, y: Int) = x + y

}


fun isEven(a: Int) = a % 2 == 0

fun comboTwoValue(a: Int, b: Int, method: (a: Int, b: Int) -> Int): Int {
    return method(a, b)
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
    println()

    // 返回类型为可null，能够自动推出类型
    val bodyLock2: String? = methodClass.lock2(1, { "我们" })
    // 传入函数参数null，不能自动推出类型，所以调用的时候要声明泛型类型
    val lock22 = methodClass.lock2<String>(1, null)
    // 函数类型声明，函数类型字面量。
    val body1: (() -> String)? = null
    val body2: (() -> String)? = { "我们" }
    val lock2 = methodClass.lock2(1, body1)
    val lock21 = methodClass.lock2(1, body2)
    println()

    println(methodClass.test3(1,2))
    println(methodClass.test4(1,2))
    println(methodClass.test5(1,2))
    println()

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