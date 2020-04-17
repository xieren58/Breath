package com.zkp.breath.kotlin

import java.io.Serializable

/**
 * 使用关键字 out 来支持协变，等同于 Java 中的上界通配符 ? extends。
 * 使用关键字 in 来支持逆变，等同于 Java 中的下界通配符 ? super。
 *
 * kotlin的out和in也能放在声明处，即放在类的泛型声明处,表示只能泛型参数只用来输入或者输出。
 *
 * Java 中单个 ? 号也能作为泛型通配符使用，相当于 ? extends Object, Kotlin 中有等效的写法：* 号，相当于 out Any。
 * 和 Java 不同的地方是，如果你的类型定义里已经有了 out 或者 in，那这个限制在变量声明时也依然在，不会被 * 号去掉。
 * 比如你的类型定义里是 out T : Number 的，那它加上 <*> 之后的效果就不是 out Any，而是 out Number
 *
 * 没有指定上限，默认使用Any？作为上限。如：out T 相当于 out T : Any?
 *
 * Java 中声明类或接口的时候，可以使用 extends 来设置边界，将泛型类型参数限制为某个类型的子集，而kotlin是用“：”冒号，
 * java多个限制使用 & 符号，而kotlin使用where关键字。
 *
 *
 * *
 *
 * Kotlin 泛型与 Java 泛型不一致的地方 :
 *
 * 1.Java 里的数组是支持协变的，而 Kotlin 中的数组 Array 不支持协变。
 * 这是因为在 Kotlin 中数组是用 Array 类来表示的，这个 Array 类使用泛型就和集合类一样，所以不支持协变。
 *
 *2. Java 中的 List 接口不支持协变，而 Kotlin 中的 List 接口支持协变。
 * Java 中的 List 不支持协变，原因在上文已经讲过了，需要使用泛型通配符来解决。
 *
 * 在 Kotlin 中，实际上 MutableList 接口才相当于 Java 的 List。Kotlin 中的 List 接口实现了只读操作，没有写操作，所以不会有类型安全上的问题，自然可以支持协变。
 *
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
    // 异常了，所以为了防止对from进行写入，我们应该加上out（生产者），表示只能获取而不能写入，如copy2（）方法。
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

// 相当于java的 T extends Number的写法，表示泛型类型只能是Number或者Number的直接，间接类型。
class Runoob3<T : Number>(t: T) {
    // 因为泛型限定为Number的子类，所以符合多态写法
    val t1: Number = t
    val t2: T = t
}

// 设置多个边界可以使用 where 关键字，注意写在主构函数后面
// 相当于java的 T extends Number & Serializable的写法
class Runoob4<T>(t: T) where T : Number, T : Serializable {
    val t1: Number = t
    val t2: Serializable = t
    val t3: T = t
    val t4 = t
}

// 表示这个泛型有限定类型范围，且该泛型具有协变
class Runoob5<out T : Number>(t: T) {
    val t1 = t
    val t2: Number = t
    fun getT() = t1
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
    // 相当于Any类
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


    val list1: ArrayList<out Number> = ArrayList<Int>()
    val list2: ArrayList<out Number> = ArrayList<Number>()

    val list3: ArrayList<in Int> = ArrayList<Int>()
    val list4: ArrayList<in Int> = ArrayList<Number>()

    // * 相当于out any，这时候右边的泛型声明不能省略, *这种是不能自动推导的。
    val list5: List<*> = ArrayList<Any>()
    val lis6: List<*> = ArrayList<String>()
    val arrayList = ArrayList<String>() // 需要传入泛型类型
    val arrayListOf3 = arrayListOf<Any>()   // 需要传入泛型类型

    val runoob5 = Runoob5(1)
    val runoob6 = Runoob5(1L)
    val runoob7 = Runoob5(1f)

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

