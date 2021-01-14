package com.zkp.breath.designpattern.mediator;

/**
 * 抽象同事类
 */
public class ConcreteColleagueC extends Colleague {

    public ConcreteColleagueC(Mediator mediator) {
        super(mediator);
    }

    public void sendMsg(String msg) {
        mediator.notity(msg, this);
    }

    public void getNotice(String msg) {
        System.out.println("ConcreteColleagueC接收到消息：" + msg);
    }

}
