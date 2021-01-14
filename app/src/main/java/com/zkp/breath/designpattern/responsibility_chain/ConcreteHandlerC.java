package com.zkp.breath.designpattern.responsibility_chain;

public class ConcreteHandlerC extends Handler {
    @Override
    public void handleRequest(int request) {
        if (request >= 20 && request < 30) {
            System.out.println("ConcreteHandlerC 的 handleRequest");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
