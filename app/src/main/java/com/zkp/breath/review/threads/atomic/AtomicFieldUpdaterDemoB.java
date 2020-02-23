package com.zkp.breath.review.threads.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicFieldUpdaterDemoB {

    public static void main(String[] args) {

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

    static class Account2 {

        private volatile Integer money;

        //  指定的字段要加上volatile，因为内部提供的只有cas和自选。
        private AtomicReferenceFieldUpdater<Account2,Integer> atomicReferenceFieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(Account2.class, Integer.class, "money");


        Account2(int initial) {
            this.money = initial;
        }

        public void increMoney() {
            for (; ; ) {
                Integer integer = atomicReferenceFieldUpdater.get(this);
                if (atomicReferenceFieldUpdater.compareAndSet(this, integer, integer + 1)) {
                    break;
                }
            }
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
