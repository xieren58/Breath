package com.zkp.breath.designpattern.responsibility_chain;

public class ConcreteHandlerA extends Handler {
    @Override
    public void handleRequest(int request) {
        if (request > 0 && request < 10) {
            System.out.println("ConcreteHandlerA 的 handleRequest");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
