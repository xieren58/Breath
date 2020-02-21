package com.zkp.breath.review.innerclass;

public class DemoA {

    private String string;

    private void f1() {

    }

    /**
     * 1.静态内部类，不依赖于外部类的存在
     * 2.无法使用其外部类的非 static 属性或方法（你就把静态内部类想象成一个‘静态方法’，一个类中‘静态方法’是没办法调用非静态属性或方法的）
     */
    public static class A {

    }

    /**
     * 成员内部类
     * 成员内部类可以访问外部类的方法和变量，因为成员内部类实例化的时外部类早已实例化，反之外部类要
     * 调用成员内类的方法需要先实例化内部类。
     *
     * Java 中为什么成员内部类可以直接访问外部类的成员？
     * 编译器会默认为成员内部类添加了一个指向外部类对象的引用并且在成员内部类构造方法中对其进行赋值操作（让引用正真指向外部类的对象）
     */
    public class B {

        private String string;

        private void f1() {
        }

        private void f2() {
            f1();
            String s1 = string;

            // 和外部类同名的成员或者方法的调用方式
            DemoA.this.f1();
            String s2 = DemoA.this.string;
        }
    }

    /**
     * 局部内部类，和成员内部类的区别在于生命周期是在方法内，就像一个局部变量一样，所以不能有访问权限限定符和static的修饰
     */
    private void f2() {
        class C {

            private String string;

            private void f1() {
            }

            private void f2() {
                f1();
                String s1 = string;

                // 和外部类同名的成员或者方法的调用方式
                DemoA.this.f1();
                String s2 = DemoA.this.string;
            }
        }
    }

    interface I1 {
        void f();
    }

    /**
     * 匿名（成员）内部类
     * 不能有构造函数，可以有构造代码块
     */
    private I1 i1 = new I1() {

        private String ss2;

        @Override
        public void f() {
            System.out.println(string);
            System.out.println(ss2);
        }
    };


}
