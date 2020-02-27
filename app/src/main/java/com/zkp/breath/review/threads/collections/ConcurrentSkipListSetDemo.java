package com.zkp.breath.review.threads.collections;

/**
 * SET类型，在数学上称为“集合”，具有互异性、无序性的特点，也就是说SET中的任意两个元素均不相同（即不包含重复元素），且元素是无序的。
 * 是不是感觉和HashMap有点类似？HashMap中的Key也是不能重复，且是无序的。
 * 事实上，JDK提供的默认SET实现——HashSet，其实就是采用“组合”的方式——内部引用了一个HashMap对象，以此实现SET的功能。
 * <p>
 * ConcurrentSkipListSet的实现非常简单，其内部引用了一个ConcurrentSkipListMap对象，所有API方法均委托ConcurrentSkipListMap对象完成
 */
public class ConcurrentSkipListSetDemo {
}
