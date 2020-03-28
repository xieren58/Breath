package com.zkp.breath.kotlin


/**
 * 可空类型的集合
 */
fun test() {
    // 可空类型的集合，List是允许存放null的
    val listWithNulls: List<String?> = listOf("Kotlin", null)
    for (item in listWithNulls) {
        // 如果要只对非空值执行某个操作，安全调用操作符可以与 let 一起使用
        item?.let { println(it) } // 输出 Kotlin 并忽略 null
    }

    // 过滤非空元素，可以使用 filterNotNull
    val intList: List<String> = listWithNulls.filterNotNull()
}

class PersonX1 {
    fun getSS(): PersonX2? {
        return PersonX2()
    }
}

class PersonX2 {
    var ss: String = ""
}

fun setPersonX2ValueStr() = ""

/**
 *安全调用也可以出现在赋值的左侧。这样，如果调用链中的任何一个接收者为空都会跳过赋值，而右侧的表达式根本不会求值
 */
fun test2() {
    val personX: PersonX1? = PersonX1()
    // 如果 `personX` 或者 `personX.getSS` 其中之一为空，都不会调用该函数：
    personX?.getSS()?.ss = setPersonX2ValueStr()
}

/**
 * 处理空的两种判断
 */
fun test3() {
    val b: String? = ""
    val l = if (b != null) b.length else -1
    val l2 = b?.length ?: -1
}

fun tes4_C(): String? {
    return null
}

/**
 * 因为 throw 和 return 在 Kotlin 中都是表达式，所以它们也可以用在 elvis 操作符右侧。这可能会非常方便，例如，检测函数参数
 */
fun tes4(): String? {
    val parent = tes4_C() ?: return null
    val name = tes4_C() ?: throw IllegalArgumentException("name expected")

    //...
    val sss = ""
    return sss
}

/**
 *非空断言运算符(!!),若该值为空则抛出异常（NPE 异常）,否则将值转换为非空类型
 *
 */
fun test5(b: String?) {
    val l = b!!.length
}