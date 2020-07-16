package com.zkp.breath.kotlin

fun main() {
//    label()
//    foo()
//    foo1()
//    foo2()
//    foo3()

    fooCopy()
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


/**
 * 非内联函数的lambda表达式是不允许直接return
 */
fun return1() {
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

/**
 * 内联函数的lambda表达式可以直接return
 */
fun return2() {
    ordinaryFunction1 {
        return@ordinaryFunction1    // 退出到ordinaryFunction1
    }

    // 因为该函数的lambda存在返回值，但是不能 return@ordinaryFunction2。
    // 可以return 是因为该函数是inline函数, 这里的return是结束整个return2函数，是允许的。
    // 不能return@ordinaryFunction2是因为该lambda表达式有返回值，不能直接跳出。
    ordinaryFunction2 {
        ""
    }

    ordinaryFunction1 {
        return  // 退出return2函数，即该函数结束
    }
}

inline fun ordinaryFunction1(block: () -> Unit) {
    block.invoke()
}

inline fun ordinaryFunction2(block: () -> String) {
    block.invoke()
}


// 注意，这种非局部的返回只支持传给内联函数的 lambda 表达式。
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // 非局部直接返回到 foo() 的调用者
        println("当前it:${it}")
    }
    println("this point is unreachable")
}

fun fooCopy() {
    val listOf = listOf(1, 2, 3, 4, 5)
    fun x(list: List<Int>) {
        list.forEach {
            if (it == 3) return // 非局部直接返回到 x() 的调用者,相当于返回到了44行。
            println("当前it:${it}")
        }
    }
    x(listOf)
    println("this point is unreachable")
}

fun foo1() {
    listOf(1, 2, 3, 4, 5).forEach {
        // 局部返回类似于在常规循环中使用 continue。并没有 break 的直接等价形式
        if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环。
        println("当前it:${it}")
    }
    println(" done with implicit label")
}

fun foo2() {
    // 我们用一个匿名函数替代 lambda 表达式
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
        //  局部返回类似于在常规循环中使用 continue。并没有 break 的直接等价形式
        if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环
        println("当前it:${value}")
    })
    println(" done with anonymous function")
}

// 通过增加另一层嵌套 lambda 表达式并从其中非局部返回
fun foo3() {
    run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
            println("当前it:${it}")
        }
    }
    println(" done with nested loop")
}