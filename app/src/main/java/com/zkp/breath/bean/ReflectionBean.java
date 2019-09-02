package com.zkp.breath.bean;

public class ReflectionBean {
    protected boolean b;
    private String s;
    public int i;

    public ReflectionBean() {
    }

    private ReflectionBean(boolean b, String s, int i) {
        this.b = b;
        this.s = s;
        this.i = i;
    }

    private void privateMethod() {
        System.out.println("调用privateMethod()");
    }

    private void publicMethod() {
        System.out.println("调用publicMethod()");
    }

    @Override
    public String toString() {
        return "ReflectionBean{" +
                "b=" + b +
                ", s='" + s + '\'' +
                ", i=" + i +
                '}';
    }
}
