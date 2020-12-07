package com.zkp.breath.kotlin

// 编译期常量,相当于java的静态常量
// 没有get访问器,因为get访问器能自定义返回的value（即动态），所以不符合常量的定义。
// 没有自定义set访问器，因为val修饰。
const val CONST = 22

class Demo(name: String) {

    // var/val变量使用构造方法的参数赋值进行初始化
    val name1 = name
    var name2 = name

    // var/val变量在init代码块初始化
    var name3: String

    init {
        this.name3 = name
    }

    // Kotlin 的变量是没有默认值的，Java 的 field 有默认值（但java的局部变量也是没有默认值的）
    // var 是 variable 的缩写， val 是 value(只读变量) 的缩写。
    // get/set访问器中使用到filed关键字则必须马上初始化（自动推断或者显示声明类型），因为kotlin没有默认初始值
    // field  幕后字段只能用于属性的get/set访问器。（在kotlin中，属性名=value会被编译器翻译成调用setter方法进而形成递归死循环,所以在get/set中kotlin提供了field关键字用于解决这个问题）
    var i: Int = 2
        // setter 函数参数是 value
        set(value) {
            // field幕后字段，代表该属性，相当于java中的this.属性名的简写
            field += value
        }
        get() = field + 1

    // 因为没有用到field幕后字段，不用在变量后马上写初始化值，get访问器相当于初始化
    var i3
        get() = 1       // 获取值的时候就知道i3的类型和值
        set(value) {}   // 无效的赋值操作，所以不用管i3到底是什么类型，当前值是什么，所以也就不用初始化。

    // 马上赋值，则访问器必须使用field关键字，否则调用这个变量的时候不知道使用的是马上赋值的值还是get访问器的值
    val i1 = 22
        get() = field + 2

    val i2: Int
        get() = 2

    val s
        get() = i == 3

    // 虽然 val 修饰的变量不能二次赋值，但可以通过自定义变量的 getter 函数，让变量每次被访问时，返回动态获取的值。
    val ss
        // 幕后属性，指向别的属性的值来作为自身的初始值
        get() = s

    /**
     * 「我很确定我用的时候绝对不为空，但第一时间我没法给它赋值」
     * 关键字lateinit，延迟初始化属性,用于类体中的属性，顶层属性与局部变量。
     * 注意：
     * 1.不允许自定义get/set访问器（get/set访问器中使用到field幕后字段是需要马上初始化的，但lateinit就是要延迟初始化，所以作用互斥）
     * 2.必须是非空类型且不能是原生类型(你声明为Int类型是不被允许的)。
     * 3.只能是var修饰（延迟赋值，不是不能赋值，而val定义后不能修改值所以不符合）
     * 4.必须指定类型
     */
    lateinit var lateinitStr: String

    fun isInitializedLateinitStr() {
        // TODO 重要,判断是否初始化
        if (::lateinitStr.isInitialized) {
        }
    }

    /**
     * kotlin的方法的参数默认是val，所以该参数不能重新赋值，也不能添加val/var修饰。
     * 而java的的参数可以添加final修饰，一般在局部匿名内部类使用外部局部变量的时候会添加。
     * Kotlin 里这样设计的原因是保证了参数不会被修改，而 Java 的参数可修改（默认没 final 修饰）会增加出错的概率。
     */
    fun funDemo(s: String) {

    }
}

// 无类体省略了花括号'{}'
open class ClassDemo2

// ‘constructor(参数)’关键字加构造参数表示主构造函数
class ClassDemo3 constructor(s: String)

// 没有任何注解或者可见性修饰符可以省略关键字‘constructor’
class ClassDemo4(s: String)

// 主构函数前添加可见修饰符表示该构造函数对外的可见度
class ClassDemo5 private constructor(s: String)

// 多个次构造函数（类似于java的方法重载；没有主构函数，不需要在次构函数后面加this()去表示调用主构函数
open class ClassDemo6 {
    constructor(s: String)
    constructor(i: Int)
    constructor(s: String, i: Int)
}

// 类头的父类无（），所以要在类体中声明的次构造函数要指向super或者调用指向super的this
class ClassDemo6X1 : ClassDemo6 {

    constructor(int: Int) : super("")

    constructor(int: Int, s: String) : super("") {
        println("ClassDemo6X1_无参数构造函数")
    }

    constructor(s: String) : this(0, "") {

    }

    // 主构函数的方法体
    init {
        println("ClassDemo6X1的init{}")
    }
}

// 直接在类头实现父类
class ClassDemo6X2 : ClassDemo6("")


class ClassDemo7 constructor(s: String) {
    // 存在主构的话则次构函数在参数后‘: this（主构函数参数）’表示间接调用主构函数
    constructor(i: Int, s: String) : this(s) {

    }
}

// 主构函数参数存在默认值
open class ClassDemo8 constructor(s: String = "默认值") {
    // this()括号中可以不传值
    constructor(i: Int, s: String) : this() {

    }
}

class ClassDemo9 constructor(s: String) {

    // 主构函数的参数可以赋值给属性
    val n: String = s
    var n1: String = s

    // val和var修饰的属性必须声明的时候就初始化或者在init()中初始化（且在init()初始化的属性必须指明数据类型）
    val n2: String
    var n3 = ""
    val n4: String? = ""
    var n5: String?


    /**
     * 初始化代码块（相当于主构函数的方法体）
     * 主构函数的参数可以在此代码块中出现，先于次构造次执行
     * 可以有多个初始化代码块，按照声明顺序执行
     */
    init {
        n2 = ""
        n5 = ""

        println("初始化代码块")
        /**
         *  (知识点)字符串模板 : $符号后面加上属性/变量
         *  字符串模板相当于java中的打印信息引用变量的形式。即： println("调用主构函数的参数s:" + s)
         */
        println("调用主构函数的参数s:$s")
    }

    constructor(i: Int) : this("主构函数参数值") {
        println("次构造函数")
    }
}