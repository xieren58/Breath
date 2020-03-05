package com.zkp.breath.designpattern.factory;

/**
 * 工厂设计的例子
 */
public class FactoryDemo {

    public static void main(String[] args) {

        ILanguage instance = LanguageFactory.getInstance(1);
//        ILanguage instance = LanguageFactory.getInstance(2);
//        ILanguage instance = LanguageFactory.getInstance(13);
        instance.function();
    }
}
