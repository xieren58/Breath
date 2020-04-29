package com.zkp.breath.kotlin


/**
 * 1.Kotlin 中的数组是一个拥有泛型的类，相对于java来说就拥有了和集合一样的功能。
 * 2.Kotlin 的数组编译成字节码时使用的仍然是 Java 的数组，但在语言层面是泛型实现，这样会失去协变 (covariance) 特性，
 * 就是子类数组对象不能赋值给父类的数组变量（多态）：
 *
 *Kotlin 中数组和 MutableList 的 API 是非常像的，主要的区别是数组的元素个数不能变。那在什么时候用数组呢？
 * 1.这个问题在 Java 中就存在了，数组和 List 的功能类似，List 的功能更多一些，直觉上应该用 List 。
 * 但数组也不是没有优势，基本类型 (int[]、float[]) 的数组不用自动装箱，性能好一点。
 *2.在 Kotlin 中也是同样的道理，在一些性能需求比较苛刻的场景，并且元素类型是基本类型时，用数组好一点。
 * 不过这里要注意一点，Kotlin 中要用专门的基本类型数组类 (IntArray FloatArray LongArray) 才可以免于装箱。
 * 也就是说元素不是基本类型时，相比 Array，用 List 更方便些。
 *
 */
fun main() {

    // kotlin的数组语言层面支持泛型，而泛型不支持协变。
    val strs: Array<String> = arrayOf("a", "b", "c")
//    val anys: Array<Any> = strs // compile-error: Type mismatch，

    // 可空类型
    val arrayOfNulls = arrayOfNulls<String>(3)
    val arrayOfNulls2: Array<String?> = arrayOfNulls(3) // 另外一种写法

    // 设置对应角标的值的不同写法
    arrayOfNulls.set(0, "a")
    arrayOfNulls[1] = "b"   // 重置了get/set方法
    arrayOfNulls[2] = "c"

    // 获取方法
    val string = arrayOfNulls.get(1)
    val string1 = arrayOfNulls[1]
    val first = arrayOfNulls.first()
    val last = arrayOfNulls.last()
    val find = arrayOfNulls.find { it.equals("b") }

    // 过滤，返回List集合，List支持协变，即子类型可以赋值给夫类型。
    val filter = arrayOfNulls.filter { it.equals("") }
    // 排序
    arrayOfNulls.sort()
    // 存在一个或者多个元素则返回true，没有任何一个元素则返回false
    arrayOfNulls.any()
    // 数组长度 （元素数量）
    val count = arrayOfNulls.count()
    val size = arrayOfNulls.size

    // 是否包含指定的元素
    val contains = arrayOfNulls.contains("a")

    //
//    arrayOfNulls.flatMap {  }

//    arrayOfNulls.fold()

//    arrayOfNulls.groupBy {  }


    // 遍历
    arrayOfNulls.forEach { println("字符串数组内容: " + it) }
    println()

    // 自动推断类型
    val arrayOf = arrayOf(1, 2, 3)
    arrayOf.forEach { println("int数组内容: " + it) }

    // 指定类型
    val intArrayOf = intArrayOf(1, 2, 3)
    intArrayOf.forEach { println("无封箱int数组内容: " + it) }

    // 指定类型，通过lambda表达式赋值
    val intArray = IntArray(5) { index -> index * 2 }
    intArray.forEach { print("无封箱int数组内容创建方式2：$it") }

    // 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
    // index表示角标
    val asc = Array(5, { index -> (index * index).toString() })
    asc.forEach { println(it) }

}


