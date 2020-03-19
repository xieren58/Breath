package com.zkp.breath.review.threads.collections;

/**
 * 内部引用了CopyOnWriteArrayList，在添加的适合会判断是否存在该元素，不存在则添加。
 * 其实相当于并发安全的ArraySet。
 */
public class CopyOnWriteArraySetDemo {

}
