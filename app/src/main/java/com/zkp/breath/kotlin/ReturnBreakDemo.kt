package com.zkp.breath.kotlin

/**
 * 普通的for循环是可以使用break、continue、return；
 * 但是在循环的高阶函数中（参数是lambda表达式）无法使用break，continue，这里以函数forEach为例子。
 *
 */

fun main() {
//    label()
//    foo1()
//    foo2()
//    foo3()
//    foo4()
//    foo5()
}

/**
 * 1. Kotlin 会自动推导函数返回值类型，但显式 return 也必须显式声明返回值类型
 * 2. 使用 = 定义函数可以省略函数返回值类型
 */
fun hello() = "Hello World"
fun hello2(): String {
    return "Hello World"
}

/**
 * 这里自动推到函数的返回值类型是 Lambda 表达式
 * 3等价4
 */
fun hello3() = {
    println("Hello World !")
}
fun hello4(): () -> Unit {
    return { println("Hello World !") }
}


/**
 * 标签的格式:自定标识符名 + @。
 */
fun label() {
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            // 终止最外层循环
            if (j == 10) break@loop
            println("当前j:${j}")
        }
    }
}

// =====================================================================
// =====================================================================

/**
 * 1.非内联函数的lambda表达式是不允许直接return，只能使用"@+标签"退出lambda表达式（lambda表达式所处函数）。
 * 2.内联函数的无返回值lambda表达式可以直接return(直接返回lambda表达式所处函数的外层函数)，
 *   也可以使用"@+标签"退出lambda表达式（lambda表达式所处函数）。
 * 3.内联函数的有返回值lambda表达式可以直接return(直接返回lambda表达式所处函数的外层函数)，
 *  但不能使用"@+标签"退出lambda表达式。
 */
fun return1() {
    // 因为该函数的lambda存在返回值
    // 可以return 是因为该函数是inline函数, 这里的return是结束整个return1函数，是允许的。
    // 不能return@ordinaryFunction2是因为该lambda表达式有返回值，不能直接跳出。
    ordinaryFunction2 {
        ""
    }

    ordinaryFunction {
        println("表达式退出")
//        return    // 非内联函数的lambda表达式不允许直接return
        return@ordinaryFunction // 退出lambda表达式
    }
    println("end")

    ordinaryFunction1 {
        println("表达式退出1")
        return    // 内联函数的lambda表达式允许直接return,退出return1函数
//        return@ordinaryFunction1  // 退出lambda表达式
    }
    println("end1")
}

fun ordinaryFunction(block: () -> Unit) {
    block.invoke()
}

inline fun ordinaryFunction1(block: () -> Unit) {
    block.invoke()
}

inline fun ordinaryFunction2(block: () -> String) {
    block.invoke()
}

// ==============================循环高阶函数中的return=======================================
// ==============================循环高阶函数中的return=======================================

fun foo1() {
    // foreach内联函数的lambda表达式参数允许直接return，返回到foo（）
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return
        println("当前it:${it}")
    }
    println("this point is unreachable")
}

/**
 * 内联函数的lambda表达式直接return，如果不想退出到最外层函数，那么可以在内联函数外套一层本地函数。
 */
fun foo2() {
    val listOf = listOf(1, 2, 3, 4, 5)

    // 本地函数，嵌套函数
    fun x(list: List<Int>) {
        list.forEach {
            // foreach内联函数的lambda表达式参数允许直接return，返回到x（）
            if (it == 3) return
            println("当前it:${it}")
        }
    }
    x(listOf)
    println("this point is unreachable")
}

/**
 * 内联函数的无返回值lambda表达式，可以使用"@+标签"退出lambda表达式，以便方法继续往下执行。
 */
fun foo3() {
    listOf(1, 2, 3, 4, 5).forEach {
        // foreach内联函数的lambda表达式参数没有返回值，允许局部返回（相当于continue），返回到lambda表达式调用者，即 forEach（）
        if (it == 3) return@forEach
        println("当前it:${it}")
    }
    println(" done with implicit label")
}

fun foo4() {
    // 我们用一个匿名函数替代 lambda 表达式
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
        // foreach内联函数的lambda表达式参数没有返回值，允许局部返回（相当于continue），返回到lambda表达式调用者，即 forEach（）
        if (value == 3) return
        println("当前it:${value}")
    })
    println(" done with anonymous function")
}

// 通过增加另一层嵌套 lambda 表达式并从其中非局部返回
fun foo5() {
    run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
            println("当前it:${it}")
        }
    }
    println(" done with nested loop")
}