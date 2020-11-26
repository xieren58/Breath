package com.zkp.breath.kotlin

// 条件控制Demo

fun f1(x: Int) {
    // when 类似其他语言的 switch 操作符。
    // 这里与 Java 相比的不同点有：
    // 省略了 case 和 break，Kotlin 自动为每个分支加上了 break 的功能。
    // Java 中的默认分支使用的是 default 关键字，Kotlin 中使用的是 else。
    when (x) {
        1 -> print("x == 1")
        2 -> {
            print("x == $x")
        }
        else -> { // 注意这个块
            print("x 不是 1 ，也不是 2")
        }
    }
}

fun f2(x: Int) {
// 如果很多分支需要用相同的方式处理，则可以把多个分支条件放在一起，用逗号分隔
    when (x) {
        0, 1 -> print("x == 0 or x == 1")
        else -> print("otherwise")
    }
}

fun f3(x: Int) {
// 配合in使用
    when (x) {
        // in表示在什么区间/范围
        in 1..10 -> print("x is in the range")
        in 30..40 -> print("x is valid")
        // !in表示不在什么区间/范围
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }
}


// 配合is使用，一旦判断成功则自动转换该类型，就能使用该类型的方法或属性。
// when 也可以作为返回值使用，分支中最后一行的结果作为返回值，需要注意的是，这时就必须要有 else 分支，使得无论怎样都
// 会有结果返回，除非已经列出了所有情况（密封类）
fun hasPrefix(x: Any) = when (x) {
    is String -> x.startsWith("prefix")
    else -> false
}

/**
 * 还可以省略 when 后面的参数，每一个分支条件都可以是一个布尔表达式,哪一个条件先为 true 就执行哪个分支的代码块，
 * 其余条件无论是否为true都不会被执行，因为Kotlin 自动为每个分支加上了 break。
 *
 * 没什么必要的写法！仅仅作为了解
 */
fun condition(str1: String, str2: String) {
    when {
        str1.contains("a") -> print("字符串 str1 包含 a")
        str2.length == 3 -> print("字符串 str2 的长度为 3")
    }
}

fun ifDemo(b: Boolean) {
    //  作为表达式像Java的三元操作符
    val c = if (b) 1 else 2
}

// 默认最后一个表达式是返回值
fun ifDemo2(a: Int, b: Int) = if (a > b) {
    print("choose a")
    a
} else {
    print("choose b")
    b
}

fun ifDemo3(a: Int) {
    if (a in 1..8) {
        print("a在指定区间内")
    }
}



