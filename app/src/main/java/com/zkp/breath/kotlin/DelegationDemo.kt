package com.zkp.breath.kotlin

// 代理类
interface S {
    fun getTask()
}

class MI(name: String) : S {
    override fun getTask() {
        println("子类重写")
    }
}

// 其实相当于java的代理模式，Proxy实现了S，但是因为使用了by关键字，所以默认需要重写方法都又编译器帮我们实现
// 而这些方法内部调用的其实就是传入的对象的同名方法（因为传入的对象的类也是实现了同一个接口）。
// by 子句表示 one 会被存储在所有的 Proxy 对象的内部， 编译器会生成 S 的所有方法，这些方法会跳向 one。
class Proxy(one: S) : S by one

fun main(args: Array<String>) {
    val one = MI("小明")
    Proxy(one).getTask()
}