package com.zkp.breath.review.polymorphic;

import com.zkp.breath.kotlin.D;
import com.zkp.breath.kotlin.Demo;

public class DemoA {

    public static class A {

        public String run(D obj) {
            return ("A & D");
        }

        public String run(A obj) {
            return ("A & A");
        }

    }

    public static class B extends A {

        public String run(B obj) {
            return ("B & B");
        }

        @Override
        public String run(A obj) {
            return ("B & A");
        }
    }

    public static class C extends B {

    }

    public static class D extends B {

    }

    public static void main(String[] args) {
        A aa = new A();
        A ab = new B();
        B b = new B();
        C c = new C();
        D d = new D();

        // 1，2，3;不存在父类引用变量指向子类对象（不满足实现多态的条件），根据传递的参数选择与之最吻合（就像鞋码，你穿41，有41的就选41，没有41的才去选42，43。。。。）的方法
        System.out.println(aa.run(b));  // 1.A & A
        System.out.println(aa.run(c));  // 2.A & A
        System.out.println(aa.run(d));  // 3.A & D

        // 4：多态，编译的时候看左边，即A类有参数AD两个方法，传入的参数B（继承A），所以选择A类的参数A的方法，运行的时候看右边，即B类有重写A类的参数A方法，所以最终调用的是B类的A方法
        // 5：多态，编译的时候看左边，即A类有参数AD两个方法，传入的参数是C（继承了B，B继承了A），所以选择A类的参数A的方法，运行的时候看右边，即B类有重写A类的参数A方法，所以最终调用的是B类的A方法
        // 6：多态，编译的时候看左边，即A类有参数AD两个方法，传入的参数是D（继承了B，B继承了A），但是有与之最吻合的D方法，所以选择了D方法而非A，运行的时候看右边，这时候B类没有重写该方法，所以最终调用了A类的D方法
        System.out.println(ab.run(b));  // 4.B & A
        System.out.println(ab.run(c));  // 5.B & A
        System.out.println(ab.run(d));  // 6.A & D

        // 7，8，9；不存在父类引用变量指向子类对象（不满足实现多态的条件），因为继承A类，所以本身存在了重写的A方法和继承过来的D方法（都是A类的方法），还有自身的B方法。所以根据最吻合的选择，传入B就调用了B方法，传入C（直接父类为B）也是调用了B方法，传入D就选择D方法。
        System.out.println(b.run(b));   // 7.B & B
        System.out.println(b.run(c));   // 8.B & B
        System.out.println(b.run(d));   // 9.A & D

    }

}
