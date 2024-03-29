package com.zkp.breath.designpattern.observerable;

/**
 * 观察者模式：定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动
 * 更新，属于行为型模式。
 * <p>
 * 难点：不要看名字。这里不能以中文名字的方式去理解，因为观察者反而是被动触发的，而被观察者是主动触发的。这里以
 * 广播发送者-广播接收者去理解就行，接收者注册在发送者里面，发送者有消息就会调起接收者，接收者也就相当于被动收
 * 到了信息。
 * <p>
 * 其实java有内置的观察者模板工具类：java.util.Observerable（大部分方法都使用synchronized线程同步机制，存储
 * 的集合是Vector，相当于双重锁，不推荐）和java.util.Observer
 */
public class ObserverClientDemo {

    public static void main(String[] args) {
        cus();
        util();
    }

    // java内置的工具类
    private static void util() {
        //创建被观察者
        UtilObservable journal = new UtilObservable();
        //创建三个不同的观察者
        UtilObserver consumerA = new UtilObserver("A");
        UtilObserver consumerB = new UtilObserver("B");
        UtilObserver consumerC = new UtilObserver("C");
        //将观察者注册到被观察者中
        journal.addObserver(consumerA);
        journal.addObserver(consumerB);
        journal.addObserver(consumerC);
        //更新被观察者中的数据
        journal.setInfomation(6, 11);
    }

    private static void cus() {
        //创建被观察者
        ObserverableImp magazine = new ObserverableImp();

        //创建三个不同的观察者
        ObserverImp customerA = new ObserverImp("A");
        ObserverImp customerB = new ObserverImp("B");
        ObserverImp customerC = new ObserverImp("C");

        //将观察者注册到被观察者中
        magazine.registerObserver(customerA);
        magazine.registerObserver(customerB);
        magazine.registerObserver(customerC);

        //更新被观察者中的数据，当数据更新后，会自动通知所有已注册的观察者
        magazine.setInfomation(5, 12);
    }

}
