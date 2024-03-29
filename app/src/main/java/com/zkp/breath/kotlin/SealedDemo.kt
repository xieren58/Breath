package com.zkp.breath.kotlin

/**
 * https://www.bilibili.com/video/av752610456/
 * https://zhuanlan.zhihu.com/p/122244787
 *
 * 密封类Demo，枚举类的扩展，枚举是限制了枚举值只能是定义的那几种，但是值类型是该类的实例，而密封类
 * 是限制类结构体系是有穷的（该类可以有子类，子类可以有多个实例对象），只有配合when表达式才能发挥作用（如果没有写满所有类型as会提示，写满则不用else语句）
 * 理解：一个类可能有多个子类，而我们经常用多态（即父类变量指向子类对象），在判断的时候我们要么尽可能把所有子类都判断才能防止出现遗漏，但万一
 * 那一天别人用了你这个方法，自己又去定义了一个子类，然后传入判断，这时候就相当于进行else语块的判断。而为了防止有人任意继承我们就使用密封类
 * 去限制类的结构体系，表示目前只有这几种子类，别人用的时候就只能传入这几种子类类型；或者实在要创建多一个子类，那么when就会提示你多了一个
 * 子类类型，代码中应该去涵盖这个子类的判断，说到底就是一种 “提示（when）和限制类结构体系的作用”
 *
 * 1. 密封类本身是一个抽象类，所以无法直接实例化()。(as的kotlin bytecode功能编译后的java代码可以看出)
 * 2. 密封类的子类必须声明在和密封类同一文件中。
 * 3. 密封类的子类可以不用嵌套在密封类中。
 * 4. 密封类拥有抽象类表示的灵活性（随意扩展：多层子类，多个子类）和枚举里集合的受限性（有限性）这两个优势
 */
sealed class Fruit

class Apple : Fruit() {
    fun opreate() = println("苹果")
}

class Banana : Fruit() {
    fun opreate() = println("香蕉")
}

open class Watemelon : Fruit() {
    fun opreate() = println("西瓜")
}

// 判断类型前面不需要加嵌套类类名，因为子类的声明不是放在密封类里面（即不是为嵌套类）
fun opreate(f: Fruit) = when (f) {
    is Apple -> f.opreate()
    is Banana -> f.opreate()
    is Watemelon -> f.opreate()
}

sealed class Fruit1 {

    abstract fun absMethod()

    class Apple1 : Fruit1() {
        override fun absMethod() = println("苹果")
    }

    class Banana1 : Fruit1() {
        override fun absMethod() = println("香蕉")
    }

    class Watemelon1 : Fruit1() {
        override fun absMethod() = println("西瓜")
    }

    data class Strawberry(val data: String) : Fruit1() {
        override fun absMethod() = println("草莓")
    }

    object Chestnut : Fruit1() {
        override fun absMethod() = println("栗子")
    }

    sealed class ChinaFruit : Fruit1() {

        class Pear : ChinaFruit() {
            override fun absMethod() {
                println("梨")
            }
        }

        class Pineapple : ChinaFruit() {
            override fun absMethod() {
                println("凤梨")
            }
        }
    }
}

fun main() {
    val o1 = Fruit1.Chestnut
    val o2 = Fruit1.ChinaFruit.Pineapple()
    val o3 = Fruit1.Strawberry("草莓")
    methodDemo1(o1)
}

fun methodDemo1(f: Fruit1) = when (f) {
    is Fruit1.Apple1 -> f.absMethod()
    is Fruit1.Banana1 -> f.absMethod()
    is Fruit1.Watemelon1 -> f.absMethod()
    is Fruit1.Strawberry -> f.absMethod()
    Fruit1.Chestnut -> f.absMethod()
    is Fruit1.ChinaFruit.Pear -> f.absMethod()
    is Fruit1.ChinaFruit.Pineapple -> f.absMethod()
}