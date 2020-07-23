package com.zkp.breath.kotlin

/**
 * 1.object类其实就是饿汉式线程安全的单例，object修饰的类也可以实现接口；object也可以用来创建匿名类的对象。
 * 2.companion object伴随外部类而存在，一个类中最多只有一个伴生对象。Java 静态变量和方法的等价写法：companion object 中的变量和函数。
 *   其实companion object修饰的类在外部类被加载的时候就随之被实例化，所以实际上还是通过实例去调用方法/变量，虽然说是等价
 *   java的静态变量和静态方法的写法，但实际其实不是，且kotlin没有静态变量和静态方法这两个概念。
 *
 *
 * 在实际使用中，在 object、companion object 和 top-level 中应如何选择：
 * 1.如果想写工具类的功能，直接创建文件，写 top-level「顶层」函数。推荐
 * 2.如果需要继承别的类或者实现接口，用companion object或者object，但是object是一个单例还是不要泛滥使用，
 *  而companion object的使用是与外部类存在某种关联才去使用。
 *
 * 常量（编译时常量）：
 * 1.Kotlin 的常量（const val）必须声明在对象（包括伴生对象）或者「top-level 顶层」中，因为常量是静态的。
 * 2.Kotlin 中只有基本类型和 String 类型可以声明成常量（防止其他类型实例后对内部的变量进行修改）。java的常量没有
 * 限制类型，自定义类的常量还是能对内部的值进行修改，所以是一种伪常量，而kotlin因为限制了类型所以不可修改也就不存在
 * 可修改这种问题。
 *
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
 * java的饿汉式单例
 *
 * public class A {
 * private static A sInstance;
 *      public static A getInstance() {
 *      if (sInstance == null) {
 *      sInstance = new A();
 *      }
 *      return sInstance;
 *      }
 *  ...
 *  }
 *
 *  kotlin的单例
 *
 *  object A {
 *      val number: Int = 1
 *      fun method() {
 *          println("A.method()")
 *      }
 *  }
 *
 * 这种通过 object 实现的单例是一个饿汉式的单例，并且实现了线程安全，和 Java 相比的不同点有：
 * 和类的定义类似，但是把 class 换成了 object 。
 * 不需要额外维护一个实例变量 sInstance。
 * 不需要「保证实例只创建一次」的 getInstance() 方法。
 */


// object关键字：创建了一个类，并且创建一个这个类的对象。 在代码中如果要使用这个对象，直接通过它的类名就可以访问。
object Sample {
    val name = "A name"
}


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

// object定义的类可以继承类和实现接口
object Three : One(20), Two {
    override fun towFunction() {
    }
}

// ===================================
// ===================================

interface TempI

/**
 * 伴生对象支持@JvmStatic,@JvmField:
 * 他们作用主要是为了在Kotlin伴生对象中定义的一个函数或属性，能够在Java中像调用静态函数和静态属性那样类名.函数名/属性名方式调用，
 * 让Java开发者完全无法感知这是一个来自Kotlin伴生对象中的函数或属性。如果不加注解那么在Java中调用方式就是类名.Companion.函数名/属性名。
 * 你让一个Java开发者知道Companion存在，只会让他一脸懵逼。
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
    companion object X : TempI {
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
