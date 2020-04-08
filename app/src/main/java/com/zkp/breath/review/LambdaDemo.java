package com.zkp.breath.review;

/**
 * 匿名类的出现：该接口使用场景特定，且使用次数较少，所以不需要特地去实现。
 * 函数类型：只有一个抽象方法的接口被称为函数类型（函数式接口），以前创建函数对象的主要手段就是匿名类。
 * <p>
 * 1.只有一个抽象方法的接口才能使用lambda表达式
 * 2.lambda标准形式：（参数类型  参数名） -> {代码语句}
 * 3.小括号内的参数类型可以省略（一般在写的时候都省略，但是可读性不好，看自己习惯选择省略与否）
 * 4.小括号只有一个参数，那么小括号可以省略。
 * 5.如果花括号只有一个语句，那么花括号都可以省略，如果有返回值，那么该语句默认作为返回值
 */
public class LambdaDemo {

    @FunctionalInterface
    public interface Learn {
        void study();
    }

    @FunctionalInterface
    public interface Learn1 {
        void study(int one);
    }

    @FunctionalInterface
    public interface Learn2 {
        String study(int one);
    }


    public static void main(String[] args) {

        // 没有参数则直接写小括号
        Learn learn = () -> {
            System.out.println();
            System.out.println();
            System.out.println();
        };
        learn.study();

        // 方法内只有一句代码，那么可以省略花括号
        Learn learn1 = () -> System.out.println("");
        learn1.study();

        // 方法只有一个参数，那么可以省略小括号，直接写参数名
        Learn1 learn11 = one -> System.out.println("" + one);
        Learn1 learn12 = (int one) -> System.out.println("" + one);

        Learn2 learn2 = (int one) -> "" + one;
    }

}
