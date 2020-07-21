package com.zkp.breath.kotlin

/**
 * https://www.kotlincn.net/docs/reference/inline-classes.html
 *
 *
 *  1.内联类必须包含一个基础值，这就意味它需要一个公有主构造器来接收，主构造函数中只声明一个val修饰的数据类型。
 *  2.没有init初始化块。
 *  3.成员属性没有幕后字段，没有初始器，只能使用get方法，赋值的话只要它们仅基于构造器中那个基础值计算，或者从
 *    可以静态解析的某个值或对象计算 - 来自单例，顶级对象，常量等。
 *  4.不允许类继承 - 内联类不能继承另一个类，并且它们不能被另一个类继承。但可以实现接口
 *  5.内联类必须在顶级声明。嵌套/内部类不能内联的。
 */

object Conversions {
    const val MINUTES_PER_HOUR = 60
}

inline class Hours(val value: Int) {

    var s
        get() = ""
        set(value) {}

    val valueAsMinutes get() = value * Conversions.MINUTES_PER_HOUR

    fun toMinutes() = value * 60


    // public static final int toMinutes(int $this) {
    //	return $this * 60;
    // }
}

fun wait(period: Hours) { /* ... */
}
// void wait(int period) { /* ... */ }

fun main() {
    val period = Hours(24)
    // 上面的初始化在编译后相当于这样，可以看到并没有正在创建实例（不会分配到堆内存），它只是将基础值分配给int类型的变量。
    //  int period = 24;

    // 如果我们在Kotlin中调用Hours(24).toMinutes()，它可以有效地编译为toMinutes(24),其实就是一个工具静态方法。
    val toMinutes = period.toMinutes()

    wait(period)
    // 上面的方法编译后如下，它只是将基础值分配给int类型的变量
    // wait(24)
}