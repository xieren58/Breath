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

        x2();
        x3();
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
}
