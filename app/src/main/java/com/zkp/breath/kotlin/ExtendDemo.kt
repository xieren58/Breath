package com.zkp.breath.kotlin


/**
 * 1.子类继承父类时，不能有跟父类同名的变量，除非父类中该变量为 private，或者父类中该变量为 open 并且子类用 override 关键字重写
 */

// 关键字open添加在class关键字前面表示该类是一个可被继承的类（开放类，父类，超类）
open class BaseClass {
    var s = "s"
    val s1 = "s1"
    // 属性添加open关键字表示可被子类重写
    open val s2 = "s2"
    open var s3 = "s3"
    open var s4 = "s4"
    open var s5 = "s5"

    // 方法前添加open关键字表示可被子类重写
    open fun f() {
        println("BaseClass的f()")
    }

    open fun f1() {
        println("BaseClass的f1()")
    }

    fun f2() {
        println("BaseClass的f2()")
    }
}

// 类名后面加上': 父类类名（）'表示一个子类
open class SonClass : BaseClass() {

    // 默认重写表示调用父类
    // val属性表示只可读，所以只重写get方法
    override val s2: String
        get() = super.s2

    // 默认重写表示调用父类
    // var表示可读可写，所以重写了get和set方法
    override var s3: String
        get() = super.s3
        set(value) {}

    // 相当于上面s3的写法，只是上面的写法get/set都需要显示声明，这里的set就不用写
    override var s4: String = super.s4

    override var s5: String = ""

    override fun f() {
        println("SonClass重写的f()")
    }

    // 如果不想被下一任重写则在前面添加final
    final override fun f1() {
        super.f1()
    }
}

class GrandChildClass : SonClass() {
    // 父类的属性有override关键字，也可表示可被重写
    override val s2: String = super.s2

    // 父类的属性有override关键字，也可表示可被重写
    override var s3: String
        get() = super.s3
        set(value) {}

    // 父类的属性有override关键字，也可表示可被重写
    override fun f() {
        super.f()
    }
}

// 不声明主构函数，只存在次构函数
open class BaseClass2 {

    constructor(s: String)

    constructor(s: String, i: Int)
}

// 父类无只存在次构函数，可由子类的次构函数调用，则类头可省略（）
class SonClass2 : BaseClass2 {

    constructor(s: String) : super(s) {
    }
}

// 也能由子类的主构函数调用父类的次构造函
class SonClass3 : BaseClass2("") {

}

// 不显示声明任何构造函数，则默认会自动生成一个无参的构造函数
open class BaseClass3 {

}

// 相当于调用父类的无参构造函数
class SonClass4 : BaseClass3() {

}

// 知识点14
open class A {
    open fun f() {
        print("A")
    }

    fun a() {
        print("a")
    }
}

interface B {
    fun f() {
        print("B")
    } //接口的成员变量默认是 open 的

    fun b() {
        print("b")
    }
}

class C() : A(), B {
    override fun f() {
        super<A>.f()//调用 A.f()
        super<B>.f()//调用 B.f()
    }
}

// 知识点15
// 接口
interface InterA {
    // 接口属性不能有初始值
    val count: Int
}

// 实现接口，接口无构造函数，接口名后省略()
open class BaseA(override val count: Int) : InterA {

}

// 实现接口
class SonA : InterA {
    override var count: Int = 0
}

// 为父类的重写字段赋值
class SonB : BaseA(2) {

    // 重写字段，引用父类的值
    override val count: Int
        get() = super.count
}