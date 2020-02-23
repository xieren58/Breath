package com.zkp.breath.review.threads.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * cas乐观锁可能会出现ABA问题
 * 在理论上,如果初始数据A,对其实施CAS乐观锁技术,期望数据仍然是A的时候才能进行操作,但是当线程1进行的时候,
 * 线程2将A进行了修改,成B,然后再将B修改成A,此时线程1进入发现数据仍然为期望数据A,但是这个时候的A已经不是之前的A了,
 * CAS乐观锁在极端情况下因为是比较的是原始的数值,所以可能存在安全隐患.
 */
public class AtomicReferenceDemoB {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
    static AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<>(100, false);

    public static void main(String[] args) {
        aba();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atomicStampedReference();
        atomicMarkableReference();
    }

    // aba问题解决的方案2
    // 使用boolean标记（忽略过程，只是单纯关心是否被更改过）
    private static void atomicMarkableReference() {
        System.out.println("===============ABA问题的解决(AtomicMarkableReference)===========");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean marked = atomicMarkableReference.isMarked();
                System.out.println(Thread.currentThread().getName() + "\t第1次mark:" + marked);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicMarkableReference.compareAndSet(100, 99, atomicMarkableReference.isMarked(), !atomicMarkableReference.isMarked());
                System.out.println(Thread.currentThread().getName() + "\t第2次mark:" + atomicMarkableReference.isMarked());
                atomicMarkableReference.compareAndSet(99, 100, atomicMarkableReference.isMarked(), !atomicMarkableReference.isMarked());
                System.out.println(Thread.currentThread().getName() + "\t第3次mark:" + atomicMarkableReference.isMarked());
            }
        }, "t5").start();

        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t第1次mark:" + marked);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (marked) {
                boolean b = atomicMarkableReference.compareAndSet(100, 2019, atomicMarkableReference.isMarked(), !atomicMarkableReference.isMarked());
                System.out.println(Thread.currentThread().getName() + "\t修改成功否:" + b + "\t当前最新的实际mark:" + atomicMarkableReference.isMarked());
                System.out.println(Thread.currentThread().getName() + "\t当前实际最新值:" + atomicMarkableReference.getReference());
            }
        }, "t6").start();

    }

    // aba问题解决的方案1
    // 使用版本号标记（追踪整个变化过程）
    private static void atomicStampedReference() {
        System.out.println("===============ABA问题的解决(AtomicStampedReference)===========");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();//t3线程的版本号 此时是1
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //期望值 更新值 期望的版本号 以及更新的版本号
            atomicStampedReference.compareAndSet(100, 99, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(99, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号" + atomicStampedReference.getStamp());
        }, "t3").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t修改成功否:" + b + "\t当前最新的实际版本号:" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t当前实际最新值:" + atomicStampedReference.getReference());
        }, "t4").start();
    }

    // aba问题演示
    private static void aba() {
        System.out.println("=============ABA问题的产生演示=========");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 99);
            atomicReference.compareAndSet(99, 100);
        }, "t1").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get().toString());
        }, "t2").start();
    }
}
