package com.zkp.breath.review.threads;

/**
 * 每个线程都有一个ThreadLocalMap，ThreadLocal其实是用当前线程去获取了自身的ThreadLocalMap变量，然后进行存取,
 * 存放的key是ThreadLocal，value则为我们传入的value。
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private static class RunnableImp implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                threadLocal.set(i);
                System.out.println(Thread.currentThread().getName() + "获取:" + threadLocal.get());
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new Thread(new RunnableImp(), "线程A");
        Thread threadB = new Thread(new RunnableImp(), "线程B");

        threadA.start();
        threadB.start();
    }
}
