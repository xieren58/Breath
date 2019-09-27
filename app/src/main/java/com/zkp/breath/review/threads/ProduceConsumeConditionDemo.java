package com.zkp.breath.review.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock实现并发多线程的生产者消费者demo
 */
public class ProduceConsumeConditionDemo {

    private static final int MAX_PRODUCT = 20;
    private static final int MIN_PRODUCT = 0;
    private int product;

    // AQS，同步队列
    private Lock lock = new ReentrantLock();
    private Condition produceCondition = lock.newCondition();
    private Condition consumeCondition = lock.newCondition();


    public static void main(String[] args) {
        ProduceConsumeConditionDemo produceConsumeDemo = new ProduceConsumeConditionDemo();

        Thread p1 = new Thread(new ProduceRunnableImp(produceConsumeDemo));
        Thread p2 = new Thread(new ProduceRunnableImp(produceConsumeDemo));
        Thread p3 = new Thread(new ProduceRunnableImp(produceConsumeDemo));
        p1.setName("p1");
        p2.setName("p2");
        p3.setName("p3");

        Thread c1 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));
        Thread c2 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));
        Thread c3 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));
        c1.setName("c1");
        c2.setName("c2");
        c3.setName("c3");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }

    private void produceByCondition() {
        lock.lock();
        try {
            while (this.product >= MAX_PRODUCT) {
                System.out.println("产品已满,请稍候再生产");
                produceCondition.await();
            }
            this.product++;
            System.out.println(Thread.currentThread().getName() + "生产者生产第" + this.product + "个产品.");
            consumeCondition.signal();  // 指定唤醒消费者的等待队列，而synchronized关键字只有一个队列且不能指定
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 消费者从店员取产品
     */
    private void consumeByCondition() {
        lock.lock();
        try {
            while (this.product == MIN_PRODUCT) {
                System.out.println("缺货,稍候再取");
                consumeCondition.await(); // 释放锁和让出cpu执行权
            }

            System.out.println(Thread.currentThread().getName() + "消费者取走了第" + this.product + "个产品.");
            this.product--;
            produceCondition.signal();  // 指定唤醒生产者的等待队列，而synchronized关键字只有一个队列且不能指定
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static class ProduceRunnableImp implements Runnable {

        private final ProduceConsumeConditionDemo produceConsumeDemo;

        public ProduceRunnableImp(ProduceConsumeConditionDemo produceConsumeDemo) {
            this.produceConsumeDemo = produceConsumeDemo;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    produceConsumeDemo.produceByCondition();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ConsumeRunnableImp implements Runnable {

        private final ProduceConsumeConditionDemo produceConsumeDemo;

        public ConsumeRunnableImp(ProduceConsumeConditionDemo produceConsumeDemo) {
            this.produceConsumeDemo = produceConsumeDemo;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);
                    produceConsumeDemo.consumeByCondition();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
