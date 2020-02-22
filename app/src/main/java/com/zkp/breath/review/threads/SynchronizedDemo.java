package com.zkp.breath.review.threads;

public class SynchronizedDemo {

    public static void main(String[] args) {
//        t1();
//        t2();
        t3();
    }

    private static void t3() {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();

        Thread thread5 = new Thread(new RunnableImp2(synchronizedDemo, true));
        Thread thread6 = new Thread(new RunnableImp2(synchronizedDemo, false));
        thread5.setName("CustomThread5");
        thread6.setName("CustomThread6");
        Thread.State state1 = thread5.getState();
        Thread.State state2 = thread6.getState();
        System.out.println("state5: " + state1 + ",state6: " + state2);

        thread5.start();
        thread6.start();
    }

    private static void t2() {
        SynchronizedDemo synchronizedDemo3 = new SynchronizedDemo();
        SynchronizedDemo synchronizedDemo4 = new SynchronizedDemo();

        Thread thread3 = new Thread(new RunnableImp1(synchronizedDemo3, true));
        Thread thread4 = new Thread(new RunnableImp1(synchronizedDemo4, false));
        thread3.setName("CustomThread3");
        thread4.setName("CustomThread4");
        Thread.State state3 = thread3.getState();
        Thread.State state4 = thread4.getState();
        System.out.println("state3: " + state3 + ",state4: " + state4);

        thread3.start();
        thread4.start();

    }

    private static void t1() {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();

        Thread thread1 = new Thread(new RunnableImp(synchronizedDemo, true));
        Thread thread2 = new Thread(new RunnableImp(synchronizedDemo, false));
        thread1.setName("CustomThread1");
        thread2.setName("CustomThread2");
        Thread.State state1 = thread1.getState();
        Thread.State state2 = thread2.getState();
        System.out.println("state1: " + state1 + ",state2: " + state2);

        thread1.start();
        thread2.start();
    }

    // 锁是该类的对象
    private synchronized void f1() {
        System.out.println("f1: " + Thread.currentThread().getName());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 锁是该类的对象
    private void f2() {
        synchronized (this) {
            System.out.println("f2: " + Thread.currentThread().getName());
        }
    }

    // 锁是class，不同对象共有一个class
    private void f3() {
        synchronized (SynchronizedDemo.class) {
            System.out.println("f3: " + Thread.currentThread().getName());
        }

        System.out.println("f3: " + Thread.currentThread().getName() + "startSleep");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("f3: " + Thread.currentThread().getName() + "endSleep");
    }

    // 锁是class，不同对象共有一个class
    private void f4() {
        synchronized (SynchronizedDemo.class) {
            System.out.println("f4: " + Thread.currentThread().getName());
        }
    }

    private void f5(boolean flag) {

        synchronized (this) {
            if (flag) {
                System.out.println("f5: " + Thread.currentThread().getName() + ",flag:true");
                System.out.println("f5: " + Thread.currentThread().getName() + ",startSleep");
                if (Thread.holdsLock(this)) {   // 能够知道当前是哪条线程持有锁
                    System.out.println("f5: " + Thread.currentThread().getName() + "线程在指定对象上保持监视器锁");
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("f5: " + Thread.currentThread().getName() + ",endSleep");
            } else {
                System.out.println("f5: " + Thread.currentThread().getName() + ",flag:false");
            }
        }

        System.out.println("f5: " + Thread.currentThread().getName());
    }


    private static class RunnableImp2 implements Runnable {

        private final SynchronizedDemo synchronizedDemo;
        private final boolean flag;

        public RunnableImp2(SynchronizedDemo synchronizedDemo, boolean flag) {
            this.synchronizedDemo = synchronizedDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            synchronizedDemo.f5(flag);
        }
    }


    private static class RunnableImp1 implements Runnable {

        private final SynchronizedDemo synchronizedDemo;
        private final boolean flag;

        public RunnableImp1(SynchronizedDemo synchronizedDemo, boolean flag) {
            this.synchronizedDemo = synchronizedDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                synchronizedDemo.f3();
            } else {
                synchronizedDemo.f4();
            }
        }
    }


    private static class RunnableImp implements Runnable {

        private final SynchronizedDemo synchronizedDemo;
        private final boolean flag;

        public RunnableImp(SynchronizedDemo synchronizedDemo, boolean flag) {
            this.synchronizedDemo = synchronizedDemo;
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                synchronizedDemo.f1();
            } else {
                synchronizedDemo.f2();
            }
        }
    }

}
