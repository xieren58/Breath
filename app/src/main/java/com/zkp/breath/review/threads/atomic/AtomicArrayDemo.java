package com.zkp.breath.review.threads.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Atomic数组，顾名思义，就是能以原子的方式，操作数组中的元素。
 * 注意：原子数组并不是说可以让线程以原子方式一次性地操作数组中所有元素的数组。
 * 而是指对于数组中的每个元素，可以以原子方式进行操作。
 * 总结：原子数组类型其实可以看成原子类型组成的数组。
 */
public class AtomicArrayDemo {

    public static void main(String[] args) {

        AtomicIntegerArray array = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
        array.getAndIncrement(0);   // 将第0个元素原子地增加1

        // 相当于
        AtomicInteger[] array1 = new AtomicInteger[]{new AtomicInteger(1),
                new AtomicInteger(2),
                new AtomicInteger(3)};
        array1[0].getAndIncrement();  // 将第0个元素原子地增加1
    }
}
