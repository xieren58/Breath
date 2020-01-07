package com.zkp.breath.kotlin

interface AS {
    fun ss() {

    }
}

interface AS2 {
    fun ss() {}
}

// 知识点4
class AsImp : AS, AS2 {
    // 实现的接口存在相同方法一定要重写
    override fun ss() {
        // 调用父类的方法
        super<AS>.ss()
    }
}

// 知识点5
class AsImp2 : AS {
    override fun ss() {
        super.ss()
    }
}

// 知识点5
fun main(args: Array<String>) {


    val b = B1()
    b.a = "你们形式上"   // 在1和2的声明方式下打印出来的还是A的值
    println(b.a)

}

interface A1 {
    var a: String
        get() = "我们"
        //  Property in an interface cannot have a backing field，一个接口不能有幕后字段的功能
        // var属性提供了get，则要提供set，如果不提供set的话会编译器认为是有幕后字段，而接口的属性的不能有幕后字段的
        set(value) {}

    val bb: String
        get() = "你们"
}

class B1 : A1 {
    //   as自动提供get/set，不能提供初始值，否则会编译器报错
//    override var a: String
//        get() = super.a
//        set(value) {}

    override var a: String = ""

    override val bb: String
        get() = super.bb

}