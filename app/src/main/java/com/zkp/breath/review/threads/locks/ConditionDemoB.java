package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的await和signal和wait，notify需要调用顺序，如果先执行唤醒再执行等待会引发死锁。
 */
public class ConditionDemoB {

    static Lock slock = new ReentrantLock();
    static Condition condition = slock.newCondition();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                conditionSignal();
            }
        }, "线程A").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                conditionWait();
            }
        }, "线程B").start();
    }

    public static void conditionWait() {
        System.out.println(Thread.currentThread().getName() + "进入conditionWait()");
        slock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            System.out.println(Thread.currentThread().getName() + "等待信号");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "拿到信号");
        } catch (Exception e) {

        } finally {
            slock.unlock();
        }
    }

    public static void conditionSignal() {
        System.out.println(Thread.currentThread().getName() + "进入conditionSignal()");
        slock.lock();
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "发出信号");
        } catch (Exception e) {

        } finally {
            slock.unlock();
        }
    }

}
