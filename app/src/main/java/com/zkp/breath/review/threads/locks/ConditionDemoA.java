package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁都实现了Lock类。
 * 显式协作 Condition 的 await、signal、signalAll 需要与显式锁 Lock 配合使用。
 * 调用 await、signal、signalAll 方法都必须在 lock 保护之内。
 * <p>
 * Condition 就可以明确的指定唤醒的线程。（阻塞队列只存放一个线程；synchronzied关键字也是能做到的，创建不同的锁对象就行）
 */
public class ConditionDemoA {

    // AQS，同步队列
    private Lock lock = new ReentrantLock();
    // 3个等待队列
    private Condition cond1 = lock.newCondition();
    private Condition cond2 = lock.newCondition();
    private Condition cond3 = lock.newCondition();

    public static void main(String[] args) {
        final ConditionDemoA work = new ConditionDemoA();
        new Thread() {
            public void run() {
                work.coderCmd();
            }
        }.start();

        new Thread() {
            public void run() {
                work.teamLeaderCmd();
            }
        }.start();

        new Thread() {
            public void run() {
                work.managerCmd();
            }
        }.start();

        // 延迟主线程获取锁和cpu的执行权
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        work.bossCmd();
    }

    private void bossCmd() {
        try {
            lock.lock();
            cond1.signal();
            System.out.println("Boss have a plain!");
        } finally {
            lock.unlock();
        }
    }

    private void managerCmd() {
        try {
            lock.lock();
            cond1.await();  // 进入c1等待队列，释放了锁和让出cpu执行权
            Thread.sleep(500);
            System.out.println("Manager received Cmd!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cond3.signal();
            lock.unlock();
        }
    }

    private void coderCmd() {
        try {
            lock.lock();
            // 调用方法所在的线程进入等待状态
            cond2.await();
            Thread.sleep(500);
            System.out.println("Coder received Cmd!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cond1.signal();
            lock.unlock();
        }
    }

    private void teamLeaderCmd() {
        try {
            lock.lock();
            cond3.await();
            Thread.sleep(500);
            System.out.println("TeamLeader received Cmd!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cond2.signal();
            lock.unlock();
        }
    }

}
