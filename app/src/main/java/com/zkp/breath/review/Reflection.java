package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/23.
 */
public class Reflection {

    public static void main(String[] args) {
        Bean bean = new Bean();
        Class<? extends Bean> aClass1 = bean.getClass();
        Class<Bean> beanClass = Bean.class;
    }

    private static class Bean {

        private String s;
        public int i;

        private void privateMethod() {

        }

        private void publicMethod() {

        }
    }
}
