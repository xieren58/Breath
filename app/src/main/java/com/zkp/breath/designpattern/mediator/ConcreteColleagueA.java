package com.zkp.breath.designpattern.mediator;

/**
 * 抽象同事类
 */
public class ConcreteColleagueA extends Colleague {

    public ConcreteColleagueA(Mediator mediator) {
        super(mediator);
    }

    public void sendMsg(String msg) {
        mediator.notity(msg, this);
    }

    public void getNotice(String msg) {
        System.out.println("ConcreteColleagueA接收到消息：" + msg);
    }

}
