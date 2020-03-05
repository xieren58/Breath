package com.zkp.breath.designpattern.strategy;

/**
 * 策略模式
 * 和代理模式的区别是控制类是没有实现接口的
 */
public class StategyDemo {

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
