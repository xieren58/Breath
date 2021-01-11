package com.zkp.breath.designpattern.template_method;

class ConcreteTemplateMethodA extends TemplateMethod {
    @Override
    protected void init() {
        System.out.println("ConcreteTemplateMethodA_init()");
    }

    @Override
    protected void start() {
        System.out.println("ConcreteTemplateMethodA_start()");
    }

    @Override
    protected void end() {
        System.out.println("ConcreteTemplateMethodA_end()");
    }
}
