package com.zkp.breath.kotlin

/**
 * 1.当实现多个接口中存在相同的方法时候，实现类调用接口的方法的时候要在super关键字后面加上"<接口名>"，如果不存在相同
 * 方法可以写也可以不写（一般都省略不写）。
 * 2.接口的属性默认都是抽象的，所以不允许初始化值,但是可以实现属性的get/set访问器（var要实现get/set,而val要实现get，相当于伪抽象）
 * ，访问器中不允许有幕后字段，实现类可以使用override关键字重写父属性，重写后需要进行初始化，如果父接口属性存在get方
 * 法则可以不初始化但是需要实现get方法且指向父属性。（和java不同的是java的接口属性都是常量。）
 * 3.接口的方法可以有默认实现，默认实现的方法可视为非抽象所以实现类可以不重写，而java的方法都是抽象的。
 *
 */

interface AS {
    fun ss() {}

    /**
     * 方法存在默认实现的原因：使用kotlin bytecode编译成java后其实接口内部会有一个默认名为DefaultImpls的final的
     * 静态内部类，该类会存在一个相同名的方法，参数就是接口类型（其实就是接口的实现类），方法体的内容和接口方法的内容
     * 一致，我们实现类在调用的时候其实就是调用这个同名方法。
     *
     *   public static final class DefaultImpls {
     *        public static void ss2(AS $this) {
     *           String var1 = "kotlin的方法可以存在默认实现，实现类可以不用重写";
     *           boolean var2 = false;
     *           System.out.println(var1);
     *       }
     *   }
     *
     */
    fun ss2() {
        println("kotlin的方法可以存在默认实现，实现类可以不用重写")
    }

}

interface AS2 {
    fun ss() {}
}

// 实现的接口存在相同方法一定要重写,即便实现类是抽象类或者接口。
interface AsImp : AS, AS2 {
    override fun ss() {
        // 多个父类存在相同的方法，调用父类的方法，要在super关键字后面加上"<父类名/接口名>"
        super<AS>.ss()
        super<AS2>.ss()
    }
}

class AsImp2 : AS {
    override fun ss() {
        // 没有实现多个接口且接口不存在相同的方法，可以省略super关键字后面的"<父类名/接口名>"
        super.ss()
    }
}

/**
 * 接口中的属性只能是抽象的，不允许初始化值,但是可以实现属性的get/set方法，相当于伪抽象。
 * 和java不同的是java的接口累的属性都是常量。
 *
 * 变量不允许存在初始化原因：使用kotlin bytecode编译成java后可以看出kotlin中定义的变量实际上会转换
 * 成setX()/getX()等抽象方法，所以不允许有初始化值。而允许实现get访问器是因为和定义默认抽象方法一样，
 * 内部有个默认名为DefaultImpls的final的静态内部类，存在同名且方法体就是get访问器内容的方法。
 */
interface A1 {
    // 接口属性不允许存在初始化；接口属性不允许存在幕后字段（幕后字段的存在需要初始化器）
    // 接口的var属性实现访问器的时候set/get都要实现，不能只实现一个。
    // var属性的set构造器不能实现方法体（可以有默认值（get构造器），但不能有默认实现，交给实现类实现）
    var a: String
        get() = "我们"
        set(value) {}

    // 接口的val属性实现访问器只用实现get访问器
    val bb: String
        get() = "你们"

    var name: String
}

class B1 : A1 {

    // 父接口存在get访问期，可以不重写，但重写则需要初始化
    override var a: String = ""

    // 没有初始化值，但是重写get方法后指向父类属性也相当于初始化
    override val bb: String
        get() = super.bb

    // 父类的name没有重写get，所以这里不能调用super.name，因为这样是无效的。
    override var name: String = ""
}

// 知识点5
fun main(args: Array<String>) {
    val b = B1()
    b.a = "你们形式上"   // 在1和2的声明方式下打印出来的还是A的值
    println(b.a)

}

