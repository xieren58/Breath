package com.zkp.breath.designpattern.factory.abstracts;

public class AbstractFactoryContext {
    public static AbstractFactory getFactory(int flag) {
        if (flag == 1) {
            return new LanguageFactory();
        } else if (flag == 2) {
            return new DesignPatternsFactory();
        }
        return null;
    }
}
