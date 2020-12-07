package com.zkp.breath.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 类委托和属性委托（任何属性（全局，局部等）都能进行委托）
 * 关键字:by
 */
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

/**
 * 类代理：只有接口才能代理。其实就是java的静态代理模式，将具体实现都指向给主构函数中的参数去实现。其实就是
 * 一种设计模式上的语法糖实现。
 *
 * 其实相当于java的代理模式，Proxy实现了S，但是因为使用了by关键字，所以默认需要重写方法都又编译器帮我们实现
 * 而这些方法内部调用的其实就是传入的对象的同名方法（因为传入的对象的类也是实现了同一个接口）。
 * by 子句表示 one 会被存储在所有的 Proxy 对象的内部， 编译器会生成 S 的所有方法，这些方法会跳向 one。
 *
 */
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
     *  属性代理其实就是代理属性的get/set方法，所以不允许自定义访问器（get/set方法），代理类必须提供 getValue()
     *   函数（以及 var 变量的 setValue()）。
     */
    var p: String by Delegate()

}

/**
 * 注意：其实委托类就是隐式实现包含所需 operator 方法的 ReadOnlyProperty 或 ReadWriteProperty 接口之一
 *
 * 自定义属性的委托对象
 *对于一个只读属性（即 val 声明的），委托必须提供一个名为 getValue 的函数，该函数接受以下参数：
 *thisRef —— 必须与 属性所有者 类型相同或者是它的超类型；（一般都是Any类型，适配效果更好）
 *property —— 必须是类型 KProperty<*> 或其超类型 （一般都是KProperty<*>）
 *new value —— 如果是可变属性(即 var 声明的),则而外提供setValue函数，因为是要赋值给该属性的，所以只能是它同类型或者是它子类
 *
 * 可以不实现ReadWriteProperty或者ReadOnlyProperty，直接重写getValue/setValue方法，但是不推荐。
 */
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Delegate2 : ReadWriteProperty<Int, String> {
    /**
     * 参数一：被代理属性的完整类名@内存地址
     * 参数二：相当于java中的Field类
     */
    override fun getValue(thisRef: Int, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    /**
     * 参数一：被代理属性的完整类名@内存地址
     * 参数二：相当于java中的Field类
     * 参数三：设置给被代理者的值
     */
    override fun setValue(thisRef: Int, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Delegate3 : ReadOnlyProperty<Int, String> {
    override fun getValue(thisRef: Int, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
}

// ================================标准代理======================================
// ================================标准代理======================================
/**
 * lazy() 是一个函数，参数是一个 lambda ，返回值是一个 Lazy<T> 实例，这个实例可以作为实现懒属性的代理：get() 的第
 * 一次调用会执行传给 lazy() 的 lambda 并且记录执行结果，后续的 get() 调用仅仅返回第一次记录的结果。
 *
 * 默认情况下，懒属性的计算是同步（线程同步[LazyThreadSafetyMode.SYNCHRONIZED]）的，如果多个线程都可以初始化则把
 * LazyThreadSafetyMode.PUBLICATION 作为参数传给 lazy() 函数。同步的话初始化只有一次，后续的get（）只会返回第
 * 一次记录的结果；而不同步则每次都会进行初始化。
 *
 * 注意：
 * 1.在未初始化的前提下调用lazy()的返回值Lzay的属性value也会触发初始化流程。
 * 2.lazy函数代理的属性只能是val。（lazy是延迟，懒惰的意思，如果是var那么可以赋值，作用互斥）
 * 3.可以指定线程安全模式。
 */
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

class User {
    /**
     * 可观察属性:监听器会收到有关此属性变更的通知。(不能val修饰的属性，val属性不能赋值)
     * Delegates.observable() 接受两个参数：初始值与修改时处理程序（handler）。 每当我们给属性赋值时会调用该处理
     * 程序（在赋值后执行，相当于一个回调方法），它有三个参数：被赋值的属性、旧值与新值。
     */
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        print("被修改的属性名:${prop.name}")
        println("$old -> $new")
    }

    /**
     * 如果你想截获赋值并“否决”它们，那么使用 vetoable() 取代 observable()。 在属性被赋新值生效之前会调用传递给
     * vetoable 的处理程序，相当于我们定义了一个赋值条件。
     *
     * 常见场景：状态切换。（比如播放音乐，如果当前处于播放状态，那么再设置播放状态是不允许的，返回false）
     */
    var id: String by Delegates.vetoable("<initValue>") { property, oldValue, newValue ->
        print("被修改的属性名为：${property.name}")
        println("$oldValue -> $newValue")
        // 返回true表示允许修改，false表示否决修改（维持旧值）
        newValue == "id1"
    }
}

/**
 * 使用map作为代理对象来存放被委托的属性的值，注意map中的key要和被代理的属性名相同，否则会查不到该属性的值。
 *
 * 常见场景：解析一段json，然后赋值给一个对象。
 */
class Client(map: Map<String, Any?>, mutableMap: MutableMap<String, Any?>) {
    val name: String by map
    val age: Int by map

    var id: String by mutableMap
    var address: String by mutableMap
}


fun main(args: Array<String>) {
    val one = MI("小明")
    val proxy = Proxy(one)
    // 代理类无重写，则调用的是被代理类的
    proxy.getTask()
    // 代理类存在重写，则调用代理类重写的方法
    proxy.printMsg()
    // 虽然代理类重写了msg字段，但是调用的方法没有进行重写，所以调用的还是使用被代理类的方法，而被代理类的方法指向的字段是其本身。
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

    val lazy = lazy { "延迟初始化" }
    val sLazy: String by lazy
    // 在未初始化的前提下获取Lazy类的属性value和调用被代理者都会触发初始化
    val value = lazy.value
    val initialized = lazy.isInitialized()

    println()
    println()

    val user = User()
    user.name = "first"
    user.name = "second"

    println()
    println()

    user.id = "id1"
    println("User的id字段的值：${user.id}")
    user.id = "id2"
    println("User的id字段的值：${user.id}")

    println()
    println()

    // 这也适用于 var 属性，如果把只读的 Map 换成 MutableMap 的话：
    val client = Client(mapOf(
            "name" to "John Doe",
            "age" to 25
    ), mutableMapOf(
            "id" to "我是id",
            "address" to "我是address"
    ))
    println(client.name) // Prints "John Doe"
    println(client.age)  // Prints 25
    println(client.id)  // Prints 25
    println(client.address)  // Prints 25
}