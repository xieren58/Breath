package com.zkp.breath.review.threads.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDemo {

    private final Lock lock = new ReentrantLock();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
//        t1();
//        t2();
//        t3();
//        t4();
        t5(2);
    }

    private static void t5(int flag) {
        LockDemo lockDemo = new LockDemo();

        switch (flag) {
            case 1:
                // 读读
                Thread thread1 = new Thread(new ReadRunnableImp(lockDemo));
                Thread thread2 = new Thread(new ReadRunnableImp(lockDemo));
                thread1.setName("LockDemo_read_Thread1");
                thread2.setName("LockDemo_read_Thread2");
                thread1.start();
                thread2.start();
                break;
            case 2:
                // 读写
                Thread thread3 = new Thread(new ReadRunnableImp(lockDemo));
                Thread thread4 = new Thread(new WriteRunnableImp(lockDemo));
                thread3.setName("LockDemo_read_Thread3");
                thread4.setName("LockDemo_write_Thread4");
                thread3.start();
                thread4.start();
                break;
            case 3:
                // 写写
                Thread thread5 = new Thread(new WriteRunnableImp(lockDemo));
                Thread thread6 = new Thread(new WriteRunnableImp(lockDemo));
                thread5.setName("LockDemo_write_Thread5");
                thread6.setName("LockDemo_write_Thread6");
                thread5.start();
                thread6.start();
                break;
            default:
                break;
        }

    }

    private static void t4() {
        LockDemo lockDemo = new LockDemo();

        Thread thread1 = new Thread(new RunnableImp3(lockDemo, true));
        Thread thread2 = new Thread(new RunnableImp3(lockDemo, false));
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
        LockDemo lockDemo = new LockDemo();

        Thread thread1 = new Thread(new RunnableImp2(lockDemo, true));
        Thread thread2 = new Thread(new RunnableImp2(lockDemo, false));
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
        LockDemo lockDemo = new LockDemo();

        Thread thread1 = new Thread(new RunnableImp1(lockDemo, true));
        Thread thread2 = new Thread(new RunnableImp1(lockDemo, false));
        thread1.setName("LockDemo_Thread1");
        thread2.setName("LockDemo_Thread2");
        thread1.start();
        thread2.start();
    }

    private static void t1() {
        LockDemo lockDemo = new LockDemo();

        Thread thread1 = new Thread(new RunnableImp(lockDemo, true));
        Thread thread2 = new Thread(new RunnableImp(lockDemo, false));
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

    private static class RunnableImp implements Runnable {

        private final LockDemo lockDemo;
        private final boolean flag;

        private RunnableImp(LockDemo lockDemo, boolean flag) {
            this.lockDemo = lockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            lockDemo.lockMethod(flag);
        }
    }

    private static class RunnableImp1 implements Runnable {

        private final LockDemo lockDemo;
        private final boolean flag;

        private RunnableImp1(LockDemo lockDemo, boolean flag) {
            this.lockDemo = lockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            lockDemo.tryLockMethod(flag);
        }
    }

    private static class RunnableImp2 implements Runnable {

        private final LockDemo lockDemo;
        private final boolean flag;

        private RunnableImp2(LockDemo lockDemo, boolean flag) {
            this.lockDemo = lockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                lockDemo.tryLockByTimeMethod(flag);
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

        private final LockDemo lockDemo;
        private final boolean flag;

        private RunnableImp3(LockDemo lockDemo, boolean flag) {
            this.lockDemo = lockDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                lockDemo.lockInterruptiblyMethod(flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "被中断");
            }
        }
    }

    private static class ReadRunnableImp implements Runnable {

        private final LockDemo lockDemo;

        private ReadRunnableImp(LockDemo lockDemo) {
            this.lockDemo = lockDemo;
        }

        @Override
        public void run() {
            lockDemo.read();
        }
    }

    private static class WriteRunnableImp implements Runnable {

        private final LockDemo lockDemo;

        private WriteRunnableImp(LockDemo lockDemo) {
            this.lockDemo = lockDemo;
        }

        @Override
        public void run() {
            lockDemo.write();
        }
    }

}
