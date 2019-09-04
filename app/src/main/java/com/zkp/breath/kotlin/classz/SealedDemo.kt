package com.zkp.breath.kotlin.classz

sealed class Fruit

class Apple : Fruit() {
    fun opreate() = println("苹果")
}

class Banana : Fruit() {
    fun opreate() = println("香蕉")
}

class Watemelon : Fruit() {
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