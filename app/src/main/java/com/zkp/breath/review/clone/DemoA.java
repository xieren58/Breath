package com.zkp.breath.review.clone;

import org.jetbrains.annotations.NotNull;

/**
 * 浅克隆和深克隆的例子
 */
public class DemoA {

    static class A {
        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    static class B implements Cloneable {

        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @NotNull
        @Override
        public B clone() {
            B clo = null;
            try {
                clo = (B) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clo;
        }
    }

    public static void main(String[] args) {
        A a = new A();
        a.setNumber(2);
        A a1 = a;
        // 浅克隆，a和a1指向同一块内存地址
        System.out.println("a的内存地址：" + a);
        System.out.println("a1的内存地址：" + a1);

        System.out.println("=============================");
        System.out.println("=============================");

        B b = new B();
        B b1 = b.clone();
        // 深克隆，内存地址不一样
        System.out.println("b的内存地址：" + b);
        System.out.println("b1的内存地址：" + b1);
    }
}
