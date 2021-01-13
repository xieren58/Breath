package com.zkp.breath.designpattern.memento;

class Originator {

    private String state1;
    private String state2;
    //......

    /**
     * 保存所需的数据到Memento
     */
    public Memento saveState() {
        return new Memento(state1, state2);
    }

    /**
     * 恢复保存的数据
     */
    public void restoreState(Memento memento) {
        state1 = memento.getState1();
        state2 = memento.getState2();
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

    @Override
    public String toString() {
        return "Originator{" +
                "state1='" + state1 + '\'' +
                ", state2='" + state2 + '\'' +
                '}';
    }
}
