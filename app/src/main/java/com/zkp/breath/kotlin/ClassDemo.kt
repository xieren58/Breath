package com.zkp.breath.kotlin

import android.annotation.SuppressLint

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

class ClassDemo {

    val s: String = ""
    // 1.要么声明的时候赋初始值
    // 2.要么可以放在init()中赋值，但as提示还是直接赋值
    var i: Int = 1

    // 方法
    // 方法声明：fun关键字 方法名（方法参数）{}
    fun f() {

    }

    fun f2(s: String) {

    }
}

// 无类体省略了花括号'{}',类体即java中的成员函数和成员变量，而在kotlin中成员变量也叫做属性
class ClassDemo2


// ‘constructor’关键字加构造参数表示主构造函数
// 方法参数表示‘（参数名 : 参数类型）’
class ClassDemo3 constructor(s: String) {

}

// 没有任何注解或者可见性修饰符可以省略关键字‘constructor’
class ClassDemo4(s: String) {

}

// 可见修饰符放在关键字‘constructor’前面
class ClassDemo5 private constructor(s: String) {

}

// 多个次构造函数（类似于java的方法重构）
// 没有主构函数，不需要在次构函数后面加this()去表示调用主构函数
class ClassDemo6 {
    constructor(s: String)
    constructor(i: Int)
    constructor(s: String, i: Int)
}

class ClassDemo7 constructor(s: String) {
    // 存在主构的话则次构函数在参数后‘: this（主构函数参数）’表示间接调用主构函数
    constructor(i: Int, s: String) : this(s) {

    }
}

// 主构函数参数存在默认值
class ClassDemo8 constructor(s: String = "默认值") {
    // this()括号中可以不传值
    constructor(i: Int, s: String) : this() {

    }
}


class ClassDemo9 constructor(s: String) {

    // 主构函数的参数可以赋值给属性
    val n: String = s
    var n1: String = s

    // val和var修饰的属性必须声明的时候就初始化或者在init()中初始化（且在init()初始化的属性必须指明数据类型）
    val n2: String
    var n3 = ""
    val n4: String? = ""
    var n5: String?

    // 初始化代码块（相当于主构函数的方法体）
    // 主构函数的参数可以在此代码块中出现
    init {
        n2 = ""
        n5 = ""

        println("初始化代码块")
        // (知识点)字符串模板 : $符号后面加上属性/变量
        // 字符串模板相当于java中的打印信息引用变量的形式。即： println("调用主构函数的参数s:" + s)
        println("调用主构函数的参数s:$s")
    }

    constructor(i: Int) : this("主构函数参数值") {
        println("次构造函数")
    }
}

class Demo13 {

    var s: String = "哈哈"
    var s1: String
    lateinit var s2: String
    val s3: String = ""

    // 提供get/set方法必须马上初始化，不允许在init（）中初始化。因为get/set方法都需要知道该属性的类型
    var s4: String = ""
        get() = field.toUpperCase()
        set(value) {
            if (value == "哈哈") {
                field = "大于"
            } else {
                field = "小于"
            }
        }

    val s5: String = "wo"
        get() = field.toUpperCase()


    init {
        s1 = "你好s2"
    }

}

fun main(args: Array<String>) {
    // 知识点
    // kotlin创建对象：val/var修饰符  变量名 = 类名（主构或者次构参数值）。
    // java创建对象： private/public修饰符 变量名 = new关键字 类名（构造函数参数值）。会java的同学一看就感觉和kotlin创建对象的方式其实很像
    val classDemo9 = ClassDemo9(1)

    val demo13 = Demo13()
    println("demo13的属性s4：${demo13.s4}")
}
