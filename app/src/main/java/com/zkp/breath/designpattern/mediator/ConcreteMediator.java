package com.zkp.breath.designpattern.mediator;

public class ConcreteMediator extends Mediator {

    // 需要知道所有具体类
    private ConcreteColleagueA concreteColleagueA;
    private ConcreteColleagueC concreteColleagueC;
    private ConcreteColleagueB concreteColleagueB;

    public void setConcreteColleagueA(ConcreteColleagueA concreteColleagueA) {
        this.concreteColleagueA = concreteColleagueA;
    }

    public void setConcreteColleagueC(ConcreteColleagueC concreteColleagueC) {
        this.concreteColleagueC = concreteColleagueC;
    }

    public void setConcreteColleagueB(ConcreteColleagueB concreteColleagueB) {
        this.concreteColleagueB = concreteColleagueB;
    }

    @Override
    public void notity(String msg, Colleague colleague) {
        if (colleague == concreteColleagueA) {
            concreteColleagueC.getNotice(msg);
        } else if (colleague == concreteColleagueB) {
            concreteColleagueC.getNotice(msg);
        } else if (colleague == concreteColleagueC) {
            concreteColleagueA.getNotice(msg);
            concreteColleagueB.getNotice(msg);
        }
    }

}
