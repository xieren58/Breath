package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 可重入性：
 * 如果你了解过synchronized关键字，一定知道他的可重入性，可重入就是同一个线程可以重复加锁，
 * 每次加锁的时候count值加1，每次释放锁的时候count减1，直到count为0，其他的线程才可以再次获取。
 * synchronized关键字要进行重复加锁，那么锁对象应该是多个变量，不能是this或者class。
 * <p>
 * 支持锁重入
 * A：同一线程在上读锁后还可以接着上读锁；
 * B：同一线程在上写锁之后既可以再次上写锁又可以上读锁；
 * <p>
 * 支持锁降级(其实和可重入性有点相似)
 * 写锁可以降级成读锁，读锁不能升级成写锁。
 * 解析：
 * 锁降级：上写锁-上读锁-释放写锁-释放读锁
 * 不可能锁升级：上读锁-上写锁-释放读锁-释放写锁
 */
public class ReentrantReadWriteLockDemoB {

    static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();
    static ReentrantReadWriteLock.ReadLock readLock = reentrantLock.readLock();

    public static void main(String[] args) {
        t1();
//        t2();
//        t3();
    }

    private static void t1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                f1();
            }
        }, "线程A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                f1();
            }
        }, "线程B").start();
    }

    private static void t2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                f2();
            }
        }, "线程C").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                f2();
            }
        }, "线程D").start();
    }

    private static void t3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                f3();
            }
        }, "线程E").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                f3();
            }
        }, "线程F").start();
    }

    // 同一线程在上写锁之后既可以再次上写锁又可以上读锁；
    private static void f1() {
        writeLock.lock();
        readLock.lock();
        readLock.lock();
        writeLock.lock();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

        writeLock.unlock();
        readLock.unlock();
        readLock.unlock();
        writeLock.unlock();
    }

    // 同一线程在上读锁后还可以接着上读锁；
    private static void f2() {
        readLock.lock();
        readLock.lock();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

        readLock.unlock();
        readLock.unlock();
    }

    // 错误例子，程序不执行。读锁之后只能获取读锁，不能够获取写锁
    private static void f3() {
        readLock.lock();
        writeLock.lock();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

        readLock.unlock();
        writeLock.unlock();
    }

}
