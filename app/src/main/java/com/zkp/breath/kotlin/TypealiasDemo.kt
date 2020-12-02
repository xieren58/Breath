package com.zkp.breath.kotlin

// 类型别名Demo

/**
 * 类型别名：使用关键字typealias  别名 = 类名/函数类型声明，其实就相当于我们的乳名，小名。
 * 建议：如果是类的类型别名建议放在该类的所在的文件，如果是函数类型建议单独放一个文件进行复用。
 */


class ADSASAD_FADASDASDASDASDASDA
// 类型别名可以简化过长的命名
typealias Temp = ADSASAD_FADASDASDASDASDASDA

// ======================================================
// ======================================================

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

// 函数类型使用别名，使函数类型可以进行复用而不需要在每个声明处都重新声明，实际应用中可以新建一个文件专门存放
typealias fff = (Int, Int) -> Unit

// 本函数的参数一是函数类型，使用别名替代
fun methodTest2(f: fff, a: Int, b: Int) {
    f(a, b)
}

typealias t1<T> = (T) -> Boolean
typealias t2 = t1<Int>
typealias t3 = t1<String>

fun fooT1(t: t1<Int>) {
}

fun fooT2(t: t2) {
}

fun fooT3(t: t3) {
}


// 参数为泛型的函数类型使用别名
typealias methodTT<U, T> = (U, T) -> String

fun <U, T> methodT(f: methodTT<U, T>, a: U, b: T) {
    f(a, b)
}


fun main() {
    // 类型别名声明的变量的泛形不能省略
    val s: methodTT<String, String> = { s1, s2 -> s1 + s2 }
    val s1: methodTT<String, String> = { s1: String, s2: String -> s1 + s2 }

    methodT(s, "1", "2")
    methodT({ i1, i2 -> i1 + i2 }, "1", "2")
    methodT<String, String>({ i1, i2 -> i1 + i2 }, "1", "2")
}


