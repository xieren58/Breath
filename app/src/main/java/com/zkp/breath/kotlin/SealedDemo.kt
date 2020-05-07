package com.zkp.breath.kotlin

/**
 * 密封类Demo，枚举类的扩展，枚举是限制了枚举值只能是定义的那几种，但是值类型是该类的实例，而密封类
 * 是限制类结构体系是有穷的（该类可以有子类，子类可以有多个实例对象），只有配合when表达式才能发挥作用（如果没有写满所有类型as会提示，写满则不用else语句）
 * 理解：一个类可能有多个子类，而我们经常用多态（即父类变量指向子类对象），在判断的时候我们要么尽可能把所有子类都判断才能防止出现遗漏，但万一
 * 那一天别人用了你这个方法，自己又去定义了一个子类，然后传入判断，这时候就相当于进行else语块的判断。而为了防止有人任意继承我们就使用密封类
 * 去限制类的结构体系，表示目前只有这几种子类，别人用的时候就只能传入这几种子类类型；或者实在要创建多一个子类，那么when就会提示你多了一个
 * 子类类型，代码中应该去涵盖这个子类的判断，说到底就是一种 “提示（when）和限制类结构体系的作用”
 *
 * 密封类本身是一个抽象类，所以无法直接实例化()，但是它可以有抽象成员。
 * 密封类不能拥有非-private构造函数（构造函数默认为private）。
 * 密封类的子类声明必须在同一文件中。（这是规定，不要问为什么）
 */
sealed class Fruit

class Apple : Fruit() {
    fun opreate() = println("苹果")
}

class Banana : Fruit() {
    fun opreate() = println("香蕉")
}

open class Watemelon : Fruit() {
    fun opreate() = println("苹果")
}

// 判断类型前面不需要加嵌套类类名，因为子类的声明不是放在密封类里面（即不是为嵌套类）
fun opreate(f: Fruit) = when (f) {
    is Apple -> f.opreate()
    is Banana -> f.opreate()
    is Watemelon -> f.opreate()
}

sealed class Fruit1 {

    class Apple1 : Fruit1() {
        fun opreate() = println("苹果")
    }

    class Banana1 : Fruit1() {
        fun opreate() = println("香蕉")
    }

    class Watemelon1 : Fruit1() {
        fun opreate() = println("苹果")
    }
}

// 判断类型，前面要加嵌套类的类名
fun opreate(f: Fruit1) = when (f) {
    is Fruit1.Apple1 -> f.opreate()
    is Fruit1.Banana1 -> f.opreate()
    is Fruit1.Watemelon1 -> f.opreate()
}