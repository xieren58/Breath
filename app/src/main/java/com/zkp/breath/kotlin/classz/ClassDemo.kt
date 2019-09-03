package com.zkp.breath.kotlin.classz

// 编译期常量,相当于java的静态常量
// 没有自定义 getter (即默认隐式get访问器)
const val CONST = 22

class Demo {
    var i: Int = 2
        set(value) {
            field = field + value
        }
        get() = field + 1
    val s
        get() = i == 3

    // 延迟初始化属性
    // 关键字lateinit,只能用于类体中（不是在主构函数中）声明的var变量，
    // 并且仅当该属性没有自定义get和set时，必须是非空类型，不能是原生类型。
    lateinit var lateinitStr: String
}