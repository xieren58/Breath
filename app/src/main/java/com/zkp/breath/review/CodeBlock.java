package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/22.
 * 静态代码块，构造代码块，构造函数，成员变量的执行顺序
 */
public class CodeBlock {

    public static void main(String[] args) {
        Children children = new Children();
    }

    private static class Parent {

        TempBean mTempBean = new TempBean();

        static {
            System.out.println("Parent_static_code_block");
        }

        {
            System.out.println("Parent_constructor_code_block");
        }

        public Parent() {
            System.out.println("Parent_constructor_method");
        }
    }

    private static class Children extends Parent{

        TempBean mTempBean = new TempBean();

        static {
            System.out.println("Children_static_code_block");
        }

        {
            System.out.println("Children_constructor_code_block");
        }

        Children() {
            System.out.println("Children_constructor_method");
        }
    }

    private static class TempBean {

        TempBean() {
            System.out.println("TempBean_constructor_method");
        }
    }
}
