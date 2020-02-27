package com.zkp.breath.review.threads.collections;


/**
 * https://segmentfault.com/a/1190000016168566
 * <p>
 * ConcurrentSkipListMap，有序且查询快(传统链表查询慢)的同步Map，使用了类似链表的数据结构--跳表（Skip List）
 * <p>
 * Skip List的基本思想总结：
 * 1.跳表由很多层组成；(内部随机生成，但有个最大值作为边界)
 * 2.每一层都是一个有序链表；
 * 3.对于每一层的任意结点，不仅有指向下一个结点的指针，也有指向其下一层的指针。
 * <p>
 * ConcurrentSkipListMap会基于比较器——Comparator ，来进行键Key的比较，如果构造时未指定Comparator ，
 * 那么就会按照Key的自然顺序进行比较，所谓Key的自然顺序是指key实现Comparable接口。
 */
public class ConcurrentSkipListMapDemo {

    public static void main(String[] args) {

    }
}
