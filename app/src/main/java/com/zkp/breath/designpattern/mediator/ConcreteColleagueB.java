package com.zkp.breath.designpattern.mediator;

/**
 * 抽象同事类
 */
public class ConcreteColleagueB extends Colleague {

    public ConcreteColleagueB(Mediator mediator) {
        super(mediator);
    }

    public void sendMsg(String msg) {
        mediator.notity(msg, this);
    }

    public void getNotice(String msg) {
        System.out.println("ConcreteColleagueB接收到消息：" + msg);
    }

}
