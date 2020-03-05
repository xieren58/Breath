package com.zkp.breath.designpattern.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created b Zwp on 2019/7/17.
 */
public class Client {

    public static void main(String[] args) {
//        staticProxy();
        dynamicProxy();
    }

    // 静态代理
    private static void staticProxy() {
        // 代理，既有保护目标对象的作用(保护被代理者，如果要修改可以只改变代理者)，也有扩展的作用（扩展也只扩展代理者）
        ISubject iSubject = new ProxyISubject(new RealISubject());
        iSubject.function();
    }

    private static void dynamicProxy() {
        // 代理对象
        // 参数一：被代理对象的类的加载器
        // 参数二：被代理对象的接口
        // 返回动态代理对象
        ISubject o = (ISubject) Proxy.newProxyInstance(RealISubject.class.getClassLoader(), new Class[]{ISubject.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("我是代理对象");
                return method.invoke(proxy, args);
            }
        });
        // 执行代理对象的方法
        o.function();

    }
}
