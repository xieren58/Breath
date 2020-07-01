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
 * https://mp.weixin.qq.com/s/LR538GcADyY205Vhsk_Egw
 *
 * Kotlin新的容器类型 Sequence（序列），它和 Iterable 一样用来遍历一组数据并可以对每个元素进行特定的处理。
 * 序列 Sequence 又被称为「惰性集合操作」，这种类似懒加载的实现有下面这些优点：
 * 1.一旦满足遍历退出的条件，就可以省略后续不必要的遍历过程。
 * 2.像 List 这种实现 Iterable 接口的集合类，每调用一次函数就会生成一个新的 Iterable，下一个函数再基于新的 Iterable 执行，
 *   每次函数调用产生的临时 Iterable 会导致额外的内存消耗，而 Sequence 在整个流程中只有一个。
 *
 * 因此，Sequence 这种数据类型可以在数据量比较大或者数据量未知的时候，作为流式处理的解决方案。
 */
private fun sequence() {

    val sequenceOf = sequenceOf("a", "b", "c")
    val toMutableList = sequenceOf.toMutableList()
    val toMutableSet = sequenceOf.toMutableSet()

    val list = listOf("a", "b", "c")
    val asSequence = list.asSequence()


    /**
     * 给定一个初识的元素，并提供函数计算下一个元素。该函数会一直生成序列的元素，直到函数实参返回null为止。如果函数
     * 实参不返回null,则该序列将是一个无限序列：
     */
    val numbers = generateSequence(6) {
        if (it < 10)
            it + 2
        else
            null
    }


    /**
     * 惰性的概念首先就是说在「👇」标注之前的代码运行时不会立即执行，它只是定义了一个执行流程，只有 result 被使用到的时候才会执行。
     * 当「👇」的 println 执行时数据处理流程是这样的：
     * 取出元素 1 -> map 为 2 -> filter 判断 2 是否能被 3 整除
     * 取出元素 2 -> map 为 4 -> filter 判断 4 是否能被 3 整除
     * ...
     * 惰性指当出现满足条件的第一个元素的时候，Sequence 就不会执行后面的元素遍历了，即跳过了 4 的遍历。
     *
     */
    val sequence = sequenceOf(1, 2, 3, 4)
    val result: Sequence<Int> = sequence
            .map { i ->
                println("Map $i")
                i * 2
            }
            .filter { i ->
                println("Filter $i")
                i % 3 == 0
            }
    // 👇
    println(result.first()) // 👈 只取集合的第一个元素

    /**
     * List 是没有惰性的特性的：
     * 执行流程如下：
     *
     * 1.声明之后立即执行
     * 2.数据处理流程如下：
     *      先执行完map方法，{1, 2, 3, 4} -> {2, 4, 6, 8}
     *      再执行完filter方法，遍历判断是否能被 3 整除
     */
    val list2 = listOf(1, 2, 3, 4)
    val result2 = list2
            .map { i ->
                println("Map $i")
                i * 2
            }
            .filter { i ->
                println("Filter $i")
                i % 3 == 0
            }
    // 👇
    println(result2.first()) // 👈 只取集合的第一个元素
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