package com.zkp.breath.designpattern.proxy;

/**
 * Created b Zwp on 2019/7/17.
 */
public class Client {

    public static void main(String[] args) {
//        ISubject iSubject = new RealISubject();

        // 代理，既有保护目标对象的作用，也有扩展的作用
        ISubject iSubject = new ProxyISubject(new RealISubject());
        iSubject.function();
    }
}
