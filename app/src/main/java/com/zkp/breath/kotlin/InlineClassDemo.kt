package com.zkp.breath.kotlin

/**
 * https://blog.csdn.net/u013064109/article/details/84866092
 * https://www.kotlincn.net/docs/reference/inline-classes.html
 *
 * 内联类实际上是基础类型的包装器（内联类是一个伪强类型的工具类）。
 * 1. 这里的强类型其实是对一个基本类型的包裹，其实内部是对一个基本类型的操作
 * 2. 实例化并不是正真意义上的实例化（避免开辟堆内存）
 * 3. 成员方法其实是静态方法。
 * 4. 让强类型作为参数更加明确，显示参数的含义（从类名可以看出，而且只能传强类型的实参），直接用基本类型一定程
 * 度造成理解的出错或者调用问题。
 * 5.内联类必须在顶级声明。嵌套/内部类不能内联的，枚举类也不能内联
 *
 *  注意：现在内联类还是一个实验功能
 *
 *  1.一定要公有主构造函数，且构造函数中声明只一个val修饰的基本数据类型
 *  2.没有init初始化块
 *  3.可以声明val属性（不能是延迟或者委托）和函数
 *  4.属性没有幕后字段，没有初始器，只能使用get方法且内部的操作还是针对构造器中的常量
 *  5. 可以实现接口，不能集成类
 */


inline class Hours(val value: Int) {

    val length: Int
        get() = value

    fun toMinutes() = value * 60
    // 其实相当于一个静态方法
    // public static final int toMinutes(int $this) {
    //	return $this * 60;
    // }
}

fun wait(period: Hours) { /* ... */ }
// void wait(int period) { /* ... */ }

fun main() {
    val period = Hours(24)
    //  int period = 24;  上面的初始化在编译后相当于这样，可以看到并没有正在创建实例（不会分配到堆内存），其实就是对基本类型的封装，

    wait(period)
    // wait(24)


}