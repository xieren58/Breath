package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/23. 多态（1.继承 2.重写 3.父类引用指向子类对象）
 */
public class FaceObj {

    public static void main(String[] args) {

        A aa = new A();
        A ab = new B();
        B b = new B();
        C c = new C();
        D d = new D();

        System.out.println(aa.run(b));  // "A & A"
        System.out.println(aa.run(c));  // "A & A"
        System.out.println(aa.run(d));  // "A & D"
        System.out.println();

        // B继承A，编译的时候看左边，调用A#run(A obj),运行的时候再看右边，B重写了该方法，所以调用B的重写方法
        System.out.println(ab.run(b));  // "B & A"
        // C继承B，B又继承A，编译的时候看左边，调用A#run(A obj),运行的时候再看右边，B重写了该方法，所以调用B的重写方法
        System.out.println(ab.run(c));  // "B & B"
        // D继承B，B又继承A，编译的时候看左边，调用A#run(D obj),运行的时候再看右边，B没有重写该方法，所以还是调用父类的方法
        System.out.println(ab.run(d));  // "A & D"
        System.out.println();

        System.out.println(b.run(b));   // "B & B"
        System.out.println(b.run(c));   // "B & B"
        System.out.println(b.run(d));   // "A & D"
        System.out.println();

        Object obj1 = new Demo();   // 多态
        Object obj2 = new Demo();   // 多态

        Demo obj3 = new Demo();
        Demo obj4 = new Demo();

        // 多态，编译看左边，检查到子类没有重写，所以最终调用了Object的equals方法（比较的是内存地址），所以判断条件为false
        if (obj1.equals(obj2)) {
            System.out.println("obj1和obj2");
        }

        // 非多态，选择最吻合的方法，子类重载的方法就能接收obj4，就不要去调用Object的equals方法，所以最终走了重载方法
        if (obj3.equals(obj4)) {
            System.out.println("obj1和obj2");
        }
    }

    private static class A {

        public String run(D obj) {
            return ("A & D");
        }

        // 重载
        public String run(A obj) {
            return ("A & A");
        }
    }

    private static class B extends A {

        // 重载
        public String run(B obj) {
            return ("B & B");
        }

        @Override  //有些面试题目这个重写注解没给出
        public String run(A obj) {
            return ("B & A");
        }
    }

    private static class C extends B {

    }

    private static class D extends B {

    }

    private static class Demo {
        // 分析：下面的类是重载而不是重写，因为违反了参数规则，重写是方法体不一致，其余都要相同，而下面的方法参数修改过了，所以是重载
        public boolean equals(Demo other) {
            System.out.println("use Demo equals.");
            return true;
        }
    }
}
