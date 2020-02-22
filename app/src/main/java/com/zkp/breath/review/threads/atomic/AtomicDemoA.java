package com.zkp.breath.review.threads.atomic;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性类是一种乐观锁（无阻塞，不用进行线程的上下文切换），相对于synchronized悲观锁（阻塞，要进行线程的上下文切换开销）
 * 原子类内部调用Unsafe类，而自增等方法使用了unsafe的自旋（有退出的无判断条件循环）和CAS保证线程安全
 */
public class AtomicDemoA {

    public static void main(String[] args) throws InterruptedException {
        t1();
    }

    private static void t1() throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Accumlator(ai), "线程-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println("执行完的结果：" + ai.get());
    }

    static class Accumlator implements Runnable {
        private AtomicInteger ai;

        Accumlator(AtomicInteger ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
//            f1();
            f2();
        }

        // 方式1
        private void f1() {
            for (int i = 0, len = 50; i < len; i++) {
                int get = ai.incrementAndGet();
                System.out.println("线程: " + Thread.currentThread().getName() + ",原子类的值：" + get);
            }
        }

        // 方式2
        private void f2() {
            for (int i = 0; i < 50; i++) {
                // 要加死循环，保证当次操作完成
                for (; ; ) {
                    int current = ai.get();
                    int next = current + 1;
                    if (ai.compareAndSet(current, next)) {
                        System.out.println("线程: " + Thread.currentThread().getName() + ",原子类的值：" + ai.get());
                        // 退出当次死循环
                        break;
                    }
                }
            }
        }
    }

}
