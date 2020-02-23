package com.zkp.breath.review.threads.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicXXXFieldUpdater，以一种线程安全的方式操作非线程安全对象的某些字段。
 */
public class AtomicFieldUpdaterDemoA {

    public static void main(String[] args) {
//        t1();
        t2();
    }

    //  线程安全的例子
    private static void t2() {
        Account2 account = new Account2(0);  // 初始金额0

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    account.increMoney();
                }
            });
            list.add(thread);
            thread.start();
        }

        // 等待所有线程执行完成
        for (Thread t : list) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(account.toString());
    }


    //  线程不安全的例子
    private static void t1() {
        Account account = new Account(0);  // 初始金额0

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    account.increMoney();
                }
            });
            list.add(thread);
            thread.start();
        }

        // 等待所有线程执行完成
        for (Thread t : list) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(account.toString());
    }

    static class Account2 {

        private volatile int money;

        //  指定的字段要加上volatile，因为内部提供的只有cas和自选。
        private AtomicIntegerFieldUpdater<Account2> accountAtomicIntegerFieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(Account2.class, "money");

        Account2(int initial) {
            this.money = initial;
        }

        public void increMoney() {
            accountAtomicIntegerFieldUpdater.incrementAndGet(this);
        }


        public int getMoney() {
            return money;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "money=" + money +
                    '}';
        }
    }

    static class Account {

        private volatile int money;

        Account(int initial) {
            this.money = initial;
        }

        public void increMoney() {
            money++;
        }

        public int getMoney() {
            return money;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "money=" + money +
                    '}';
        }
    }
}
