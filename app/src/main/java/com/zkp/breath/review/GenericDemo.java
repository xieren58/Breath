package com.zkp.breath.review;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/***
 * 泛型的例子
 *
 * Java 的泛型本身是不支持协变和逆变的，PECS 法则：「Producer-Extends, Consumer-Super」
 * 生产者：? extends，使泛型支持协变（只能读取不能修改）
 * 消费者：? super，使泛型支持逆变（只能修改不能读取）
 */
public class GenericDemo {

    public static void main(String[] args) {

        List<Integer> integers1 = new ArrayList<>();
        integers1.add(1);
        List<Number> integers2 = new ArrayList<>();
        integers2.add(2);
        // 1.上限：? extends Number，作为接收参数可以指泛型类型是Number或者Number的子类的实例对象
        // 2.只能用于获取，获取的值的类型用Number指向，因为内部的元素都是Number的子类或者就是Number。
        producerExtends(integers1);
        producerExtends(integers2);


        // 1.下限:? super Integer，实例泛型类型可以是Integer以及其超类。
        // 2.添加的类型只能是层级关系中最低的引用类型，即Integer，因为实例的泛型类型是Number,Object,Integer
        //  所以因为number = integer（父类变量指向子类对象是允许的）;object = integer（）（父类变量指向子类对象是允许的）;integer = integer都是成立的。
        //  但是如果我们添加的类型是Number或者Object，那么Integer = Object/Number是不成立的。
        // 3.如果获取的话只能用Object类型去接收，因为其实例泛型的类型可能存在许多父类型，所以只能用最顶层类型去接收

        // new ArrayList<Number>()的number类型是Integer是超类
        List<? super Integer> numbers1 = new ArrayList<Number>();
        List<? super Integer> numbers2 = new ArrayList<Integer>();
        // new ArrayList<Number>()的number类型是Integer是超类
        List<? super Integer> numbers3 = new ArrayList<Object>();
        consumerSuper(numbers1);
        consumerSuper(numbers2);

        // 避免这种写法！！！！！！！
        // 和list1，list2是同个作用
        ArrayList list = new ArrayList<String>();
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList list2 = new ArrayList();
        list.add(1);
        list.add(new Object());
        try {
            Object o = list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();


        /**
         * 由于在程序中定义的 ArrayList 泛型类型实例化为 Integer 的对象，
         * 如果直接调用 add 方法则只能存储整形数据，不过当我们利用反射调用 add 方法时就可以存储字符串，
         * 因为 Integer 泛型实例在编译之后被擦除了，只保留了原始类型 Object，所以自然可以插入。
         */
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        Class<? extends ArrayList> aClass = arrayList.getClass();
        try {
            Method method = aClass.getMethod("add", Object.class);
            method.invoke(arrayList, "abc");
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println("集合:" + arrayList.get(i));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

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
//            list.add(new Number(1))   // 是不允许的
//            list.add(new Object())    // 是不允许的
            Object object = list.get(0);
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Bean<T> {

        // 编译后泛型会被擦除，替换为非泛型上边界，如果没有指定边界则为 Object 类型，如不想被擦除为 Object 类型时不要忘了添加上边界操作等
        // 相当于：private Object t;
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
