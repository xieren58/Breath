package com.zkp.breath.designpattern.responsibility_chain;

public class ConcreteHandlerB extends Handler {
    @Override
    public void handleRequest(int request) {
        if (request >= 10 && request < 20) {
            System.out.println("ConcreteHandlerB 的 handleRequest");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
