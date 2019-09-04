package com.zkp.breath.kotlin.classz

// 知识点2
class Outer {
    private val bar: Int = 1
    var v = "成员属性"
    /**嵌套内部类**/
    inner class Inner {
        fun foo() = bar  // 访问外部类成员
        fun innerTest() {
            var o = this@Outer //获取外部类的成员变量
            println("内部类可以引用外部类的成员，例如：" + o.v)
        }
    }
}

// 知识点3
class Test {
    var v = "成员属性"
    // 成员匿名内部类
    val ss = object : TestInterFace {
        override fun test() {
            //var zz = this@Test
            // print(zz.v)
            print(v)  // 个人感觉就是类似上面的写法，只是帮我们自动转换了而已
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        // 匿名内部类声明的方法，只能匿名内部类内部调用，外部无法调用
        fun xxx() {

        }
    }

    fun setInterFace(test: TestInterFace) {
        test.test()
    }

    fun function() {
        // 方式1，成员匿名内部类
        setInterFace(ss)
        // 方式2，局部匿名内部类
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

    }
}

/**
 * 定义接口
 */
interface TestInterFace {
    fun test()
}

// 知识点4
open class Foo {
    open fun f() { println("Foo.f()") }
    open val x: Int get() = 1
}

class Bar : Foo() {
    override fun f() { /* …… */ }
    override val x: Int get() = 0

    inner class Baz {
        fun g() {
            super@Bar.f() // 调用 Foo 实现的 f()
            println(super@Bar.x) // 使用 Foo 实现的 x 的 getter
        }
    }
}


fun main(args: Array<String>) {
    // 知识点2
    val demo = Outer().Inner().foo()
    println(demo) //   1
    val demo2 = Outer().Inner().innerTest()
    println(demo2)

}