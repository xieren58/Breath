package com.zkp.breath.kotlin.classz

// 对象

class ObjectClass {
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

    fun bar() {
        val x1 = foo().x        // works
    }
}

// ===================================
// ===================================

open class One(age: Int) {
    var age: Int = age
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
}
