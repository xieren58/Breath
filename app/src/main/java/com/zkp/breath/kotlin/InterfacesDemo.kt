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

interface A1 {
    var a: String
        get() = "我们"
        set(value) {}

    val bb: String
        get() = "你们"
}

class B1 : A1 {

    override var a: String = ""

    override val bb: String
        get() = super.bb

}

// 知识点5
fun main(args: Array<String>) {
    val b = B1()
    b.a = "你们形式上"   // 在1和2的声明方式下打印出来的还是A的值
    println(b.a)

}

