package com.zkp.breath.review.threads.locks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/***
 * LockSupport类的核心方法其实就两个：park()和unark()，其中park()方法用来阻塞当前调用线程，unpark()方法用于唤醒指定线程。
 * 这其实和Object类的wait()和signial()方法有些类似，但是LockSupport的这两种方法从语意上讲比Object类的方法更清晰，而且可以针对指定线程进行阻塞和唤醒。
 */
public class LockSupportDemoA {

    public static void main(String[] args) {
        FIFOMutex mutex = new FIFOMutex();
        MyThread a1 = new MyThread("a1", mutex);
        MyThread a2 = new MyThread("a2", mutex);
        MyThread a3 = new MyThread("a3", mutex);

        a1.start();
        a2.start();
        a3.start();

        try {
            a1.join();
            a2.join();
            a3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert MyThread.count == 300;
        System.out.print("Finished");
    }

    static class MyThread extends Thread {
        private String name;
        private FIFOMutex mutex;
        public static int count;

        public MyThread(String name, FIFOMutex mutex) {
            this.name = name;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                mutex.lock();
                count++;
                System.out.println("name:" + name + "  count:" + count);
                mutex.unlock();
            }
        }
    }

    /**
     * 假设现在需要实现一种FIFO类型的独占锁，可以把这种锁看成是ReentrantLock的公平锁简单版本，
     * 且是不可重入的，就是说当一个线程获得锁后，其它等待线程以FIFO的调度方式等待获取锁。
     */
    static class FIFOMutex {
        private final AtomicBoolean locked = new AtomicBoolean(false);
        private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

        public void lock() {
            Thread current = Thread.currentThread();
            waiters.add(current);

            // 如果当前线程不在队首，或锁已被占用，则当前线程阻塞
            // NOTE：这个判断的意图其实就是：锁必须由队首元素拿到
            while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
                /// 非队首元素进入阻塞
                LockSupport.park(this);
            }
            waiters.remove(); // 删除队首元素
        }

        public void unlock() {
            locked.set(false);
            // 获取到的新的头部的线程被唤醒
            LockSupport.unpark(waiters.peek());
        }
    }
}
