package com.zkp.breath.designpattern.singleton;

/**
 * Created b Zwp on 2019/7/17.
 * 饿汉式（线程安全）
 *
 * 原理:依赖 JVM类加载机制，保证单例只会被创建1次，即线程安全。
 *     JVM在类的初始化阶段(即 在Class被加载后、被线程使用前)，会执行类的初始化
 *     在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化
 *
 * 使用场景：初始化速度快 & 占用内存小。
 *      原因：如果初始化速度慢，那么卡线程；如果内存大，还没使用到该类就占用了内存资源，不可取。
 */
public class EagerSingleton {

    // 1. 加载该类时，单例就会自动被创建
    private static EagerSingleton ourInstance = new EagerSingleton();

    // 2. 私有构造函数，禁止外部创建实例，保证实例只有一个
    private EagerSingleton() {
    }

    // 3. 通过调用静态方法获得创建的单例
    public static EagerSingleton newInstance() {
        return ourInstance;
    }
}
