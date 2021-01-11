package com.zkp.breath.designpattern.facade;

/**
 * 外观模式:隐藏系统的复杂性，并向客户端提供了一个客户端可以访问系统的接口。这种类型的设计模式属于结构型模式。
 * 其实实际开发中不知不觉都在使用这个模式。
 * <p>
 * 1. 避免客户端直接访问系统，解耦客户端与系统置间的耦合度。
 * 2. 降低访问复杂系统时的复杂度。
 * <p>
 * 和代理模式的主要区别：
 * 1. 代理类和被代理类有相同的行为（可以认为继承同一个接口），而外观类和系统之间没有存在相同行为。
 * 2. 代理类是为了提高某一个类的安全性和降低某一个类的可见性，而外观类则是为了降低直接访问系统时的复杂度以及解耦
 * 客户端和系统之间的耦合度。
 * 3. 代理是一对一，外观可以是一对多。
 */
public class FacadeClientDemo {

    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
        facade.methodC();
    }

}
