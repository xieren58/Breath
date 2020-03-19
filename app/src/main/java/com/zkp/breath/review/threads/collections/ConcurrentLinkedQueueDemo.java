package com.zkp.breath.review.threads.collections;

/**
 * ConcurrentLinkedQueue底层是基于链表实现的队列（先进先出）,并发安全（自旋+CAS的方式，适合并发量适中的场景）。
 * <p>
 * ConcurrentLinkedQueue使用了自旋+CAS的非阻塞算法来保证线程并发访问时的数据一致性。由于队列本身是一种链表结构，
 * 所以虽然算法看起来很简单，但其实需要考虑各种并发的情况，实现复杂度较高，并且ConcurrentLinkedQueue不具备实时
 * 的数据一致性，实际运用中，队列一般在生产者-消费者的场景下使用得较多，所以ConcurrentLinkedQueue的使用场景并
 * 如阻塞队列那么多。
 * 注意：
 * 1.ConcurrentLinkedQueue的迭代器是弱一致性的，这在并发容器中是比较普遍的现象，主要是指在一个线程在遍历队列
 * 结点而另一个线程尝试对某个队列结点进行修改的话不会抛出ConcurrentModificationException，这也就造成在遍历某
 * 个尚未被修改的结点时，在next方法返回时可以看到该结点的修改，但在遍历后再对该结点修改时就看不到这种变化。
 * 2.size方法需要遍历链表，所以在并发情况下，其结果不一定是准确的，只能供参考。
 */
public class ConcurrentLinkedQueueDemo {

}
