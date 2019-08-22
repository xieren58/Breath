package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/22.
 */
public class Demo {

    public static void main(String[] args) {

        short s1 = 1;
        s1 = (short) (s1 + 1);  // 需要强转，否则编译报错，因为1是int类型。
        short s2 = 1;
        s2 += 1;    // += 是 java 语法规定的运算符，所以 java 编译器会对它进行转换特殊处理（可视为自动帮我们强转），故可以正确编译执行

        System.out.println();
        System.out.println("===========double乘法精度问题===========");

        // 0.1默认情况下属于double浮点型，相乘会有精度问题，但是加减没有精度问题
        System.out.println(3 * 0.1);
        System.out.println(3f * 0.1);
        // 0.1f为float，不会有精度问题
        System.out.println(3 * 0.1f);
        // 直接输出，不会有精度问题，因为你指定了精度。
        System.out.println(0.3);

        System.out.println();
        System.out.println("===========拆箱和封箱===========");

        double d1 = 200.0;
        Double d2 = 200.0;
        Double d3 = 200.0;
        System.out.println(d2.equals(d3));  // true，数值比较
        System.out.println(d2 == d3);   // false，内存比较，double有精度问题，没有缓存，每次都是new出来的
        System.out.println(d1 == d2);   // 其中一边是基本数据类型，触发拆箱进行数值比较
        System.out.println();

        float f1 = 200f;
        Float f2 = 200f;
        Float f3 = 200f;
        System.out.println(f2.equals(f3));  // true，数值比较
        System.out.println(f2 == f3);   // false，内存比较，double有精度问题，没有缓存，每次都是new出来的
        System.out.println(f1 == f2);   // 其中一边是基本数据类型，触发拆箱进行数值比较
        System.out.println();

        int i1 = 128;
        Integer i2 = 127;   // Integer，Long缓存-127 - 128的数值,
        Integer i3 = 127;
        Integer i4 = 128;
        Integer i5 = 1;
        System.out.println(i1 == i4);  // true。其中一边是基本数据类型，触发拆箱进行数值比较
        System.out.println(i2 == i3);  // true。内存比较，指向同一个内存
        System.out.println(i2.equals(i3));  // true。数值比较
        System.out.println(i3 == i4);   // false。内存比较，i4超过缓存，重新new
        System.out.println((i3 + i5) == i4); // true。两边有一边是表达式（包含算数运算）则 == 比较的是数值（自动触发拆箱过程）


        System.out.println();
        System.out.println("===========String字符串===========");

    }

}
