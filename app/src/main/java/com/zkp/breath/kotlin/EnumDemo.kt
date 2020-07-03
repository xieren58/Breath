package com.zkp.breath.kotlin


/**
 * 1. 枚举类能定义抽象方法
 * 2. 枚举类能实现接口，但不能继承抽象类。
 * 3. 枚举类中的成员只有常量和抽象方法
 */

// 和java的常规定义一致
private enum class Color1 {
    RED, BLACK, BLUE, GREEN, WHITE
}

// 可以在类头定义主构函数，但不能在类体定义次构函数（看注释第3点解析就知道）
private enum class Color(val rgb: Int, var alpha: Float = 1f, s: String = "默认") {
    RED(0xFF0000, 0f),
    GREEN(0x00FF00, 0.5f),
    BLUE(0x0000FF);
}

//定义一个接口
interface ItemClickListener {
    fun onClick(msg: String)
}

abstract class XXxxx {
    abstract fun xxx()

}

//枚举类继承接口，每个常量都必须重写接口的方法
enum class EnumDemo92 : ItemClickListener {
    BUTTON {
        override fun onClick(msg: String) {
            print(msg)
        }
    },
    IMAGE {
        override fun onClick(msg: String) {
            print(msg)
        }
    }
}

enum class Person1(var code: Int) {

    // 常量必须实现类内定义的抽象方法
    NAME1(0) {
        override fun showName(name: String): Person1 = NAME2
    },

    NAME2(1) {
        override fun showName(name: String): Person1 = NAME2
    };

    // 枚举类内定义的抽象方法
    abstract fun showName(name: String): Person1

}


fun main(args: Array<String>) {
    val color: Color = Color.BLUE
    println("获取枚举值的完整包名： ${Color.BLUE.declaringClass}")

    val enumValueOf = enumValueOf<Color>("RED")
    println("库函数方法获取指定名字的枚举值: ${enumValueOf.name}")

    // 和枚举类特有的方法values的作用一样
    val enumValues = enumValues<Color>()
    for ((index, value) in enumValues.withIndex()) {
        println("库函数方法()_the element at $index is $value")
    }

    // 以数组的形式，返回枚举值
    val values = Color.values()
    for ((index, value) in values.withIndex()) {
        println("the element at $index is $value")
    }

    // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
    println(Color.valueOf("RED"))
    // 获取枚举名称
    println(color.name)
    // 获取枚举值在所有枚举数组中定义的顺序
    println(color.ordinal)
    // 比较顺序。0表示相等，负数表示前者先于后者，正数反之
    println(Color.BLUE.compareTo(Color.RED))


    // 重写的枚举类中声明的方法，可以被其实例调用
    Person1.NAME1.showName("")

}