package com.zkp.breath.kotlin


/**
 * 数据类，对应java的Bean类，组建函数componentN()，解构声明，copy函数。
 * 解构声明：内部调用的是组建函数componentN()，但是能解析的只用定义在类名后的主构函数的成员属性。
 *
 * 注意：1.数组和集合也能使用解构声明。
 *      2.主构函数必须带有全局变量。
 *      3.针对Gson反序列化的情况，必须声明默认无参构造函数或者主沟函数的变量设置默认值，
 *      （防止出现变量为非空类型，但获取的实际值还是null）。https://mp.weixin.qq.com/s/jVRTFTiwTtr7P7vyAj8G7A
 */
data class DataClass(val s: String, var i: Int) {
    val ss: String = ""
    var ii: Int = 0
}

fun main() {

    val dataClass = DataClass("数据类", 22)
    println("toString(): ${dataClass.toString()}")
    println("componentN()组件函数: ${dataClass.component1()}")
    val copy = dataClass.copy(s = "数据类copy", i = 33)
    val copy1 = dataClass.copy()
    println("copy类: $copy")
    println("copy内存地址比较: ${dataClass === copy1}")
    // 解构声明，其实内部调用的就是组建函数
    val (s, i) = copy1
    // 直接调用数据类对象其实内部调用是toString()方法
    println("copy1的toString(): ${copy1}")
    // 标准库提供的数据类
    val pair = Pair("a", "b")
    val triple = Triple("a", "b", "c")
    // 下划线表示没有使用到的变量
    val (_, s2) = pair
    println("打印参数2：${s2}")

    // 数组也能使用解构声明
    val (i1, i2, i3) = arrayOf(1, 2, 3)
    println("i1: ${i1}, i2: ${i2}, i3: $i3")

    // 集合也能使用解构声明
    val (a1, a2, a3) = arrayListOf(1, 2, 3)
    println("i1: ${a1}, i2: ${a2}, i3: $a3")


    // Map优雅的遍历方式
    // 接收参数接收的是一个Pair，Pair本身也是Data类
    val hashMapOf = hashMapOf("a" to 1, "b" to 2, "c" to 3)
    // 遍历出来元素类型是Pair，所以也能用解构声明获取
    for ((key, value) in hashMapOf) {
        println("获取到的key:${key}，value是：${value}")
    }

}


