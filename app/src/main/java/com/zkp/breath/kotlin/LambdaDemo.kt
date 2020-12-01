package com.zkp.breath.kotlin


/**
 * https://juejin.im/post/5ac0dca8f265da237e09ddc1#heading-7
 *
 * java的lambda和kt的lambda不是同一个概念。
 * lambda和普通函数的区别：普通函数是准备好了逻辑，差参数；lambda是准备好了参数，差逻辑。
 *
 * 1. lambda表达式分类:普通的lambda表达式;带接收者的lambda表达式。
 * 2. lambda表达式返回值总是返回函数体内部最后一行表达式的值。
 * 3. 可以使用typealias关键字给Lambda类型命名。
 * 4. 替代原有匿名内部类，但是需要注意一点就是只能替代含有单抽象方法的类。
 * 5. Kotlin和Java内部类或lambda访问局部变量的区别,java但匿名内部类或者lambda访问但局部变量必须需要final修饰，
 *   也就意味着在内部类内部或者lambda表达式的内部是无法去修改函数局部变量的值；而kotlin既可以访问final修饰的变量，
 *   也可以访问非final修饰的变量，也就意味着在Lambda的内部是可以直接修改函数局部变量的值，这叫lambdab表达式的变量捕获。
 *   （详情可看doc文件夹中" lambdab表达式的变量捕获.png"）
 * 6. 函数类型其实就是FunctionX类，该类就有一个invoke()方法。
 *
 */
class MethodClass {


    // 函数类型常见写法1
    fun lambda1(body: (a: Int, b: Int) -> Int) {
        println(body(3, 4))
    }

    // 函数类型写法2，函数类型的参数名可以省略。
    fun lambda2(body: (Int, Int) -> Int) {
        println(body(3, 4))
    }


    /**
     * 函数重载的定义和java一样，参数类型不一致或者数量不同都可视为重载。
     *
     * 其实lambda表达式指向的是FunctionX类，lambda表达式的参数个数如果相同则引用的是同一个FunctionX类，这就导致
     * 参数类型相同所以不允许重载（使用kotlin bytecode功能转换成java代码就明白了）。
     */
    fun lambda2(body: (a: String, b: String) -> String, s: String) {

    }

    // 不可重载
//    fun lambda2(body: (a: Int, b: Int) -> Int, s: String) {
//
//    }

    fun lambda2(s: String, body: (a: String, b: String) -> String) {

    }

    fun lambda2(s: String, body: (a: String, b: String, c: String) -> String) {

    }

    fun lambda2(s: String, body: (a: String, b: String, c: String, d: String) -> Int) {

    }

    // 函数类型的参数类型为data类
    fun lambda2(body: (p: Pair<Int, Int>) -> Int): Int {
        return body(Pair<Int, Int>(1, 2))
    }

    // 函数类型的返回值类型为泛型
    fun <T> filter(body: () -> T): T {
        return body()
    }

    fun <T> filter1(t: T, body: (T) -> T): T {
        return body(t)
    }

    fun <T> filter1(t: T, body: (T, T) -> T): T {
        return body(t, t)
    }

    fun <T> filter1(t: T, body: (T, T, T) -> T): T {
        return body(t, t, t)
    }

    fun <T> filter2(t: T, body: (T) -> T) {
        t.filter2(body)
    }

    // 类中的扩展函数调用需要一个中转方法调用
    // 这里的this表示扩展函数的调用者
    fun <T> T.filter2(body: (T) -> T): T {
        return body(this)
    }

    fun <T> lock(t: Int, body: (Int) -> T): T {
        return body(t)
    }

    /**
     * lambda表达式的参数是泛型，调用时传入的参数也应该是泛型类型的参数。
     */
    fun <T> lock(t11: Int, t: T, body: (T) -> T) {
        val body1 = body(t)     // 因为传入
//        val body11 = body(t11)   // 类型不匹配
    }

    // lambda 表达式，即函数类型的字面量（看例子的调用方式就知道）
    // 函数类型为可null类型，返回值为可null类型
    fun <T> lock2(t: Int, body: (() -> T)?): T? {

        // 方法一：比较常见的写法
        // 因为函数类型为可null类型所以需要判空
        if (body != null) {
            return body()
        }

        // 方法二
        // 使用？.操作符，如果不为null则调用let函数，let函数内的it表示body，所以需要在it后面添加()表示调用自己。
//        return body?.let { it() }


        // 方法三
        // invoke方法，只有函数
//        return body?.invoke()
        return null
    }

}


fun isEven(a: Int) = a % 2 == 0

fun comboTwoValue(a: Int, b: Int, method: (a: Int, b: Int) -> Int): Int {
    return method(a, b)
}

/**
 * Kotlin支持局部函数,也就是说函数可以嵌套。
 * 局部函数可以访问外部函数的局部变量。
 */
fun dfs(s: String, i: Int) {
    fun dfs(i: Int): String {
        return i.toString() + s
    }
    dfs(1)
}

// 将集合item转换成其他类型后添加到一个新的集合后返回
fun <T, R> List<T>.cusMap(transform: (T) -> R): List<R> {
    val result = arrayListOf<R>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}

fun main() {

    val methodClass = MethodClass()

    methodClass.lambda1 { s, s1 -> s + s1 }     // 知道了类型，可以省略,切记参数不用加括号！！！！
    methodClass.lambda1 { s: Int, s1: Int -> s + s1 }     // 最完整的写法，类型也写上，切记参数不用加括号！！！！
    methodClass.lambda2 { (s, s1) -> s + s1 }   // 知道了类型，可以省略。注意这里加了圆括号，因为Pair有组建函数ComponetN,所以这里是解构声明的写法
    methodClass.lambda2 { (s, s1): Pair<Int, Int> -> s + s1 }   // 最完整的写法，类型也写上。注

    val body = { "我们" }
    val filter = methodClass.filter(body)
    val filter1 = methodClass.filter { "我们" }
    println(filter)

    methodClass.filter1("", { str -> str + "扩展的部分" })
    methodClass.filter1(1, { i -> i + 2 })
    val filter1Body: (Int) -> Int = { i -> i + 2 }
    methodClass.filter1(1, filter1Body)

    val filter1Body2: (String, String) -> String = { str1, str2 -> str1 + str2 }
    methodClass.filter1("", { str1, str2 -> str1 + str2 })
    methodClass.filter1("", filter1Body2)


    val xxww1 = methodClass.filter {
        val s: String = "我们"
        val s1: String = "你们"
        s + s1
    }
    println(xxww1)

    val xxww2 = methodClass.filter {
        val s: String = "我们"
        val s1: String = "你们"
        // 返回值，作用的范围为filter方法，否则会结束main方法。
        return@filter s + s1
    }
    println(xxww2)

    methodClass.filter2("我们") { s -> s + "拼接字段" }

    val lock = methodClass.lock(1) { i -> "hello to myself:$i" }

    // 返回类型为可null，能够自动推出类型
    val bodyLock2: String? = methodClass.lock2(1) { "我们" }
    // 传入函数参数null.
    val lock22 = methodClass.lock2<String>(1, null)
    // 函数类型声明，函数类型字面量。
    val body1: (() -> String)? = null
    val body2: (() -> String)? = { "我们" }
    val lock2 = methodClass.lock2(1, body1)
    val lock21 = methodClass.lock2(1, body2)
    println()

    val arrayListOf = arrayListOf(1, 2, 3)
    val cusMap = arrayListOf.cusMap { it % 2 == 0 }

    // 传递是lambda表达式，表达式内容需要用{}包裹
    arrayOf(3, 4, 5).filter { s -> s > 0 }
    // 使用函数作为参数传递，那么函数需要是函数引用（函数对象），在函数前加"::"即可，注意使用圆括号
    arrayOf(3, 4, 5).filter(::isEven)
    // 引用方式2，花阔号包裹
    arrayOf(3, 4, 5).filter {
        //        i -> isEven(i)
        // 相当于上面的写法，只是当函数类型参数只有一个参数的时候可以省略不写，使用it代替。
        isEven(it)
    }
    println()

    arrayOf("你", "我", "ta").filter { it == "你" }
    arrayOf("你", "我", "ta").filter("你"::equals)
    println()

    // 创建函数对象（函数引用）后作为参数传递，因为min是属于Math方法，所以在：：前加上类名标记说明这个方法的出处
    comboTwoValue(3, 4, Math::min)
    comboTwoValue(3, 4) { a, b -> Math.min(a, b) }
    println()

}