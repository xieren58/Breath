package com.zkp.breath.kotlin.classz

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

    //  三个方法的名字都是一致的，但是三个方法隶属的类是不同的，所以不会冲突

    override fun Particle.react(name: String) {
        println("$name 与粒子发生反应")
    }

    override fun Electron.react(name: String) {
        println("$name 与电子发生反应")
    }

    fun react(p: Electron) {      // 重载，因为父类已经有了一个同名方法
        p.react(name)
    }
}

open class M
class N : M()

fun M.p() = println("M.p")
fun N.p() = println("N.p")


// =====================================================
// =====================================================


class B2

class A2 {
    // 该类型扩展函数
    fun A2.fa() {
        println("扩展函数fa")
    }

    fun B2.fb() {
        println("扩展函数fb")
    }

    fun fa1() {
        fa()    // 相当于this.fa()，而this就表示fa的接收者类型的实例
    }

    fun fb1(b: B2) {
        b.fb()
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
        baz()   // 输出”C.baz“。相当于this@C.baz()
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
        d.foo()   // 调用扩展函数
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

fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
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

    val m: M = N()
    m.p()   // m的类型为M

    // =====================================================
    // =====================================================

    val aa = A2()
    aa.fa1()
    aa.fb1(B2())
    println()

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

    val t = null
    println(t.toString())   // "null"

}