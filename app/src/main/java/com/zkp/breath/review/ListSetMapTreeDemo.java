package com.zkp.breath.review;

import com.zkp.breath.review.bean.HashMapBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ListSetMapTreeDemo {

    public static void main(String[] args) {
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

        // 添加final的对象作为key，防止修改成员变量导致hashcode出现问题
        HashMap<HashMapBean, Integer> hashMapBeanIntegerHashMap = new HashMap<>();
        final HashMapBean hashMapBean1 = new HashMapBean("N1", 1);
        final HashMapBean hashMapBean2 = new HashMapBean("N2", 2);
        final HashMapBean hashMapBean3 = new HashMapBean("N3", 3);
        hashMapBeanIntegerHashMap.put(hashMapBean1, 1);
        hashMapBeanIntegerHashMap.put(hashMapBean2, 2);
        hashMapBeanIntegerHashMap.put(hashMapBean3, 3);
    }

}
