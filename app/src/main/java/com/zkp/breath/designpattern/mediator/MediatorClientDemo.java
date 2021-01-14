package com.zkp.breath.designpattern.mediator;

/**
 * 中介者模式：用一个中介者对象来封装一系列的对象交互。中介者使得各对象不需要显式地相互引用，从而使其松散耦合，
 * 而且可以独立地改变它们之间的交互。
 */
public class MediatorClientDemo {

    public static void main(String[] args) {
        ConcreteMediator concreteMediator = new ConcreteMediator();

        ConcreteColleagueA concreteColleagueA = new ConcreteColleagueA(concreteMediator);
        ConcreteColleagueB concreteColleagueB = new ConcreteColleagueB(concreteMediator);
        ConcreteColleagueC concreteColleagueC = new ConcreteColleagueC(concreteMediator);

        concreteMediator.setConcreteColleagueA(concreteColleagueA);
        concreteMediator.setConcreteColleagueB(concreteColleagueB);
        concreteMediator.setConcreteColleagueC(concreteColleagueC);

        concreteColleagueA.sendMsg("concreteColleagueA发送消息");
        concreteColleagueB.sendMsg("concreteColleagueB发送消息");
        concreteColleagueC.sendMsg("concreteColleagueC发送消息");
    }

}
