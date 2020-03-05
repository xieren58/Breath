package com.zkp.breath.designpattern.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created b Zwp on 2019/7/17.
 * 设计核心思想：实现同一个接口，用一个可扩展和可修改的类去包裹一个不改变不扩展的类。
 * 静态代理：在程序运行前就手动写好代理类。这样只能代理某一个类
 * 动态代理：通过反射原理，在程序运行的时候动态的生成代理对象，所以可以代理任意的类。
 */
public class ProxyDemo {

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
