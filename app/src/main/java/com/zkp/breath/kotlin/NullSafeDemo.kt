package com.zkp.breath.kotlin


/**
 * 可空安全操作符（?.）：如果使用一个可null的类型的变量去调用其成员的时候，要在点号前面加上?表示这个变量有可能为null，
 * 而当这个变量为null的时候是不会去调用其成员的，这样就不会出现java的NPE（空指针异常）。
 *
 * ?:( Elvis 操作符)：配合空安全操作符使用，当变量为null的时候会执行?:后面的逻辑。
 *
 * 非空断言运算符(!!),若该值为空则抛出异常（NPE 异常）,否则将值转换为非空类型。
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
    val l = if (b != null) b.length else -1 // 这种用法类似java的三目运算符
    val l2 = b?.length ?: -1
}

// Elvis 操作符还有另外一种常见用法，如下：
fun validate(user: User) {
    val id = user.id ?: return // 👈 验证 user.id 是否为空，为空时 return

    // 等同于
//    fun validate(user: User) {
//        if (user.id == null) {
//            return
//        }
//        val id = user.id
//    }
}

fun tes4_C(): String? {
    return null
}


fun test(s: String?): Unit? {
    val s1 = s ?: return null

    val length = s1.length
    val c = s1[0]
    s1.plus("ss")
    for (ss in s1) {
        println("$ss")
    }

    return null
}

/**
 * 因为 throw 和 return 在 Kotlin 中都是表达式，所以它们也可以用在 elvis 操作符右侧。这可能会非常方便，例如，检测函数参数
 */
fun tes4(): String? {
    // 注意：如果 tes4_C() 返回的值为null那么直接return结束该方法，而如果能够继续往下执行，那么表示该返回值一定是非空类型。
    val parent = tes4_C() ?: return null


    val tes4C = tes4_C()
    if (tes4C == null) return null


    // throw 表达式的类型是特殊类型 Nothing
    // 在你自己的代码中，你可以使用 Nothing 来标记一个永远不会返回的函数，编译器会知道在该调用后就不再继续执行了
    val name = tes4_C() ?: throw IllegalArgumentException("name expected")

    // 如果用 null 来初始化一个要推断类型的值，而又没有其他信息可用于确定更具体的类型时，编译器会推断出 Nothing?
    val x = null           // “x”具有类型 `Nothing?`
    val l = listOf(null)   // “l”具有类型 `List<Nothing?>

    return ""
}

/**
 *非空断言运算符(!!),若该值为空则抛出异常（NPE 异常）,否则将值转换为非空类型
 */
fun test5(b: String?) {
    val l = b!!.length
}

/**
 * Java 里面的 @Nullable 和 @NonNull 注解，在转换成 Kotlin 后对应的就是可空变量和不可空变量
 */
fun test6() {
    // java
//    @Nullable
//    String name;
//    @NonNull
//    String value = "hello";

    // kotlin
    var name: String? = null
    var value: String = "hello"
}

/**
 * 检查null
 */
fun check() {
    val age = -1
    require(age > 0) {

    }

    val name = null
    // 该函数存在返回值，当非null会返回自身，否则会抛出异常；null则执行参数2的lambda表达式，反之不成立；
    try {
        checkNotNull(name) {
            println("checkNotNull_lambda")
        }
    } catch (e: Exception) {
        println("checkNotNull_exception")
    }
    // 该函数存在返回值，当非null会返回自身，否则会抛出异常；null则执行参数2的lambda表达式，反之不成立；
    try {
        requireNotNull(name) {
            println("requireNotNull_lambda")
        }
    } catch (e: Exception) {
        println("requireNotNull_exception")
    }

}