package com.zkp.breath.kotlin.classz

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
        in 1..10 -> print("x is in the range")
        in 30..40 -> print("x is valid")
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

// =======================================
// =======================================

open class Person {
    fun p1() {
        println("person的p1方法")
    }
}

class Man : Person() {
    fun s1() {
        println("Man的s1方法")
    }
}

fun classCheck(p: Any) {
    when (p) {
        is Person -> p.p1()
        is Man -> p.s1()
        else -> println("哈哈")
    }
}

fun main() {

    // 因为类型指定为父类，所以无法执行子类的方法
    val pp: Person = Man()
    // 强转为子类，就能执行子类特有的方法
    val pp2 = pp as Man
    pp2.s1()

    // 可空类型（装箱类型）
    val ppNull: Person? = Man()
    // 强转为指定类型（拆箱）
    val ppNull2 = ppNull as Man
    ppNull2.s1()

    val nullP: Person? = null
    val nullPP2 = nullP as Man  // null 不能强转为任何类型
    nullPP2.s1()        // 报异常，程序奔溃


    // 最安全的写法
    val man: Person = Man()
    // 强转失败则会返回null，所以一定要指定类型则类型还要可空
    val man2: Man? = man as? Man
    // 防止null异常写法，推荐
    man2?.s1()
}

