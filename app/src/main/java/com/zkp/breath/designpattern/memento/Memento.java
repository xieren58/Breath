package com.zkp.breath.designpattern.memento;

/**
 * 备忘录
 */
public class Memento {

    private String state1;
    private String state2;
    //......

    public Memento(String state1, String state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }
}
