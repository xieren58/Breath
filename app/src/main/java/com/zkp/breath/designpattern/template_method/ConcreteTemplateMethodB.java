package com.zkp.breath.designpattern.template_method;

class ConcreteTemplateMethodB extends TemplateMethod {
    @Override
    protected void init() {
        System.out.println("ConcreteTemplateMethodB_init()");
    }

    @Override
    protected void start() {
        System.out.println("ConcreteTemplateMethodB_start()");
    }

    @Override
    protected void end() {
        System.out.println("ConcreteTemplateMethodB_end()");
    }
}
