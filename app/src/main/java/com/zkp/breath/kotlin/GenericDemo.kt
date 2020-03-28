package com.zkp.breath.kotlin

/**
 * 没有指定上限，默认使用Any？作为上限
 */
class Box<T>(t: T) {
    // 类型为泛型
    var value = t

    // 泛型约束
    // 对于多个上界约束条件，可以用 where 子句
    fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
            where T : CharSequence,
                  T : Comparable<T> {
        return list.filter { it > threshold }.map { it.toString() }
    }

    // from没加out之前内部逻辑有可能出现对from进行写入，但我们传入的是int,有可能写入的是string。这时候就出现
    // 异常了，所以为了防止对from进行写入，我们应该加上out（生产者），表示只能获取而不能写入
    fun copy1(from: Array<Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    // 协变
    fun copy2(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
        // from元素类型是Any或者其子类，to元素类型是Any类型，所以from元素赋值给to元素是允许的。
            to[i] = from[i]
    }

    // 逆变
    fun fill(dest: Array<in String>, value: String) {
        dest[1] = value
    }

}

// 使用 out 使得一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类
class Runoob1<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}

// in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型但是无法作为返回值的类型：
class Runoob2<in A>(a: A) {
    fun foo(a: A) {
    }
}


// =============================泛型函数=============================
// =============================泛型函数=============================

fun <T> singletonList(item: T): List<T> {
    val arrayListOf = arrayListOf<T>()
    arrayListOf.add(item)
    return arrayListOf
}

// 泛型约束，最普通的约束类型是上限，对应 Java 的 extends 关键字
// 泛型的类型为Comparable或其子类
fun <T : Comparable<T>> cusSort(list: List<T>) {
    list.forEach {
        println("打印:{$it}")
    }
}

// 指定多个上限，使用where语句。必须满足是所有上限的共同子类
fun <T> cusCopyWhenGreater(list: List<T>) where T : CharSequence, T : Comparable<T> {
    list.forEach {
        println("打印:{$it}")
    }
}

fun <T> process(value: T) {
    println(value?.hashCode())
    println(value.hashCode())
}

class TempDemo {

}

fun main(args: Array<String>) {
    //创建方式1，as其实有提示，但是离开as可读性不好
    val fx = Box("")
    // 创建方式2，可读性好，但是太长
    val fx1: Box<String> = Box<String>("")
    // 创建方式3，强烈推荐，可读性好
    val fx2: Box<String> = Box("")


    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
//    fx2.copy1(ints, any) // error
    fx2.copy2(ints, any)

    val strs = Array(3) { "" }
    fx2.fill(strs, "a")

    val singletonList = singletonList(1)
    val singletonList1 = singletonList("我们")

    val arrayListOf = arrayListOf(1, 2, 3)
    val arrayListOf1 = arrayListOf(TempDemo())
    cusSort(arrayListOf)
//    cusSort(arrayListOf1) // 编译不通过
    val arrayListOf2 = arrayListOf("a")
    cusCopyWhenGreater(arrayListOf2)
//    cusCopyWhenGreater(arrayListOf1)  // 编译不通过

}

