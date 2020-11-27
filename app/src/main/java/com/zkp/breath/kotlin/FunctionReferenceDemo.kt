package com.zkp.breath.kotlin

/**
 * 函数类型：是一类类型，不是一种类型，因参数类型，返回值类型不同而不同。
 *
 * 高阶函数：参数或者返回值为函数类型的函数。
 *
 * 函数引用（Function Reference）：函数作为参数的本质是因为函数在kotlin里可作为对象存在，因为只有对象才能被作为
 * 参数传递，赋值也是一样道理，只有对象才能被赋值给变量。kotlin使用使用“::函数名”其实创建了一个FunctionX的实例，
 * 内部创建了一个和该函数结构一样的仿造函数，该仿造函数内部调用了真正的函数，这相当于一种代理思想。
 *
 * 匿名函数（无名字的函数，本质就是函数引用，因为也能作为参数传递给函数或者赋值给变量）：要传一个函数类型的参数，或者把一个函数
 * 类型的对象赋值给变量，除了用双冒号来拿现成的函数使用，你还可以直接把这个函数挪过来写，但这两种方式只能在函数中声明。如下例子：
 *
 * lambda表达式，本质也是函数引用，因为也能作为参数传递给函数或者赋值给变量（和java的不一样）：
 * 1. 函数中的lambda表达式参数可以使用匿名函数赋值传递（因为lambda表达式和匿名函数本质上都属于同一种类型：FunctionX）
 * 2. 函数有且只有一个Lambda函数类型的参数，你可以把 Lambda 写在括号的外面，还可以把括号去掉。(语法糖，也叫闭包）
 * 3. 如果这个 Lambda 是单参数的，无论是否有使用都可以省略这个参数，因为 Kotlin的 Lambda 对于省略的唯一参数有默认
 *    的名字：it。
 * 4. Lambda  的返回值不是用 return 来返回，而是直接取最后一行代码的值。
 *
 *
 * 总结：
 * 1.在 Kotlin 里「函数并不能传递，传递的是对象」和「匿名函数和 Lambda 表达式其实都是对象」。
 * 2. Java 8 的 Lambda 只是一种便捷写法，本质上并没有功能上的突破，而 Kotlin 的 Lambda 是实实在在的对象
 * 3. 一旦涉及到函数引用的概念就要知道内部其实就是FunctionX类
 */


fun a(funParam: (Int) -> String): String {
    return funParam(1)
}

fun b1(int: Int) = int.toString()


fun main() {
    b1(1)    // 调用函数
    val kFunction1 = ::b1    // 函数引用，指向函数类型对象的引用
    val kFunction2 = kFunction1 // 赋值操作，因为右边已经是一个函数类型对象，所以不用再加“::”
    kFunction1(1)   // 使用函数引用实现和b函数的等价操作，实际上调用的是这个对象的invoke()函数(一种语法糖)
    kFunction1.invoke(1)
    // 使用函数引用实现和b函数的等价操作，实际上调用的是这个对象的invoke()函数(一种语法糖)。
    (::b1)(1)   // 没有显示调用invoke（）函数，函数引用对象需要添加（）
    (::b1).invoke(1)
    ::b1.invoke(1)
    a(::b1)  // 使用::将方法变成函数引用，引用就是用作参数传递。

    // 匿名函数
    a(fun(param: Int): String {
        return param.toString()
    })

    // 右边能推导出类型，左边的类型声明可省略
    val d = fun(param: Int): String {
        return param.toString()
    }

    // 变量没有声明类型的话则lambda表达式的参数不能省略
    val b1 = { param: Int ->
        param.toString()
    }
    // 变量有声明类型则lambda表达式的参数可以省略，因为依靠左边能推断出类型
    val b2: (Int) -> String = {
        it.toString()
    }

    // 如果 Lambda 是函数的最后一个参数，你可以把 Lambda 写在括号的外面，还可以把括号去掉。
    a { i -> i.toString() }
    // 如果这个 Lambda 是单参数的，无论是否有使用都可以省略，默认用it代替
    a { it.toString() }
    a { "我们" }

}
