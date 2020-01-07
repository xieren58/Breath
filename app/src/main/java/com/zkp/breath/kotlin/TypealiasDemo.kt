package com.zkp.breath.kotlin

// 类型别名Demo


// 普通类使用别名
// 别名为Temp
typealias Temp = ADSASAD_FADASDASDASDASDASDA

class ADSASAD_FADASDASDASDASDASDA

// 泛型类使用类型别名例子
class MyType<U, T>(a: U, b: T) {
    val a1 = a
    val b1 = b
}
// 已指定泛型类型，可以有效避免在多次创建指定泛型类型带来的声明过长的问题
// val s: MyType<String, Int> = MyType("", 1)   // 声明比较长
// val s1 = MyType("", 1)   // 自动推断类型，推荐写法
typealias UType = MyType<String, Int>

// 剩下一个泛型类型给用户指定
typealias TType<U> = MyType<U, Int>


// ======================================================
// ======================================================

// 顶层匿名函数，lambd表达式
var x = { s: Int, b: Int -> s + b }

// 函数类型使用别名
typealias fff = (Int, Int) -> Unit

// 本函数的参数一是函数类型，使用别名替代
fun methodTest2(f: fff, a: Int, b: Int) {
    f(a, b)
}

fun methodTest(f: (Int, Int) -> Unit, a: Int, b: Int) {
    // 本方法的函数类型
    f(a, b)

    // 顶层函数
    println(x(1, 2))

    // 本地匿名函数
    val x2 = { a1: Int, b1: Int -> a1 + b1 }
    print("感觉一样" + x2(1, 2))
}

// ===============================================
// ===============================================

typealias methodTT<U, T> = (U, T) -> Unit

fun <U, T> methodT(f: (U, T) -> Unit, a: U, b: T) {
    f(a, b)
}

// ===============================================
// ===============================================

// 普通定义方式
// 参数类型为函数类型
fun <T> foo(f: (T) -> Boolean) {

}

// ==============使用类型别名的方式定义==============
// ==============使用类型别名的方式定义==============

// 函数类型的参数为泛型的类型别名定义
typealias t1<T> = (T) -> Boolean

// 函数类型的参数为指定Int的类型别名定义(注意：t2后面不用<Int>,否则typealias t2<Int> = t1<Int>会被认为是泛型，编译不通过)
typealias t2 = t1<Int>

// 函数类型的参数为指定String的类型别名定义
typealias t3 = t1<String>


fun fooT1(t: t1<Int>) {

}

fun fooT2(t: t2) {

}

fun fooT3(t: t3) {

}

