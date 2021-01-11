package com.zkp.breath.designpattern.prototype;

/**
 * 原型模式：通过拷贝原型对象创建新的对象，是用于创建重复的对象。当直接创建对象的代价比较大时或者过程比较繁琐，
 * 则采用这种模式。（其实核心就是 Cloneable 接口中调用Object的clone方法）
 * 优点：
 * 1.性能提高。
 * 2.逃避构造函数的约束。
 * 3.隐藏创建对象的细节。
 * <p>
 * 涉及到了浅克隆和深克隆的知识
 *
 * @see com.zkp.breath.review.clone
 */
class PrototypeClientDemo {
    public static void main(String[] args) {
        ConcretePrototypeA concretePrototypeA = new ConcretePrototypeA();
        concretePrototypeA.setAge(21);
        concretePrototypeA.setId("zkp");

        ConcretePrototypeA clone = (ConcretePrototypeA) concretePrototypeA.clone();
        concretePrototypeA.action();
        System.out.println("=========================");
    }
}
