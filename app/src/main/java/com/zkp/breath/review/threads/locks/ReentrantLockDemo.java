package com.zkp.breath.review.threads.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantLock类，实现了Lock接口，是一种可重入的独占锁。
 * <p>
 * ReentrantLock类的其中一个构造器提供了指定公平策略 / 非公平策略的功能，默认为非公平策略。
 * 公平策略：在多个线程争用锁的情况下，公平策略倾向于将访问权授予等待时间最长的线程。也就是说，
 * 相当于有一个线程等待队列，先进入等待队列的线程后续会先获得锁，这样按照“先来后到”的原则，对于每一个等待线程都是公平的。
 * 非公平策略：在多个线程争用锁的情况下，能够最终获得锁的线程是随机的（由底层OS调度）。
 * <p>
 * 注意：
 * 1.一般情况下，使用公平策略的程序在多线程访问时，总体吞吐量（即速度很慢，常常极其慢）比较低，因为此时在线程调度上面的开销比较大。
 * 2.在遇到异常的时候不会自动释放锁，所以要在try-catch中的finally语块中调用释放
 * 3.当线程持有lock锁，没有手动释放锁就进行sleep后其他线程是无法获取锁的。
 */
public class ReentrantLockDemo {

    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        t1();
//        t2();
//        t3();
//        t4();
    }

    private static void t4() {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        Thread thread1 = new Thread(new RunnableImp3(reentrantLockDemo, true));
        Thread thread2 = new Thread(new RunnableImp3(reentrantLockDemo, false));
        thread1.setName("LockDemo_Thread1");
        thread2.setName("LockDemo_Thread2");
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }

    private static void t3() {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        Thread thread1 = new Thread(new RunnableImp2(reentrantLockDemo, true));
        Thread thread2 = new Thread(new RunnableImp2(reentrantLockDemo, false));
        thread1.setName("LockDemo_Thread1");
        thread2.setName("LockDemo_Thread2");
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用tryLock()在等待期间如果还没获取到锁的话调用interrupt()则会响应InterruptedException
        thread2.interrupt();
    }

    private static void t2() {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        Thread thread1 = new Thread(new RunnableImp1(reentrantLockDemo, true));
        Thread thread2 = new Thread(new RunnableImp1(reentrantLockDemo, false));
        thread1.setName("LockDemo_Thread1");
        thread2.setName("LockDemo_Thread2");
        thread1.start();
        thread2.start();
    }

    private static void t1() {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        Thread thread1 = new Thread(new RunnableImp(reentrantLockDemo, true));
        Thread thread2 = new Thread(new RunnableImp(reentrantLockDemo, false));
        thread1.setName("LockDemo_Thread1");
        thread2.setName("LockDemo_Thread2");
        thread1.start();
        thread2.start();
    }

    private void lockMethod(boolean flag) {
        lock.lock();
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result += i;
        }
        System.out.println(Thread.currentThread().getName() + " :" + result);
        if (flag) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    private void tryLockMethod(boolean flag) {
        if (lock.tryLock()) {
            // 一定要判断成功才进入，否则即便返回值是false也执行的话被报IllegalMonitorStateException
            try {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    result += i;
                }
                System.out.println(Thread.currentThread().getName() + " :" + result);
                if (flag) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void tryLockByTimeMethod(boolean flag) throws InterruptedException {
        // 在等待获取锁的时间内能获取锁则继续执行，不能则放弃（放弃后上锁的代码是不会执行，就是注释1处的代码以及后面都不会执行）
        if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
            // 一定要判断成功才进入，否则即便返回值是false也执行的话被报IllegalMonitorStateException
            try {   // 1
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    result += i;
                }
                System.out.println(Thread.currentThread().getName() + " :" + result);
                if (flag) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void lockInterruptiblyMethod(boolean flag) throws InterruptedException {
        //就是如果锁不可用，那么当前正在等待的线程是可以被中断的
        lock.lockInterruptibly();
        try {
            int result = 0;
            for (int i = 0; i < 100; i++) {
                result += i;
            }
            System.out.println(Thread.currentThread().getName() + " :" + result);
            if (flag) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private static class RunnableImp implements Runnable {

        private final ReentrantLockDemo reentrantLockDemo;
        private final boolean flag;

        private RunnableImp(ReentrantLockDemo reentrantLockDemo, boolean flag) {
            this.reentrantLockDemo = reentrantLockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            reentrantLockDemo.lockMethod(flag);
        }
    }

    private static class RunnableImp1 implements Runnable {

        private final ReentrantLockDemo reentrantLockDemo;
        private final boolean flag;

        private RunnableImp1(ReentrantLockDemo reentrantLockDemo, boolean flag) {
            this.reentrantLockDemo = reentrantLockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            reentrantLockDemo.tryLockMethod(flag);
        }
    }

    private static class RunnableImp2 implements Runnable {

        private final ReentrantLockDemo reentrantLockDemo;
        private final boolean flag;

        private RunnableImp2(ReentrantLockDemo reentrantLockDemo, boolean flag) {
            this.reentrantLockDemo = reentrantLockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                reentrantLockDemo.tryLockByTimeMethod(flag);
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + ": 哈哈哈");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "被中断");
            }
        }
    }

    private static class RunnableImp3 implements Runnable {

        private final ReentrantLockDemo reentrantLockDemo;
        private final boolean flag;

        private RunnableImp3(ReentrantLockDemo reentrantLockDemo, boolean flag) {
            this.reentrantLockDemo = reentrantLockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                reentrantLockDemo.lockInterruptiblyMethod(flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "被中断");
            }
        }
    }


}
