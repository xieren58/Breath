package com.zkp.breath.review.threads.collections;


import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * https://segmentfault.com/a/1190000016168566
 * <p>
 * ConcurrentSkipListMap，有序(和TreeMap一样是根据key排序，不是指按插入顺序)且查询快(传统链表查询慢)的同步（cas操作）Map，
 * 使用了类似链表的数据结构--跳表（Skip List,空间换时间的设计思想）
 * <p>
 * Skip List的基本思想总结：
 * 1.跳表由很多层组成；(内部随机生成，但有个最大值作为边界)
 * 2.每一层都是一个有序链表；
 * 3.对于每一层的任意结点，不仅有指向下一个结点的指针，也有指向其下一层的指针。
 * <p>
 * ConcurrentSkipListMap会基于比较器——Comparator ，来进行键Key的比较，如果构造时未指定Comparator ，
 * 那么就会按照Key的自然顺序进行比较，所谓Key的自然顺序是指key实现Comparable接口。
 *
 * 删除是一个懒删除以此提高并发效率，其实就是在该删除节点的后面添加一个value为null的节点，然后改变删除节点的前节点的指向
 * ，而原版本指向该删除节点的其他层的上层节点修改为指向value为null的节点，而上层节点的前节点断开指向其上层节点。其实
 * 总结就是断开所有指向删除节点的指向，最后在后续的查找或者插入中会被清除或者gc回收
 */
public class ConcurrentSkipListMapDemo {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put(3, "a");
        concurrentSkipListMap.put(2, "b");
        concurrentSkipListMap.put(4, "c");
        concurrentSkipListMap.put(1, "d");
        concurrentSkipListMap.put(5, "e");

        for (Map.Entry<Integer, String> next : concurrentSkipListMap.entrySet()) {
            Integer key = next.getKey();
            String value = next.getValue();
            System.out.println("key:" + key + ",value:" + value);
        }
    }
}
