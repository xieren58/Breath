package com.zkp.breath.kotlin

import kotlin.reflect.KProperty

// 代理类，kotlin提供了使用by就能创建一个代理模式。
interface S {
    fun getTask()
    fun printMsg()

    val msg: String
    fun printName()
}

class MI(name: String) : S {
    override fun getTask() {
        println("子类重写")
    }

    override fun printMsg() {
        print("MI类重写")
    }

    override val msg: String = "MI的信息字段"

    override fun printName() {
        println("MI类的printName方法d:{$msg}")
    }
}

// 其实相当于java的代理模式，Proxy实现了S，但是因为使用了by关键字，所以默认需要重写方法都又编译器帮我们实现
// 而这些方法内部调用的其实就是传入的对象的同名方法（因为传入的对象的类也是实现了同一个接口）。
// by 子句表示 one 会被存储在所有的 Proxy 对象的内部， 编译器会生成 S 的所有方法，这些方法会跳向 one。
class Proxy(one: S) : S by one {

    override fun printMsg() {
        println("代理类重写")
    }

    override val msg: String = "Proxy类的信息字段"
}

// ==============================属性代理=============================
// ==============================属性代理=============================

class Example {
    /**
     * val/var <property name>: <Type> by <expression>。by 之后的表达式是代理，因为属性对应的 get()
     * （以及 set()）会被代理到它们的 getValue() 和 setValue() 方法。属性代理无需实现任何接口，
     * 但是他们必须要要提供 getValue() 函数（以及 var 变量的 setValue()）
     */
    var p: String by Delegate()

}

// 自定义代理
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

// ================================标准代理======================================
// ================================标准代理======================================
/**
 * lazy() 是一个函数，参数是一个 lambda ，返回值是一个 Lazy<T> 实例，这个实例可以作为实现懒属性的代理：get() 的第
 * 一次调用会执行传给 lazy() 的 lambda 并且记录执行结果，后续的 get() 调用仅仅返回第一次记录的结果。
 *
 * 默认情况下，懒属性的计算是同步（线程同步[LazyThreadSafetyMode.SYNCHRONIZED]）的，如果多个线程都可以初始化则把LazyThreadSafetyMode.PUBLICATION 作为
 * 参数传给 lazy() 函数。同步的话初始化只有一次，后续的get（）只会返回第一次记录的结果；而不同步则每次都会进行初始化。
 */
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}



fun main(args: Array<String>) {
    val one = MI("小明")
    val proxy = Proxy(one)
    // 代理类无重写，则调用的是被代理类的
    proxy.getTask()
    // 代理类存在重写，则调用代理类重写的方法
    proxy.printMsg()
    // 虽然代理类重写了我们msg字段，但是调用的方法没有进行重写，所以调用的还是使用被代理类的方法，而被代理类的方法指向的字段是其本身。
    proxy.printName()   // MI类的printName方法d:MI的信息字段"
    println("调用代理类自身重写的msg字段：{$proxy.msg}") //Proxy类的信息字段

    println()
    println()

    val e = Example()
    // 调用了p属性的代理Delegate对象的getValue()方法
    println(e.p)
    // 调用了p属性的代理Delegate对象的setValue()方法
    e.p = "NEW"

    println()
    println()

    println(lazyValue)
    println(lazyValue)
}