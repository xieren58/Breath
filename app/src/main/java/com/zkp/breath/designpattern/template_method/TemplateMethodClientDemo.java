package com.zkp.breath.designpattern.template_method;

/**
 * 模板方法模式：定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构
 * 即可重定义该算法的某些特定步骤。这种类型的设计模式属于行为型模式。
 * <p>
 * 1. 封装不变部分，开放可变部分。
 * 2. 调用顺序由父类控制，子类实现。
 * 3. 提供了一个很好的代码复用平台。
 */
public class TemplateMethodClientDemo {

    public static void main(String[] args) {
        TemplateMethod concreteTemplateMethodA = new ConcreteTemplateMethodA();
        concreteTemplateMethodA.function();
    }

}
