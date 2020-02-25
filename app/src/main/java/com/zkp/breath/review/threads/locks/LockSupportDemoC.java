package com.zkp.breath.review.threads.locks;

import java.util.concurrent.locks.LockSupport;

/**
 * 形象的理解，线程阻塞需要消耗凭证(permit)，这个凭证最多只有1个。当调用park方法时，如果有凭证，
 * 则会直接消耗掉这个凭证然后正常退出；但是如果没有凭证，就必须阻塞等待凭证可用；而unpark则相反，
 * 它会增加一个凭证，但凭证最多只能有1个。
 * <p>
 * 总结：先执行unpark后执行park不会导致阻塞。
 * <p>
 * 为什么可以先唤醒线程后阻塞线程？
 * 因为unpark获得了一个凭证,之后调用park因为有凭证消费，故不会阻塞。
 */
public class LockSupportDemoC {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("被中断了");
                }
                System.out.println("继续执行");
            }
        }
    }

    public static void main(String[] args) {
        t1.start();
        LockSupport.unpark(t1);
        System.out.println("unpark invoked");
    }
}
