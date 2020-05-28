package com.zkp.breath.kotlin

import android.content.res.Resources
import android.util.TypedValue

/**
 * 扩展：给已有的类去额外添加函数和属性，而且既不需要改源码也不需要写子类。
 *
 * 扩展函数：
 * 1.顶层扩展函数：并不属于任何一个类，只属于所在的package，只是限制只能通过某个类的对象才能调用。
 * 2.成员扩展函数：既是外部类的成员函数，又是前缀类的扩展函数，只是由于它同时还是成员函数，所以只能在它所属的类里面被调用，
 *   到了外面就不能用了。
 * 3.顶层扩展函数和成员扩展函数区别在于可见性。
 * 4.顶层扩展函数允许使用函数引用的调用方式，而成员扩展函数不允许。
 *
 *
 * 一般的扩展函数或扩展属性我们都定义在顶层（扩展可以视为一种工具方法/属性）
 */

open class H {
    open fun p() = println("H.p")
}

class J : H() {
    override fun p() = println("J.p")
}

// =====================================================
// =====================================================

open class Particle

class Electron : Particle()

/**
 * 分发接收者类型是虚拟的（如果子类重写父类的方法，那么会调用子类的重写扩展函数），
 * 扩展接收者静态解析（方法参数声明类型是什么类型就是什么类型，与实际接收的实例类型无关）
 */
open class Element(val name: String = "") {

    //  三个方法的名字都是一致的，但是三个方法隶属的类是不同的，所以不会冲突

    open fun Particle.react(name: String) {
        println("$name 与粒子发生反应")
    }

    open fun Electron.react(name: String) {
        println("$name 与电子发生反应")
    }

    fun react(p: Particle) {
        p.react(name)   // 静态解析的，和接收的参数的实例的类型无关
    }
}

open class NobleGas(name: String) : Element(name) {

    override fun Particle.react(name: String) {
        println("$name 与粒子发生反应")
    }

    override fun Electron.react(name: String) {
        println("$name 与电子发生反应")
    }

    // 重载，因为父类已经有了一个同名方法
    fun react(p: Electron) {
        p.react(name)
    }
}

open class M
class N : M()

fun M.p() = println("M.p")
fun N.p() = println("N.p")


// =====================================================
// =====================================================

class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

/**
 * 类内声明其他类的扩展函数。
 * 扩展声明所在的类的实例称为分发接收者（Connection），调用扩展方法的类的实例称为扩展接收者（Host）
 */
class Connection(val host: Host, val port: Int) {

    fun printPort() {
        print(port)
    }

    fun printHostname() {}

    fun Host.printConnectionString() {
        // 调用 Host.printHostname()，因为这里相当于this.printHostname()，而this指代调用者即Host对象
        printHostname()
        // 调用 Connection.printPort()，因为这里相当于this@Connection.printPort（）
        printPort()
        // 分发接收者和扩展接收者同名函数情况下，调用分发接收者的方法加上： this@类名
        this@Connection.printHostname()
    }

    fun connect() {
        host.printConnectionString()   // 调用扩展函数
    }
}

// =====================================================
// =====================================================

class D {
    fun bar() {
        println("D bar")
    }
}

class C2 {
    fun baz() {
        println("C baz")
    }

    fun bar() {
        println("C bar")
    }

    fun D.foo() {
        bar()   // 输出”D bar“。相当于this.bar()，该函数为扩展函数，所以this为接受者类型，即为D。
        baz()   // 输出”C.baz“。相当于this@C2.baz()
        this@C2.bar()  //   输出”C.bar“。本类存在和接收者类型所在类同名方法，如果要调用自身的方法，一定要加this@本类类名.方法名()
    }

    fun caller(d: D) {
        d.foo()   // 调用扩展函数
    }
}

// =====================================================
// =====================================================

open class D3 {
}

class D1 : D3() {
}

open class C3 {
    open fun D3.foo() {
        println("D.foo in C")
    }

    open fun D1.foo() {
        println("D1.foo in C")
    }

    fun caller(d: D3) {
        d.foo()
    }
}

class C4 : C3() {
    override fun D3.foo() {
        println("D.foo in C1")
    }

    override fun D1.foo() {
        println("D1.foo in C1")
    }
}

// =====================================================
// =====================================================

class MyClass {

    /**
     * 1.因为伴生对象是在外部类实例化的时候才去实例化，所以伴生对象是不能调用外部类的成员方法或者属性的
     * 2.伴生对象可以有扩展函数
     */
    companion object {

        val myClassField1: Int = 1
        var myClassField2 = "this is myClassField2"

        fun companionFun1() {
            println("this is 1st companion function.")
            // 伴生对象相当于java的静态成员（实际不是），所以这里看成静态方法，静态方法不能调用成员方法， 所以这里调用的是顶层方法
            foo()
        }

        // 伴生对象的成员函数
        fun companionFun2() {
            println("this is 2st companion function.")
            companionFun1()
        }
    }

    // 主构函数方法体
    init {
        test2()
    }

    // 成员函数
    fun test2() {
        /**
         * 注意：
         * 1.类内的其它函数优先引用类内扩展的伴随对象函数，即对于类内其它成员函数来说，类内扩展屏蔽类外扩展.
         * 2.类内的伴随对象扩展函数只能被类内的函数调用。
         */
        // 下面两种写法都可以
        MyClass.foo()
        Companion.foo()
    }

    // 类内伴生对象扩展函数
    // 基于伴生对象依赖外部类，伴生对象的扩展函数也只能外部类才能调用。
    fun Companion.foo() {
        println("伴随对象的扩展函数（内部）")
    }
}

// 顶层伴生对象的扩展函数
fun MyClass.Companion.foo() {
    println("伴随对象的扩展函数")
}

// 顶层伴生对现象的扩展变量
val MyClass.Companion.no: Int
    get() = 10


// =====================================================
// =====================================================

fun String?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，调用不需要添加?.
    return toString()
}

// ======================================================
// ======================================================

// 扩展属性
// 扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中，扩展属性只能被声明为 val,扩展属性不能有初始化器，没有后端字段field。
val <T> List<T>.cusLastIndex: Int
    get() = size

//  dp转px
val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )

val a: (Int) -> Unit = { println("$it") }

// 把扩展函数的引用赋值给变量
val a1: String.(Int) -> Unit = String::method1

// 这种写法也是合法的，知道左边的调用方式即可。
val b: (String, Int) -> Unit = String::method1
val a2: String.(Int) -> Unit = {
    println("调用者:$this")
    println("参数:$it")
}
val a3: String.(Int) -> Unit = { i: Int ->
    println("参数:$i")
}


// 存在Receiver的函数引用可以赋值给没有或者有Receiver的变量。
val ax: String.(Int) -> Unit = String::method1
val bx: (String, Int) -> Unit = String::method1
val cx: String.(Int) -> Unit = bx
val dx: (String, Int) -> Unit = ax

val e: (String, Int) -> Unit = ::method2
val f: String.(Int) -> Unit = ::method2

// ======================================================
// ======================================================

fun String.method1(i: Int) {
    println(i)
}

fun method2(s: String, i: Int) {

}

fun main(args: Array<String>) {
    val ele: H = J()
    ele.p()     // 输出"J.p",动态解析就是和java的多态一样
    println()

    // =====================================================
    // =====================================================

    val al = Element("铝")
    al.react(Particle())    // 铝和粒子发生反应
    al.react(Electron()) // 铝和粒子发生反应
    println()

    val a2 = NobleGas("氢")
    a2.react(Particle())    // 调用的是父类的方法
    a2.react(Electron())  // 调用的是重载了父类方法的重载方法
    println()

    val n = N()
    n.p()   // n的类型为N

    val m: M = N()  // 多态
    m.p()   // 扩展函数静态解析

    // =====================================================
    // =====================================================

    val c: C2 = C2()
    val d: D = D()
    c.caller(d)
    println()

    // =====================================================
    // =====================================================

    C3().caller(D3())   // 输出 "D.foo in C"
    C4().caller(D3())  // 输出 "D.foo in C1" —— 分发接收者虚拟解析
    C3().caller(D1())  // 输出 "D.foo in C" —— 扩展接收者静态解析
    println()

    // =====================================================
    // =====================================================

    // 调用的是扩展函数，因为我们声明为可null类型。（和重载的含义相似）
    val t: String? = ""
    println(t.toString())

    // =====================================================
    // =====================================================

    println("no:${MyClass.no}")
    // 类内的伴随对象扩展函数只能被类内的函数引用，不能被类外的函数和伴随对象内的函数引用；
    // 所以这里调用的是顶层伴生对象扩展函数
    MyClass.foo()
    MyClass.companionFun2()


    val cusLastIndex = arrayListOf<String>("1", "2").cusLastIndex

    // 以下都调用方式都相互等价
    (String::method1)("rengwuxian", 1)  // 如果是类名的方式则参数首位一定是调用者
    String::method1.invoke("rengwuxian", 1)
    "".method1(1)
    (""::method1)(1)
    (""::method1).invoke(1)
    ""::method1.invoke(1)

    "rengwuxian".a1(1)
    a1("rengwuxian", 1)
    a1.invoke("rengwuxian", 1)


    //    "rengwuxian".method2(1) // 不允许调用，报错
    val f: String.(Int) -> Unit = ::method2
    "rengwuxian".f(1) // 可以调用

    "rengwuxian".method1(1) // 可以调用
    val b: (String, Int) -> Unit = String::method1
//    "rengwuxian".b(1) // 不允许调用，报错
}