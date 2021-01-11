package com.zkp.breath.review.clone;

/**
 * 继承下的clone
 */
public class DemoC {

    public static class A implements Cloneable {

        @Override
        public A clone() {
            A clo = null;
            try {
                clo = (A) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clo;
        }

    }

    public static class B {
        private A a;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

    }

    public static class C extends B implements Cloneable {
        @Override
        public C clone() {
            C clo = null;
            try {
                clo = (C) super.clone();
                // 实现深度拷贝
                clo.setA(super.a.clone());
//                clo.setA(super.getA().clone());   // 和上面的方法一样
//                clo.setA(getA().clone());     // 和上面的方法一样
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clo;
        }
    }

    public static void main(String[] args) {
        A a = new A();

        C c = new C();
        c.setA(a);
        C c1 = c.clone();

        System.out.println("c的内存地址:" + c + ",c的a变量的你内存地址：" + c.getA());
        System.out.println("c1的内存地址:" + c1 + ",c1的a变量的你内存地址：" + c1.getA());
    }

}
