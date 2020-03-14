package com.zkp.breath.designpattern.singleton;

public class LazySingleton {

    // 双重锁
    private static volatile LazySingleton instance = null;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (instance == null) { // 1
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    /**
                     * a. memory = allocate() //分配内存
                     * b. ctorInstanc(memory) //初始化对象
                     * c. instance = memory //设置instance指向刚分配的地址
                     *
                     * 防止指令重排
                     * 下面的代码在编译运行时，可能会出现重排序从a-b-c排序为a-c-b，那么多线程可能会出现问题，
                     * 当线程A在执行第4行代码时，B线程进来执行到第1行代码。假设此时A执行的过程中发生了指令重排序，
                     * 即先执行了a和c，没有执行b，那么由于A线程执行了c导致instance指向了一段地址，所以B线程判断instance不为null，
                     * 会直接跳到第5行并返回一个未初始化的对象。
                     */
                    instance = new LazySingleton(); // 4
                }
            }
        }
        return instance;    // 5
    }
}
