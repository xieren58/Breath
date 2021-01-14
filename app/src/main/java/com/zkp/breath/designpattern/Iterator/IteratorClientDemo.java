package com.zkp.breath.designpattern.Iterator;

/**
 * 迭代器模式:提供一种方式访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。属于行为型模式。
 */
public class IteratorClientDemo {

    public static void main(String[] args) {
        String[] names = {"Robert", "John", "Julie", "Lora"};

        ConcreteContainer concreteContainer = new ConcreteContainer();
        concreteContainer.setData(names);

        Iterator iterator = concreteContainer.getIterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println("打印信息：" + next);
        }
    }

}
