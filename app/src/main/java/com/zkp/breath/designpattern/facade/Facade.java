package com.zkp.breath.designpattern.facade;

public class Facade {

    private final SubSystemA subSystemA;
    private final SubSystemB subSystemB;
    private final SubSystemC subSystemC;

    public Facade() {
        subSystemA = new SubSystemA();
        subSystemB = new SubSystemB();
        subSystemC = new SubSystemC();
    }

    public void methodA() {
        subSystemA.methodA();
    }

    public void methodB() {
        subSystemB.methodB();
    }

    public void methodC() {
        subSystemC.methodC();
    }

}
