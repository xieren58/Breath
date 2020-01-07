package com.zkp.breath.kotlin

/**
 * 强转Demo
 */

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
