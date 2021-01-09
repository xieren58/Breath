package com.zkp.breath.designpattern.strategy;

/**
 * https://www.cnblogs.com/me115/p/3790615.html
 * <p>
 * 策略模式
 * 1. 存在相同行为，只是具体实现不同。比如打折，有3折，5折等等
 * 2. 选用具体的实现由客户端承担，并转给Context对象，降低具体行为和客户端的耦合，因为具体行为的调用其实在Context。
 * 3. 避免使用多重条件判断，多if-else或者多分支的switch语句都会降低可读性，而可读性的提升也会降低复杂度，复杂
 * 度降低也会让维护变得容易。
 * <p>
 * 工厂和策略的区别：
 * 1.用途不一样
 * >工厂是创建型模式,它的作用就是创建对象；
 * >策略是行为型模式,它的作用是让一个对象在许多行为中选择一种行为;
 * 2.关注点不一样
 * >一个关注对象创建
 * >一个关注行为的封装
 */
public class StrategyClientDemo {

    public static void main(String[] args) {
        f1(1);
//        f1(2);
    }

    /**
     * 简单工厂模式+策略模式
     */
    private static void f1(int flag) {
        IAnimStrategy iAnimStrategy = null;

        // 简单工厂模式
        switch (flag) {
            case 1:
                iAnimStrategy = new AlphaStrategyImp();
                break;
            case 2:
                iAnimStrategy = new TranslationStrategyImp();
                break;
            default:
                break;
        }

        AnimStrategyContext animStrategyContext = new AnimStrategyContext(iAnimStrategy);
        animStrategyContext.anim();
    }
}
