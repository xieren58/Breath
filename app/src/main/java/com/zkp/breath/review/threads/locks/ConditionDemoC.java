package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemoC {

    public static void main(String[] args) {
        A a = new A();

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        a.produce();
                    }
                }
            }, "生产线程" + i).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        a.consume();
                    }
                }
            }, "消费线程" + i).start();
        }
    }

    static class A {
        int count;
        int max = 100;
        Lock lock = new ReentrantLock();
        Condition emptyCondition = lock.newCondition();
        Condition fullCondition = lock.newCondition();

        public void produce() {
            lock.lock();
            // 自旋判断是否生产到最大值，是则挂起，不是则继续生产且唤醒消费队列，最后解锁
            try {
                while (count == max) {
                    System.out.println("生产达到最大值：" + Thread.currentThread().getName());
                    fullCondition.await();
                }
                count++;
                System.out.println(Thread.currentThread().getName() + "生产第" + this.count + "个产品.");
                emptyCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void consume() {
            lock.lock();
            try {
                while (count == 0) {
                    System.out.println("已全部消费完毕：" + Thread.currentThread().getName());
                    emptyCondition.await();
                }
                System.out.println(Thread.currentThread().getName() + "取走了第" + this.count + "个产品.");
                count--;
                fullCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
