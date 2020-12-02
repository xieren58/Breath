package com.zkp.breath.review;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/***
 * 泛型的例子
 *
 * Java 的泛型本身是不支持协变和逆变的（协变和逆变使泛型实现多态），PECS 法则：「Producer-Extends, Consumer-Super」
 *
 * 生产者：? extends，上界通配符，使泛型支持协变（只能读取不能修改，这里的修改仅指对泛型集合添加元素，如果是
 * remove(int index) 以及 clear 当然是可以的）。其中 ? 是个通配符，表示泛型类型是一个未知类型，extends 限制了
 * 这个未知类型的上界，即为某个类型的间接或直接子类/实现类，也包括指定的上界类型。
 *
 * 上界通配符的获取和添加概括：
 * 只能用于获取，因为指定了上界的原因，实际类型要么是上界类型要么是上界的子类类型，符合多态的特性；不能用于添加，因为
 * 实际类型可能是上界类型的子类，然后添加元素的类型是上界类型，这种行为是不允许的。
 *
 * 消费者：? super，下界通配符，使泛型支持逆变（只能修改不能读取，这里说的不能读取是指不能按照泛型类型读取，你如果按照
 * Object 读出来再强转当然也是可以的）。其中 ? 是个通配符，表示泛型类型是一个未知类型，super 限制了这个未知类型的下界，
 * 即为某个类型的间接或者直接父类/接口，也包括指定的下界类型。
 *
 * 下界通配符的获取和添加概括：
 * 可以添加，因为添加元素的类型是下界类型，而对象的真实类型一定是下界类型或者下界的父类型，满足多态特性；也能用于获取
 * ，但是获取出来的类型是Object类型。
 *
 * ？通配符：这样使用 List<?> 其实是 List<? extends Object> 的缩写。
 *
 * Java 中声明类,接口,方法的时候，可以使用 extends（没有super，注意这个是声明类或接口的时候用的） 来设置边界，将
 * 泛型类型参数限制为某个类型的子集。
 */
public class GenericDemo {

    public static void main(String[] args) {

        List<? extends TextView> textViews1 = new ArrayList<TextView>(); // 👈 本身
        List<? extends TextView> textViews2 = new ArrayList<Button>(); // 👈 直接子类
        List<? extends TextView> textViews3 = new ArrayList<RadioButton>(); // 👈 间接子类

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

        // 避免下面这三种写法！！！！！！！
        // list,list1，list2的泛型类型都为Object。
        // 正确的写法为左边变量指定，右边对象省略或者也指定。 List<String> list = new ArrayList<String>(); List<String> list = new ArrayList()
        ArrayList list = new ArrayList<String>();
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList list2 = new ArrayList();
        list.add(1);
        list.add(new Object());
        list2.add("");
        try {
            Object o = list.get(0);
            Object o1 = list1.get(0);
            Object o2 = list2.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();

        // ===================================================
        // ===================================================

        // 下面的写法都满足类定义的泛型的要求
        Bean2<Integer> bean2 = new Bean2<>();
        bean2.setT(2);
        Integer t2 = bean2.get3();
        Number number2 = bean2.getT();
        Serializable serializable2 = bean2.getT2();

        Bean2<Number> bean3 = new Bean2<>();
        Number t3 = bean3.get3();
        Number number3 = bean3.getT();
        Serializable serializable3 = bean3.getT2();

        // 上界通配符的泛型限制和类定义的泛型限定是一致的，所以下面的声明是允许的。
        Bean2<? extends Number> bean4 = new Bean2<Integer>();
//        bean4.setT();     // 上面的声明类型是斜边，不能添加元素。  实例化声明类型 和 定义类的泛型类型 是不一样的，要谨记
        Number number4 = bean4.get3();
        Serializable serializable4 = bean4.getT2();

        // 为什么get3（）方法获取到到不是Object而是Number？
        // 因为List类并没有限制泛型范围，所以只能用顶层父类Object指向，而自定义类Bean2中的泛型限制了泛型范围（<T extends Number & Serializable>），所以优先拿该类型指向。
        Bean2<? super Integer> bean5 = new Bean2<Number>();
        bean5.setT(5);
        Number number5 = bean5.get3();
        Number t5 = bean5.getT();
        Serializable serializable5 = bean5.getT2();


        // ===================================================
        // ===================================================
        Bean3<String> objectBean3 = new Bean3<>();
        String t = objectBean3.getT();

        Bean3<? extends Number> objectBean4 = new Bean3<>();
        Number t1 = objectBean4.getT();

        Bean3<? super Integer> objectBean5 = new Bean3<>();
        Object object5 = objectBean5.getT();


        /**
         * 泛型擦除
         *
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


        // 判断某个对象的泛型类型是否为指定类型
        List<String> stringList = new ArrayList<>();
        Object o = stringList;
        Class<? extends List> aClass1 = stringList.getClass();
        check(o, aClass1);
    }

    // 由于 Java 中的泛型存在类型擦除的情况，任何在运行时需要知道泛型确切类型信息的操作都没法用了
//    private static <T> void printIfTypeMatch(Object item) {
//        if (item instanceof T) { // 👈 IDE 会提示错误，illegal generic type for instanceof
//            System.out.println(item);
//        }
//    }

    private static <T> void check(Object item, Class<T> type) {
        // item是否为type到实例对象
        if (type.isInstance(item)) {
            System.out.println("泛型类型检查成功");
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
            list.add(1);    // 只能添加下界类型
//            list.add(new Number(1))   // 是不允许的
//            list.add(new Object())    // 是不允许的
            Object object = list.get(0);
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 限制泛型范围。
     * 指定泛型类型为必须为Number和Serializable的子类型，或者为Number，为Serializable类型。
     */
    private static class Bean2<T extends Number & Serializable> {
        private T t;

        public void setT(T t) {
            this.t = t;
        }

        public Number getT() {
            return t;
        }

        public Serializable getT2() {
            return t;
        }

        public T get3() {
            return t;
        }
    }

    private static class Bean3<T> {
        private T t;

        public T getT() {
            return t;
        }
    }


    private static <T extends Number> Number getT1(T t) {
        return t;
    }

    private static <T extends Number> T getT2(T t) {
        return t;
    }

    private static <T extends Number> T getT3(T t) {
        Object o = new Object();
        return (T) o;
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
     * 元组生成关系，这就是关系数据库的核心理念。
     * java借助泛型实现元组。
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
