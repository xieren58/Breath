package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/23.
 */
public class ExceptionDemo {

    public static void main(String[] args) {
        try {
            x();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            x1();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ============ 运行时异常，可不被强制要求加try ============
        // ============ 运行时异常，可不被强制要求加try ============
        // 但是在处理异常的时候，不管类型，都try-catch才是友好的操作
        try {
            x2();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            x3();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ============ 运行时异常，可不被强制要求加try ============
        // ============ 运行时异常，可不被强制要求加try ============

        System.out.println();
        System.out.println();

        System.out.println(t1());
        System.out.println(t2());   // 返回值为finally的
        System.out.println(t3());   // 原异常被覆盖
    }

    // 被检查异常，一定要throws和调用处一定要try-catch
    private static void x() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    // 被检查异常基类Exception
    private static void x1() throws Exception {
        throw new Exception();
    }

    // 运行时异常，调用时可以不用try-catch
    private static void x2() throws NullPointerException {
        throw new NullPointerException();
    }

    //运行时异常，可以不用throws，调用时可以不用try-catch
    private static void x3() {
        throw new NullPointerException();
    }

    private static int t1() {
        int i = 0;
        try {
            return i;
        } finally {
            i = 2;
        }
    }

    private static int t2() {
        int i = 0;
        try {
            return i;
        } finally {
            // 合法但是不要在finally中使用return
            return 2;
        }
    }

    private static int t3() {
        try {
            int i = 5 / 0;
        } finally {
            // 合法但不要在finally使用抛异常的写法
            throw new RuntimeException("hello");
        }
    }


}
