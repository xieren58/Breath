package com.zkp.breath.designpattern.template_method;

public abstract class TemplateMethod {
    protected abstract void init();

    protected abstract void start();

    protected abstract void end();

    // 为防止恶意操作，一般模板方法都加上 final 关键词。
    public final void function() {
        init();
        start();
        end();
    }

}
