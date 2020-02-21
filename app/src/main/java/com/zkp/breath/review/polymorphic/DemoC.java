package com.zkp.breath.review.polymorphic;

public class DemoC {

    public static class A {

        public String name = "father";

        public void printName() {
            // java没有成员变量重写的概念，所以这里是指向的是A的name
            System.out.println(this.name);
            System.out.println(this.getClass());// getClass()方法默认会被子类重写，所以这里的this指向的是B的实例
        }
    }

    public static class B extends A {
        // java没有成员变量重写的概念，所以B有两个成员变量，一个是super.name，一个是自身声明的name
        public String name = "son";
    }

    public static void main(String[] args) {
        A s = new B();
        // 多态。编译看左边，调用父类的方法，运行看右边，子类没有重写，所以还是调用父类的方法
        s.printName();
    }
}
