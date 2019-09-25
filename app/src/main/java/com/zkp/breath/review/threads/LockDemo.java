package com.zkp.breath.review.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        t1();
//        t2();
//        t3();
        t4();
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


}
