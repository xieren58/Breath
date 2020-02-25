package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.LockSupport;

/**
 * park和unpark其实实现了wait和notify的功能，不过还是有一些差别的:
 * A:park可以不需要获取某个对象的锁。
 * B:unpark可以指定线程进行唤醒。
 * C:如果当前线程在挂在状态，当被中断的时候也会被唤醒。（park是会响应中断的，但是不会抛出异常，wait会抛出异常）
 * D:因为中断的时候park不会抛出InterruptedException异常，所以需要在park之后自行判断中断状态，
 * 然后做额外的处理。
 * E:park()和unpark()方法的调用顺序不会引起死锁。
 */
public class LockSupportDemoB {

    public static Object o = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnableImp(), "线程A");
        Thread thread2 = new Thread(new RunnableImp(), "线程B");

        thread1.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠1秒");

        thread2.start();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠3秒");

        System.out.println("线程1持有的blocker: " + LockSupport.getBlocker(thread1));

        thread1.interrupt();
        System.out.println("线程A执行interrupt()");

        LockSupport.unpark(thread2);
    }

    static class RunnableImp implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + ":进入run方法");
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + ":start");
                // 方法执行所在的线程挂起
                LockSupport.park(this);
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "被中断了");
                }
                System.out.println(Thread.currentThread().getName() + ":end");
            }
        }
    }

}
