package com.zkp.breath.review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
        System.out.println("===========ArrayList===========");

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(1);
        integerArrayList.add(2);
        integerArrayList.add(3);
        // 数组是new的，但是元素浅拷贝
        Integer[] integers1 = integerArrayList.toArray(new Integer[3]);
        Integer[] integers2 = integerArrayList.toArray(new Integer[2]);
        System.out.println(Arrays.equals(integers1, integers2));    // 源码最终调用元素的equals()方法
        Integer[] srcTempIntegers = {1, 2, 3};
        Integer[] dstTempIntegers = new Integer[srcTempIntegers.length];
        // 集合的toArray()方法内部就是调用了System.arraycopy()
        System.arraycopy(srcTempIntegers, 0, dstTempIntegers, 0, dstTempIntegers.length);
        System.out.println(new ArrayList<>(Arrays.asList(srcTempIntegers)).toString());
        System.out.println();

        Integer[] integers = {1, 2, 3};
        List<Integer> integers3 = Arrays.asList(integers);  // 返回Arrays内部类ArrayList,不具备增删操作
        try {
            integers3.add(4);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("不具备该操作" + e.toString());
        }
        List<Integer> integers4 = new ArrayList<>(integers3);   // 转换为真正的ArrayList,具备增删操作
        integers4.add(4);
        System.out.println();

        List<String> stringList = new ArrayList<>();
        stringList.add("java");
        stringList.add("android");
        // 内部使用toArray()，集合的内存地址不同，但指向相同的元素（元素属于浅拷贝）
        List<String> stringList1 = new ArrayList<>(stringList);
        System.out.println(stringList.equals(stringList1));

        // 生成子集的后续操作会对父集生效，看源码
        List<String> subList = stringList.subList(0, stringList.size());
        subList.add("c++");
        System.out.println(stringList.toString());

        System.out.println();
        System.out.println("===========HashSet===========");

        // 无序且不可重复。LinkHashSet有序且不可重复
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("1");
        hashSet.add("2");
        hashSet.add("3");
        System.out.println(hashSet.toString());



    }
}
