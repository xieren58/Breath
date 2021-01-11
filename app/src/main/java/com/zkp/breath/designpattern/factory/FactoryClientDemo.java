package com.zkp.breath.designpattern.factory;

import com.zkp.breath.designpattern.factory.abstracts.AbstractFactory;
import com.zkp.breath.designpattern.factory.abstracts.AbstractFactoryContext;
import com.zkp.breath.designpattern.factory.abstracts.IDesignPatterns;
import com.zkp.breath.designpattern.factory.method.DratLanguageFactory;
import com.zkp.breath.designpattern.factory.method.ILanguageFactory;
import com.zkp.breath.designpattern.factory.method.JavaLanguageFactory;
import com.zkp.breath.designpattern.factory.method.KotlinLanguageFactory;
import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.LanguageFactory;

/**
 * 工厂设计的例子
 */
public class FactoryClientDemo {

    public static void main(String[] args) {

        simple();
    }

    /**
     * 简单的工厂模式：属于创建型模式，它提供了一种创建对象的最佳方式。
     */
    private static void simple() {
        ILanguage instance = LanguageFactory.getInstance(1);
//        ILanguage instance = LanguageFactory.getInstance(2);
//        ILanguage instance = LanguageFactory.getInstance(13);
        instance.function();
    }

    /**
     * 抽象工厂模式：围绕一个超级工厂创建其他工厂，该超级工厂又称为其他工厂的工厂。
     * 系统的产品有多于一个的产品族，而系统只消费其中某一族的产品。
     * <p>
     * 优点：便于交换产品系列。（比如有一个Circle工厂类，一个Rect工厂类，这是两个产品族，客户端可以根据需要选择
     * 其中的一个产品族进行操作）
     */
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

    /**
     * 工厂方法模式：定义一个用于创建对象的接口，让子类决定实例化哪个类，工厂方法使一个类的实例化延迟到其子类。
     * <p>
     * 优点：
     * 1.相比较工厂模式，更符合开闭原则，简单工厂模式需要修改工厂类的判断逻辑。
     * 2.简单工厂中的工厂类存在复杂的switch逻辑判断, 工厂方法把简单工厂的内部逻辑判断移到了客户端进行。
     * 3.说到底就是更符合接口编程，一旦实例化有不同的操作，我们可以在具体创建对象的实现类中实现自己特有的操作。
     */
    private static void methods() {
        ILanguageFactory kotlinLanguageFactory = new KotlinLanguageFactory();
//        ILanguageFactory javaLanguageFactory = new JavaLanguageFactory();
//        ILanguageFactory dratLanguageFactory = new DratLanguageFactory();

        ILanguage language = kotlinLanguageFactory.createLanguage();
        language.function();
    }
}
