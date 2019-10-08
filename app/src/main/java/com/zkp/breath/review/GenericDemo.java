package com.zkp.breath.review;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {

    public static void main(String[] args) {
        List<? extends Number> integers1 = new ArrayList<Integer>();
        List<? extends Number> integers2 = new ArrayList<Number>();
        producerExtends(integers1);
        producerExtends(integers2);

        List<? super Integer> numbers1 = new ArrayList<Number>();
        List<? super Integer> numbers2 = new ArrayList<Integer>();
        consumerSuper(numbers1);
        consumerSuper(numbers2);

        System.out.println();
        System.out.println();


    }

    /**
     * 上限 - 协变 -  生产者
     * 但是「只能读取不能修改」，这里的修改仅指对泛型集合添加元素，如果是 remove(int index) 以及 clear 当然是可以的。
     *
     * @param list
     */
    private static void producerExtends(List<? extends Number> list) {
        try {
            Number number = list.get(0);
            System.out.println(number.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param list 泛型声明相当于List< ? extends Object>
     */
    private static void producerExtends2(List<?> list) {
        try {
            Object o = list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下限 - 逆变 -  消费者
     * 但是「只能修改不能读取」，这里说的不能读取是指不能按照泛型类型读取，你如果按照 Object 读出来再强转当然也是可以的。
     *
     * @param list
     */
    private static void consumerSuper(List<? super Integer> list) {
        try {
            list.add(1);
            Object object = list.get(0);
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Bean<T> {
        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        // 泛型静态方法不能使用类的泛型类型，原因和static比类先加载。
        public static <U> U f(U u) {
            return u;
        }
    }

    /**
     * 元组其实是关系数据库中的一个学术名词，一条记录就是一个元组，一个表就是一个关系，纪录组成表，
     * 元组生成关系，这就是关系数据库的核心理念。java借助泛型实现元组
     *
     * @param <A>
     * @param <B>
     * @param <C>
     */
    private static class Triplet<A, B, C> {
        private A a;
        private B b;
        private C c;

        public Triplet(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

}
