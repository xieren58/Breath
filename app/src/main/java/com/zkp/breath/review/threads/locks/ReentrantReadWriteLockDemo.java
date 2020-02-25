package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        t1(2);
    }

    private static void t1(int flag) {
        ReentrantReadWriteLockDemo reentrantLockDemo = new ReentrantReadWriteLockDemo();

        switch (flag) {
            case 1:
                // 读读
                Thread thread1 = new Thread(new ReadRunnableImp(reentrantLockDemo));
                Thread thread2 = new Thread(new ReadRunnableImp(reentrantLockDemo));
                thread1.setName("LockDemo_read_Thread1");
                thread2.setName("LockDemo_read_Thread2");
                thread1.start();
                thread2.start();
                break;
            case 2:
                // 读写
                Thread thread3 = new Thread(new ReadRunnableImp(reentrantLockDemo));
                Thread thread4 = new Thread(new WriteRunnableImp(reentrantLockDemo));
                thread3.setName("LockDemo_read_Thread3");
                thread4.setName("LockDemo_write_Thread4");
                thread3.start();
                thread4.start();
                break;
            case 3:
                // 写写
                Thread thread5 = new Thread(new WriteRunnableImp(reentrantLockDemo));
                Thread thread6 = new Thread(new WriteRunnableImp(reentrantLockDemo));
                thread5.setName("LockDemo_write_Thread5");
                thread6.setName("LockDemo_write_Thread6");
                thread5.start();
                thread6.start();
                break;
            default:
                break;
        }

    }

    private void read() {
        String name = Thread.currentThread().getName();

        reentrantReadWriteLock.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= 1) {
                System.out.println(name + "正在进行读操作");
            }
            System.out.println(name + "读操作完毕");
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    private void write() {
        String name = Thread.currentThread().getName();

        reentrantReadWriteLock.writeLock().lock();
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= 1) {
                System.out.println(name + "正在进行写操作");
            }
            System.out.println(name + "写操作完毕");
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }


    private static class ReadRunnableImp implements Runnable {

        private final ReentrantReadWriteLockDemo reentrantLockDemo;

        private ReadRunnableImp(ReentrantReadWriteLockDemo reentrantLockDemo) {
            this.reentrantLockDemo = reentrantLockDemo;
        }

        @Override
        public void run() {
            reentrantLockDemo.read();
        }
    }

    private static class WriteRunnableImp implements Runnable {

        private final ReentrantReadWriteLockDemo reentrantLockDemo;

        private WriteRunnableImp(ReentrantReadWriteLockDemo reentrantLockDemo) {
            this.reentrantLockDemo = reentrantLockDemo;
        }

        @Override
        public void run() {
            reentrantLockDemo.write();
        }
    }

}
