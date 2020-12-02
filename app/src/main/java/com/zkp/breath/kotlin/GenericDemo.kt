package com.zkp.breath.kotlin

import java.io.Serializable

/**
 * 协变：out，等同于 Java 中的上界通配符 ? extends。
 * 逆变：in，等同于 Java 中的下界通配符 ? super。
 * 和Java不同的是Kotlin可以把协变和逆变提前定义在类上。
 *
 * 通配符"*"：Java 中单个 ? 号也能作为泛型通配符使用，相当于 ? extends Object, Kotlin 中有等效的写法：* 号，相当于 out Any。
 *      和 Java 不同的地方是，如果你的类型定义里已经有了 out 或者 in，那这个限制在变量声明时也依然在，不会被 * 号去掉。
 *      比如你的类型定义里是 out T : Number 的，那它加上 <*> 之后的效果就不是 out Any，而是 out Number
 *
 * 泛型约束：Java中声明类或接口的泛型时可以使用 extends 约束泛型类型为某个类型的子集，而kotlin是用“：”冒号，
 *      java多个限制使用 & 符号，而kotlin使用where关键字。
 *
 * Kotlin 泛型与 Java 泛型不一致的地方 :
 *
 * 1.Java 里的数组是支持协变的，而 Kotlin 中的数组 Array 不支持协变。
 *   这是因为在 Kotlin 中数组是用 Array 类来表示的，这个 Array 类使用泛型就和集合类一样，所以不支持协变。
 *
 *2. Java 中的 List 接口不支持协变，而 Kotlin 中的 List 接口支持协变（这里是指可以实现多态，且List实体类只能获取指但不能修改值）。
 * Java 中的 List 不支持协变，原因在上文已经讲过了，需要使用泛型通配符来解决。
 *
 * 在 Kotlin 中，实际上 MutableList 接口才相当于 Java 的 List。Kotlin 中的 List 接口实现了只读操作，没有写操作，
 * 所以不会有类型安全上的问题，自然可以支持协变。
 *
 */
class Box<T>(t: T) {
    // 类型为泛型
    var value = t

    // from没加out之前内部逻辑有可能出现对from进行写入，但我们传入的是int,有可能写入的是string。这时候就出现
    // 异常了，所以为了防止对from进行写入，我们应该加上out（生产者），表示只能获取而不能写入，如copy2（）方法。
    fun copy1(from: Array<Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    // 这个例子的作用是证明out只能用于获取
    fun copy2(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
        // from元素类型是Any或者其子类，to元素类型是Any类型，所以from元素赋值给to元素是允许的。
            to[i] = from[i]
    }

    // 这个例子的作用是证明in能用于添加
    fun fill(dest: Array<in String>, value: String) {
        dest[1] = value
        val any = dest[1]
    }
}


// ===============================================================================
// ===============================================================================

// 提前定义上界的写法：协变，该泛型只能用于返回值，不能用于形参。
class Producer<out T> {
    fun produce(): T? {
        return null
    }

    // 不允许用于形参
//    fun produce1(t: T){
//
//    }
}

// 提前定义下界的写法 ：逆变，该泛型只能用于形参，不能用于返回值。
class Consumer<in T> {
    fun consume(t: T) {

    }

    // 不允许用于返回值
//    fun consume1() : T?{
//        return null
//    }
}

// 泛型约束：相当于java的 T extends Number的写法，表示泛型类型只能是Number或者Number的直接，间接类型。
class Consumer1<T : Number>

// 泛型约束
// 对于多个约束条件，可以用 where 子句
// 相当于java的 T extends Number & Serializable的写法
class Consumer2<T> where T : Number, T : Serializable

class Consumer3<T> {
    fun ff(): T? {
        return null
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


// 泛型约束
// 对于多个约束条件，可以用 where 子句，必须满足是所有上限的共同子类
fun <T> cusCopyWhenGreater(list: List<T>) where T : CharSequence, T : Comparable<T> {
    list.forEach {
        println("打印:{$it}")
    }
}

fun <T> process(value: T) {
    // 相当于Any类
    println(value?.hashCode())
    println(value.hashCode())
}

private class TempDemo

// =================================================================
// =================================================================

// 由于 Java 中的泛型存在类型擦除的情况，任何在运行时需要知道泛型确切类型信息的操作都没法用了。
// 比如你不能检查一个对象是否为泛型类型 T 的实例：
//fun <T> printIfTypeMatch1(item: Any) {
//    if (item is T) { // 👈 IDE 会提示错误，Cannot check for instance of erased type: T
//        println(item)
//    }
//}

// 使用关键字 reified 配合 inline 来解决：
inline fun <reified T> printIfTypeMatch2(item: Any) {
    if (item is T) { // 👈 这里就不会在提示错误了
        println(item)
    }
}

fun main(args: Array<String>) {
    // kotlin的List接口本身就支持协变，看接口定义，只是把协变的写法提前定义了。
    val strs1: List<String> = listOf("a", "b", "c")
    val anys1: List<CharSequence> = strs1   // 1
    val anys2: List<out CharSequence> = strs1   // 2，相当于1, 其实1就是省略了out关键字，因为类定义的时候已经提前定义了out
    // 和 List 类似，Set 同样具有 covariant（协变）特性。
    val strSet = setOf("a", "b", "c")

    val list1: ArrayList<out Number> = ArrayList<Int>()
    val list2: ArrayList<out Number> = ArrayList<Number>()
    val list3: ArrayList<in Int> = ArrayList<Int>()
    val list4: ArrayList<in Int> = ArrayList<Number>()


    // * 相当于out any，这时候右边的泛型声明不能省略, *这种是不能自动推导的。
    val list5: List<*> = ArrayList<Any>()
    val list6: List<*> = ArrayList<String>()
    val list7: List<*> = ArrayList<Int>()

    val consumer11 = Consumer1<Int>()
    val consumer12 = Consumer1<Number>()
    // 因为类定义中已经实现了泛型约束，所以下面的*表示的意义等同于类中的泛型约束。
    val consumer13: Consumer1<*> = Consumer1<Int>()
    val consumer14: Consumer1<out Number> = Consumer1<Int>()
    val consumer15: Consumer1<in Int> = Consumer1<Number>()
//    val consumer16: Consumer1<out Any> = Consumer1<Int>() // 错误声明
//    val consumer17: Consumer1<in Any> = Consumer1<Number>()   // 错误声明


    val consumer21 = Consumer2<Int>()
    val consumer22: Consumer2<*> = Consumer2<Int>()
    val consumer23: Consumer2<out Number> = Consumer2<Int>()
    val consumer24: Consumer2<in Int> = Consumer2<Number>()
//    val consumer25: Consumer2<out Any> = Consumer2<Number>()  // 错误声明
//    val consumer26: Consumer2<in Any> = Consumer2<Int>()  // 错误声明


    val box: Box<String> = Box("")
    box.fill(Array(3) { i -> i.toString() }, "a")
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
//    fx2.copy1(ints, any) //error， kotlin的数组是有泛型定义的，而泛型本身不具有协变
    box.copy2(ints, any)


    val singletonList = singletonList(1)
    val singletonList1 = singletonList("我们")


    val arrayListOf = arrayListOf(1, 2, 3)
    val arrayListOf1 = arrayListOf(TempDemo())
    cusSort(arrayListOf)
//    cusSort(arrayListOf1) // 编译不通过
    val arrayListOf2 = arrayListOf("a")
    cusCopyWhenGreater(arrayListOf2)
//    cusCopyWhenGreater(arrayListOf1)  // 编译不通过


    // 一般允许这种写法的话就表示在定义类里面已经存在out/in关键字，否则按照java的规则泛型是不允许多态的。
    val producer1: Producer<Number> = Producer<Int>()    //  // 👈 这里不写 out 也不会报错
    val producer2: Producer<out Number> = Producer<Int>() // 👈 out 可以但没必要
    val producer3 = Producer<Int>() // ===>  val producer3: Producer<Int> = Producer<Int>() ==》 val producer3: Producer<out Int> = Producer<Int>()
    val producer4: Producer<*> = Producer<Int>()

    val produce = producer1.produce()
    val produce1 = producer3.produce()
    val produce2 = producer4.produce()


    val consumer1 = Consumer<Int>()     // ==>  val consumer: Consumer<Int> = Consumer<Int>()   ==> val consumer: Consumer<in Int> = Consumer<Int>()
    val consumer2: Consumer<Int> = Consumer<Number>() // 👈 这里不写 in 也不会报错
    val consumer3: Consumer<in Int> = Consumer<Number>() // 👈 in 可以但没必要
    val consumer4: Consumer<*> = Consumer<Int>()    // Nothing类型, 无效写法
//    consumer4.consume()


    val c1: Consumer3<*> = Consumer3<Int>() // 相当于下面的写法
    val c: Consumer3<out Any> = Consumer3<Int>()
    val ff1 = c1.ff()
    val ff = c.ff()
}

