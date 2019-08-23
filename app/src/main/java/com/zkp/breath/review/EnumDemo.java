package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/23.
 */
public class EnumDemo {

    public static void main(String[] args) {

        switchMethod(Bean.B1);

    }

    private static void switchMethod(Bean bean) {
        int ordinal = bean.ordinal();
        String name = bean.name();
        System.out.println("ordinal: " + ordinal + ",name: " + name);

        // 其实内部是调用了bean.ordinal()，实际还是int类型
        switch (bean) {
            case B1:  // B1.ordinal()
                System.out.println("B1");
                break;
            case B2:    // B2.ordinal()
                System.out.println("B2");
                break;
            case B3:    // B3.ordinal()
                System.out.println("B3");
                break;
            default:
                break;
        }
    }

    private enum Bean {
        /**
         * b1
         */
        B1("b1"),
        /**
         * b2
         */
        B2("b2"),
        /**
         * b3
         */
        B3("b3");

        Bean(String name) {
            this.name = name;
        }

        private String name;
    }
}
