package com.zkp.breath.designpattern.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created b Zwp on 2019/7/17.
 * 代理模式：为其他对象提供一种代理以控制对这个对象的访问，其实就是避免直接访问真实对象，代理就是真实对象的代表。
 * 如果直接访问可能会给使用者或者被访问者带来许多麻烦，那么就应该使用此模式。
 */
public class ProxyClientDemo {

    public static void main(String[] args) {
//        staticProxy();
        dynamicProxy();
    }

    // 静态代理
    private static void staticProxy() {
        // 代理，既有保护目标对象的作用(保护被代理者，如果要修改可以只改变代理者)，也有扩展的作用（扩展也只扩展代理者）
        ISubject iSubject = new ProxyISubject();
        iSubject.function();
    }

    // 动态代理：通过反射原理，在程序运行的时候动态的生成代理对象，所以可以代理任意的类。
    public static void dynamicProxy() {
        ProxyHandler proxy = new ProxyHandler();
        //绑定该类实现的所有接口
        ISubject sub = (ISubject) proxy.bind(new RealSubject());
        sub.function();
    }

    public static class ProxyHandler implements InvocationHandler {
        private Object tar;

        //绑定委托对象，并返回代理类
        public Object bind(Object tar) {
            this.tar = tar;
            //绑定该类实现的所有接口，取得代理类
            // 参数一：被代理对象的类的加载器（固定写法，不需要关注这个参数）
            // 参数二：被代理对象的所有接口
            // 参数三：InvocationHandler
            return Proxy.newProxyInstance(tar.getClass().getClassLoader(),
                    tar.getClass().getInterfaces(),
                    this);
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            //这里就可以进行所谓的AOP编程了
            //在调用具体函数方法前，执行功能处理
            result = method.invoke(tar, args);
            //在调用具体函数方法后，执行功能处理
            return result;
        }
    }

}
