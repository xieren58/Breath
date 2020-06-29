package com.zkp.breath.kotlin

/**
 * Kotlin 中集合分为两种类型：只读的和可变的（mutableXXX）。这里的只读有两层意思：
 * 1. 集合的 size 不可变（不能添加新的元素）
 * 2. 集合中的元素值不可变 （不能修改元素）
 *
 * listOf() 创建不可变的 List，mutableListOf() 创建可变的 List。
 * setOf() 创建不可变的 Set，mutableSetOf() 创建可变的 Set。
 * mapOf() 创建不可变的 Map，mutableMapOf() 创建可变的 Map。
 *
 * 可以看到，有 mutable 前缀的函数创建的是可变的集合，没有 mutbale 前缀的创建的是不可变的集合，不过不可变的可以通过
 * toMutable*() 系函数转换成可变的集合。
 */
fun main() {
    list()

    set()

    map()

    sequence()
}

/**
 *
 * Kotlin新的容器类型 Sequence（序列），它和 Iterable 一样用来遍历一组数据并可以对每个元素进行特定的处理。
 *
 */
private fun sequence() {
    val sequenceOf = sequenceOf("a", "b", "c")
    val toMutableList = sequenceOf.toMutableList()
    val toMutableSet = sequenceOf.toMutableSet()

    val list = listOf("a", "b", "c")
    val asSequence = list.asSequence()
}

private fun map() {
    // map,mapOf 的每个参数表示一个键值对，to 表示将「键」和「值」关联，这个叫做「中缀表达式」
    val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 3)
    // 取值
    val valie1 = map.get("key1")
    val value2 = map["key2"]

    val toMutableMap = map.toMutableMap()
    val toMap = map.toMap()
    val toSortedMap = map.toSortedMap()

    val asIterable = map.asIterable()
    val asSequence = map.asSequence()


    // mutableMapOf() 创建的 Map 才可以修改
    val mutableMapOf = mutableMapOf("key1" to 1, "key2" to 2)
    mutableMapOf.put("key1", 2)
    mutableMapOf["key1"] = 2
    val toMutableMap1 = mutableMapOf.toMutableMap()
    val toMap1 = mutableMapOf.toMap()
    val toSortedMap1 = mutableMapOf.toSortedMap()
}

private fun set() {
    // 和 List 类似，Set 同样具有 covariant（协变）特性。
    val strSet = setOf("a", "b", "c")
    val anysSet: Set<Any> = strSet

    val toMutableList1 = strSet.toMutableList()
    val toMutableSet1 = strSet.toMutableSet()
    val toSet1 = strSet.toSet()
    val toList1 = strSet.toList()

    val asIterable = strSet.asIterable()
    val asSequence = strSet.asSequence()
}

private fun list() {
    // kotlin的List接口本身就支持协变，看接口定义。
    val listOf = listOf("a", "b", "c")
    val anys: List<Any> = listOf // success

    val toMutableList = listOf.toMutableList()
    val toMutableSet = listOf.toMutableSet()
    val toSet = listOf.toSet()
    val toList = listOf.toList()

    val asReversed = listOf.asReversed()
    val asSequence = listOf.asSequence()
    val asIterable = listOf.asIterable()
}