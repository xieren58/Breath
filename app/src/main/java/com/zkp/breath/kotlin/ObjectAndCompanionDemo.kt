package com.zkp.breath.kotlin

/**
 * 对象和伴生对象
 * 1.伴生对象相当于java工具类的作用
 * 2.对象相当于java匿名内部类，局部匿名内部类的作用，只是kotlin可以继承对象
 */

class ObjectClass {

    // 感觉这几种定义方式都无意义！！！
    // ==========================================
    // ==========================================
    // Private function, so the return type is the annoymouse object type
    private fun foo() = object {
        val x: String = "x"
    }

    // Public function, so the return type is Any
    fun publicFoo() = object {
        val x: String = "X"
    }

    // 返回值为any的，匿名对象的成员也是不可访问的
    fun publicFoo2(): Any {
        return object {
            val x: String = "X"
        }
    }
    // ==========================================
    // ==========================================


    fun bar() {
        val x1 = foo().x        // works
    }
}

// ===================================
// ===================================

open class One(age: Int) {
    var age: Int = age

    open fun oneFunction() {

    }

    fun oneFunctionPrivate() {

    }
}

interface Two {
    fun towFunction()
}

object Three : One(20), Two {
    override fun towFunction() {
    }
}

// ===================================
// ===================================

interface TempI

class Four {
    companion object {
        var s: String = "伴生对象的变量"
        fun function() {
            println("伴生对象的方法")
        }
    }
}

class Five {
    companion object X : TempI {
        fun function() {
            println("伴生对象的方法")
        }
    }
}

fun main() {
    // 知识点2
    // 对象表达式在这里的用法可以理解为继承，object代表一个匿名的类
    // 直接创建一个匿名类的对象然后赋值给变量one
    var one = object : One(20), Two {
        override fun towFunction() {
            println("重写父接口的方法")
        }

        fun newAddFunction() {
            println("新增的方法")
        }
    }

    println("${one.age}")
    one.newAddFunction()

    // 知识点3
    var tempObject = object {
        var s: String = "哈哈"
        fun muFuntion() {
            println("tempObject的方法")
        }
    }

    println(tempObject.muFuntion())

    // 伴生对象的调用方式和java调用静态方法是一样的
    println(Four.function())
    // 可以省略Companion关键字
    println(Four.Companion.function())
    println(Five.function())
    // 可以省略伴生对象名
    println(Five.X.function())

    val objectClass = ObjectClass()
    val publicFoo = objectClass.publicFoo()
    val publicFoo2 = objectClass.publicFoo2()
}
