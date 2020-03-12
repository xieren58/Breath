package com.zkp.breath.kotlin

/**
 * 密封类Demo，枚举类的扩展，枚举是限制了枚举值只能是定义的那几种，但是值类型是该类的实例，而密封类
 * 是限制类结构体系是有穷的（该类可以有子类，子类可以有多个实例对象），只有配合when表达式才能发挥作用（如果没有写满所有类型as会提示，写满则不用else语句）
 *
 * 密封类本身是一个抽象类，所以无法直接实例化()，但是它可以有抽象成员。
 * 密封类不能拥有非-private构造函数（构造函数默认为private）。
 * 继承了密封类子类（直接继承者）的类可以在其他文件中定义，不一定在同一个文件中。
 *
 *
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