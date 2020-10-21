package com.zkp.breath.kotlin

/**
 * 1.object修饰一个类，则该类就是（线程安全）的饿汉式的单例；也可以用来创建一个匿名内部类的实例。
 * 2.companion object：伴生对象，一个类中最多只有一个伴生对象。其实就是一个默认类名为Companion的final静态内部类
 * （ private默认构造方法，只有一个特定的kotlin编译器调用的public构造方法，所以我们不能人为创建实例），外部类持有
 * Companion类实例的变量，所以外部类可以访问Companion的方法或者变量。companion object就等价java的静态变量和静
 * 态方法的写法，但实际其实不是，且kotlin没有静态变量和静态方法这两个概念。
 *
 * 常量（编译时常量）：
 * 1.Kotlin 的常量（const val）必须声明在object类，companion object，「top-level 顶层」这三者其一中。
 * 2.Kotlin 中只有基本类型和 String 类型可以声明成常量（防止其他类型实例后对内部的变量进行修改）。java的常量没有
 * 限制类型，自定义类的常量还是能对内部的值进行修改，所以是一种伪常量，而kotlin因为限制了类型所以不可修改也就不存在
 * 可修改这种问题。
 *
 *  // java的伪常量的例子
 * public class User {
 *      int id; // 👈 可修改
 *      String name; // 👈 可修改
 *      public User(int id, String name) {
 *          this.id = id;
 *          this.name = name;
 *      }
 *  }
 *
 *  // 虽然这个常量不能二次赋值，但是可以修改其内部的成员变量的值
 *  static final User user = new User(123, "Zhangsan");
 *  user.name = "Lisi";
 *
 */


/**
 * 饿汉式单例，java的饿汉式demo：EagerSingleton
 *
 * 和 Java 相比的不同点有：
 * 1. 和类的定义类似，但是把 class 换成了 object。
 * 2. 不需要额外维护一个实例变量 sInstance。
 * 3. 不需要「保证实例只创建一次」的 getInstance() 方法。
 */
object EagerSingletonKt {
    val name = "A name"
}

/**
 * 双重校验锁式的懒汉式单例， java的饿汉式demo：LazySingleton
 *
 * 1. 私有化构造函数
 * 2. companion object静态方式调用
 * 3. lazy标准函数创建对象，mode指定线程安全模式。
 */
class LazySingletonKt private constructor() {
    companion object {
        val instance: LazySingletonKt by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LazySingletonKt()
        }
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

// object定义的类可以继承类和实现接口
object Three : One(20), Two {
    override fun towFunction() {
    }
}

// ===================================
// ===================================

/**
 * 伴生对象支持@JvmStatic,@JvmField:
 *
 * 他们作用主要是为了在Kotlin伴生对象中定义的一个函数或属性，能够在Java中像调用静态函数和静态属性那样类名.函数名/属性名
 * 方式调用，让Java开发者完全无法感知这是一个来自Kotlin伴生对象中的函数或属性。如果不加注解那么在Java中调用方式就是
 * 类名.Companion.函数名/属性名。你让一个Java开发者知道Companion存在，只会让他一脸懵逼。
 */
class Four {
    companion object {
        // 静态变量
        var s: String = "伴生对象的变量"
        const val CONST_NUMBER = 1
        val s2: String = "ss"

        fun function() {
            println("伴生对象的方法")
        }

        @JvmField
        val sJvmField = ""

        @JvmStatic
        fun functionJvmStatic() {

        }
    }
}

/**
 * Java 中的静态变量和方法，在 Kotlin 中都放在了 companion object 中。因此 Java 中的静态初始化在 Kotlin
 * 中自然也是放在 companion object 中的，像类的初始化代码一样，由 init 和一对大括号表示：
 */
class Five {
    companion object X {
        // 静态初始化，相当于java的static代码块，只会执行一次
        init {
            println("伴生对象静的态初始化init{}")
        }

        fun function() {
            println("伴生对象的方法")
        }
    }
}

fun main() {
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

}
