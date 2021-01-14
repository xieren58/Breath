package com.zkp.breath.designpattern.mediator;

/**
 * 抽象同事类
 */
public abstract class Colleague {

    protected final Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

}
