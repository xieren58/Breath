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

/**
 * 接口中的属性只能是抽象的，不允许初始化值
 */
interface A1 {
    var a: String
        get() = "我们"
        set(value) {}

    val bb: String
        get() = "你们"

    var name: String
}

class B1 : A1 {

    override var a: String = ""

    override val bb: String
        get() = super.bb

    // 父类的name没有重写get，所以这里不能调用super.name，因为这样是无效的，相当于无重写
    override var name: String = ""
}

// 知识点5
fun main(args: Array<String>) {
    val b = B1()
    b.a = "你们形式上"   // 在1和2的声明方式下打印出来的还是A的值
    println(b.a)

}

