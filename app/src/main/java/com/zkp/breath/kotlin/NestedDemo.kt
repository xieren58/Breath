package com.zkp.breath.kotlin


class NestedClass {

    // 嵌套类，相当于java的静态内部类
    class StaticClass
}

// 知识点2
class Outer {
    private val bar: Int = 1
    var v = "成员属性"

    /**嵌套内部类，相当于成员内部类**/
    inner class Inner {
        fun foo() = bar  // 访问外部类成员
        fun innerTest() {
            // 为了消除歧义，要访问来自外部作用域的 this，我们使用this@label，其中 @label 是一个 代指 this 来源的标签。
            var o = this@Outer //获取外部类的成员变量
            println("内部类可以引用外部类的成员，例如：" + o.v)
        }
    }
}


open class Foo {
    open fun f() {
        println("Foo.f()")
    }

    open val x: Int get() = 1
}

class Bar : Foo() {
    override fun f() {
    }

    override val x: Int get() = 0

    inner class Baz {
        fun g() {
            /**
             * this@Bar ，获取外部类的对象后调用其成员
             */
            val bar = this@Bar
            bar.f()
            bar.x

            /**
             *  super@Bar 获取外部类的父类对象后调用其成员
             */
            super@Bar.f()
            super@Bar.x
        }
    }
}

// 知识点3
class Test {
    var v = "成员属性"
    // 成员匿名内部类
    val ss = object : TestInterFace {
        override fun test() {
            // 获取外部对象后调用外部成员
            var zz = this@Test
            print(zz.v)

            // 个人感觉就是类似上面的写法，只是帮我们自动转换了而已
            print(v)
        }

        // 匿名内部类声明的方法，只能匿名内部类内部调用，外部无法调用。这点和java一样。
        fun xxx() {

        }
    }

    fun setInterFace(test: TestInterFace) {
        test.test()
    }

    fun function() {
        // 方式1，成员匿名内部类
        setInterFace(ss)
        // 方式2，局部匿名内部类，采用对象表达式来创建接口对象，即匿名内部类的实例。
        setInterFace(object : TestInterFace {
            override fun test() {

            }
        })

        val listener = object : TestInterFace {
            override fun test() {

            }
        }
        // 方式3，局部匿名内部类（其实和方式2一样，只是用一个引用指向）
        setInterFace(listener)

        // 方法4,直接调用
        // 匿名内部类其实就是一个已经实例化的对象，而此时这个对象又是成员变量，那么可以调用其内部方法
        ss.test()
//        ss.xxx()    // 匿名类自定义的方法只能内部自己调用，外部无法调用。这点和java一样
    }
}

/**
 * 定义接口
 */
interface TestInterFace {
    fun test()
}


fun main(args: Array<String>) {
    // 知识点2
    val demo = Outer().Inner().foo()
    println(demo) //   1
    val demo2 = Outer().Inner().innerTest()
    println(demo2)


    // 创建嵌套类的实例（相当于java的静态内部类）
    val staticClass = NestedClass.StaticClass()

}