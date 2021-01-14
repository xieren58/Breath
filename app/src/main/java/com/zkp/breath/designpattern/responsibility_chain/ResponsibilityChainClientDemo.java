package com.zkp.breath.designpattern.responsibility_chain;

/**
 * 责任链模式：使多个对象都有机会处理请求，将这些对象连成一条链，并沿着这条链传递请求，直到又一个对象处理请求为止。
 * <p>
 * 1. 接收者和发送者都没有对方的明确信息，且链中的对象自己也并不知道链的结构。
 * 2. 随时增加或修改一个请求的结构。
 * 3. 一个请求极有可能到了链的末端都得不到处理，或者因为没有正确配置得不到处理。
 */
public class ResponsibilityChainClientDemo {

    public static void main(String[] args) {
        Handler concreteHandlerA = new ConcreteHandlerA();
        Handler concreteHandlerB = new ConcreteHandlerB();
        Handler concreteHandlerC = new ConcreteHandlerC();

        concreteHandlerA.setSuccessor(concreteHandlerB);
        concreteHandlerB.setSuccessor(concreteHandlerC);

        concreteHandlerA.handleRequest(33);
    }

}
