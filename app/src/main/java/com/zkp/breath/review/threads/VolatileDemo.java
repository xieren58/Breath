package com.zkp.breath.review.threads;

/**
 * 线程安全满足的条件：原子性，可见性，有序性。
 * <p>
 * volatile：一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义
 * 1.保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * 2.禁止进行指令重排序。
 * <p>
 * 总结：
 * 1. 可以直接读取变量（不需要执行加锁操作）因此不会阻塞线程，而读取volatile变量相当于进入同步代码块，因此volatile
 * 变量是一种比synchronized关键字更轻量级的同步机制。
 * 2. volatile关键字能保证可见性，原因是声明为volatile的简单变量如果当前值与该变量以前的值相关，那么volatile关
 * 键字不起作用。也就是说如下的表达式都不是原子操作：“count++”、“count = count+1”。
 * 3. volatile关键字能保证有序性。
 * 4. 不能保证原子性。
 *
 */
public class VolatileDemo {

    private volatile boolean shutdownRequested;

    public void shutdown() {
        shutdownRequested = true;
    }

    public void doWork() {
        int i = 0;
        while (!shutdownRequested) {
            // do stuff
            System.out.println("打印i：" + i);
            i++;
        }

        System.out.println("中断了。。。");
    }

    public static void main(String[] args) {

        VolatileDemo volatileDemo = new VolatileDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始循环");
                volatileDemo.doWork();
            }
        }).start();

        System.out.println("执行睡眠");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行设置标志进行中断");
                volatileDemo.shutdown();
            }
        });
        thread.start();

        System.out.println("执行睡眠2");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("是否处于活动状态：" + thread.isAlive());

    }

}
