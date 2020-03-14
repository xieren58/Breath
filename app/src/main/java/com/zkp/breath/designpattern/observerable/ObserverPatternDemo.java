package com.zkp.breath.designpattern.observerable;

/**
 * 难点：不要看名字。这里不能以中文名字的方式去理解，因为观察者反而是被动触发的，而被观察者是主动触发的。
 * 这里以广播发送者-广播接收者去理解就行，接收者注册在发送者里面，发送者有消息就会调起接收者，接收者也就相当于被动收到了信息。
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
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
