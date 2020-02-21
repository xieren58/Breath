package com.zkp.breath.review.polymorphic;

public class DemoB {

    public static class DemoA {
        // 重载
        public boolean equals(DemoA other) {
            System.out.println("use Demo equals.");
            return true;
        }
    }

    public static void main(String[] args) {

        Object obj1 = new DemoA();   // 多态
        Object obj2 = new DemoA();   // 多态

        DemoA obj3 = new DemoA();
        DemoA obj4 = new DemoA();

        // 多态，编译看左边，检查到子类没有重写，所以最终调用了Object的equals方法（比较的是内存地址），所以判断条件为false
        if (obj1.equals(obj2)) {
            System.out.println("obj1和obj2");
        }

        // 非多态，选择最吻合的方法，子类重载的方法就能接收obj4，就不要去调用Object的equals方法，所以最终走了重载方法
        if (obj3.equals(obj4)) {
            System.out.println("obj1和obj2");
        }
    }
}
