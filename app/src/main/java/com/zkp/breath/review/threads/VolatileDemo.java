package com.zkp.breath.review.threads;

/**
 * 线程安全满足的条件：原子性，可见性，有序性。
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