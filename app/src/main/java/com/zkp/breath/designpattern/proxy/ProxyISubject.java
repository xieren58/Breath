package com.zkp.breath.designpattern.proxy;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class ProxyISubject implements ISubject {

    private ISubject mISubject;

    /**
     * 代理类内部主动创建被代理类
     */
    public ProxyISubject() {
        mISubject = new RealSubject();
    }

    @Override
    public void function() {
        System.out.println("ProxyISubject_function: 扩展代码1");
        mISubject.function();
        System.out.println("ProxyISubject_function: 扩展代码2");
    }
}
