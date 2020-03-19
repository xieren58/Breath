package com.zkp.breath.review.threads.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue是基于数组(环形结构，因为构造需要指定长度)实现的阻塞队列(使用ReentrantLock保证并发安全，
 * 两个Condition等待队列，一个用于存放生产者，一个用于存放消费者)，
 * 它实现了BlockingQueue接口。
 * <p>
 * ArrayBlockingQueue是一种有界阻塞队列，在初始构造的时候需要指定队列的容量。具有如下特点：
 * 1.队列的容量一旦在构造时指定，后续不能改变；
 * 2.插入元素时，在队尾进行；删除元素时，在队首进行；
 * 3.队列满时，调用特定方法插入元素会阻塞线程；队列空时，删除元素也会阻塞线程；
 * 4.支持公平/非公平策略，默认为非公平策略。这里的公平策略，是指当线程从阻塞到唤醒后，以最初请求的顺序（FIFO）
 * 来添加或删除元素；非公平策略指线程被唤醒后，谁先抢占到锁，谁就能往队列中添加/删除顺序，是随机的。
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.put("a");
            queue.put("b");
            queue.put("c");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 一定要提前出来，因为take是会改变size的
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            try {
                String take = queue.take();
                System.out.println("take:" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
