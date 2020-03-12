package com.zkp.breath.kotlin

// 条件控制Demo

fun f1(x: Int) {
    // when 类似其他语言的 switch 操作符。在 when 中，else 同 switch 的 default
    when (x) {
        1 -> print("x == 1")
        2 -> print("x == 2")
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


// 配合is使用，一旦判断成功则自动转换该类型，就能使用该类型的方法或属性
// 下面的方法存在隐式返回值
fun hasPrefix(x: Any) = when (x) {
    is String -> x.startsWith("prefix")
    else -> false
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

