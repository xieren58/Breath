package com.zkp.breath.designpattern.factory;

import com.zkp.breath.designpattern.factory.abstracts.AbstractFactory;
import com.zkp.breath.designpattern.factory.abstracts.AbstractFactoryContext;
import com.zkp.breath.designpattern.factory.abstracts.IDesignPatterns;
import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.LanguageFactory;

/**
 * 工厂设计的例子
 */
public class FactoryDemo {

    public static void main(String[] args) {

        simple();
    }

    // 简单的工厂模式
    private static void simple() {
        ILanguage instance = LanguageFactory.getInstance(1);
//        ILanguage instance = LanguageFactory.getInstance(2);
//        ILanguage instance = LanguageFactory.getInstance(13);
        instance.function();
    }

    // 抽象工厂模式
    private static void abstracts() {
        AbstractFactory languageAbstractFactory = AbstractFactoryContext.getFactory(1);
        assert languageAbstractFactory != null;
        ILanguage language = languageAbstractFactory.getLanguage(1);
//        language = languageAbstractFactory.getLanguage(2);
//        language = languageAbstractFactory.getLanguage(3);
        language.function();

        AbstractFactory designPatternsAbstractFactory = AbstractFactoryContext.getFactory(2);
        assert designPatternsAbstractFactory != null;
        IDesignPatterns designPattern = designPatternsAbstractFactory.getDesignPattern(1);
//        designPattern = designPatternsAbstractFactory.getDesignPattern(2);
//        designPattern = designPatternsAbstractFactory.getDesignPattern(3);
        designPattern.function();
    }
}
