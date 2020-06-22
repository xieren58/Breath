package com.zkp.breath.review;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class JavaAnnotationTestDemo {

    // =============================================================================
    // =============================================================================
    // =============================================================================

    // 使用Inherited这个注解的类的子类也同样拥有这个注解的效果；如果不添这个Inherited注解，那么使用Inheritable注解
    // 的类的子类不拥有Inheritable注解的效果。
    @Inherited
    public @interface Inheritable {

    }

    @Inheritable
    class InheritableFather {
        public InheritableFather() {
            // InheritableBase是否具有 Inheritable Annotation
            // Class类的isAnnotationPresent()方法，某个类是否有某个注解。
            System.out.println("InheritableFather:" + InheritableFather.class.isAnnotationPresent(Inheritable.class));
        }
    }

    public class InheritableSon extends InheritableFather {
        public InheritableSon() {
            // 调用父类的构造函数
            super();
            // InheritableSon类是否具有 Inheritable Annotation
            System.out.println("InheritableSon:" + InheritableSon.class.isAnnotationPresent(Inheritable.class));
        }
    }

    // =============================================================================

    @Deprecated
    private static void deprecatedTest() {

    }

    // =============================================================================

    /**
     * Date的构造方法是过时api，添加SuppressWarnings注解后调用不会发出过时警告。
     */
    @SuppressWarnings(value = {"deprecation"})
    private static void suppressWarningsTest() {
        Date date = new Date(113, 8, 26);
    }

    // =============================================================================

    @interface MyAnnotation {
        String[] value() default "unknown";
    }


    static class Person {

        /**
         * empty()方法同时被 "@Deprecated" 和 "@MyAnnotation()"所标注
         * (01) @Deprecated，意味着empty()方法，不再被建议使用
         * (02) @MyAnnotation, 意味着empty() 方法对应的MyAnnotation的value值是默认值"unknown"
         */
        @MyAnnotation
        @Deprecated
        public void empty() {
            System.out.println("\nempty");
        }

        /**
         * sombody() 被 @MyAnnotation(value={"girl","boy"}) 所标注，
         *
         * @MyAnnotation(value={"girl","boy"}), 意味着MyAnnotation的value值是{"girl","boy"}
         */
        @MyAnnotation(value = {"girl", "boy"})
        public void somebody(String name, int age) {
            System.out.println("\nsomebody: " + name + ", " + age);
        }
    }

    private static void reflectionAnnotationTest() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        // 新建Person
        Person person = new Person();
        // 获取Person的Class实例
        Class<Person> c = Person.class;
        // 获取 somebody() 方法的Method实例
        Method mSomebody = c.getMethod("somebody", new Class[]{String.class, int.class});
        // 执行该方法
        mSomebody.invoke(person, new Object[]{"lily", 18});
        iteratorAnnotations(mSomebody);

        // 获取 somebody() 方法的Method实例
        Method mEmpty = c.getMethod("empty", new Class[]{});
        // 执行该方法
        mEmpty.invoke(person, new Object[]{});
        iteratorAnnotations(mEmpty);
    }

    public static void iteratorAnnotations(Method method) {

        // 判断 somebody() 方法是否包含MyAnnotation注解
        if (method.isAnnotationPresent(MyAnnotation.class)) {
            // 获取该方法的MyAnnotation注解实例
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = myAnnotation.value();
            for (String str : values)
                System.out.printf(str + ", ");
            System.out.println();
        }

        // 获取方法上的所有注解，并打印出来
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

    // =============================================================================

    @SafeVarargs
    private static <T> void safeVarargsTest(T... args) {

    }

    // =============================================================================

    /**
     * 在学习 Lambda 表达式时，我们提到如果接口中只有一个抽象方法，那么该接口就是函数式接口。@FunctionalInterface
     * 就是用来指定某个接口必须是函数式接口，所以 @FunInterface 只能修饰接口。
     */
    @FunctionalInterface
    interface FunctionInterfaceTest {
        void test();
    }

    // =============================================================================

//    @Repeatable()
//    public @interface Persons {
//
//    }


    // =============================================================================
    // =============================================================================
    // =============================================================================

    public static void main(String[] args) {
        // 如果有开发人员试图使用或重写被 @Deprecated 标示的方法，编译器会给相应的提示信息
        deprecatedTest();
        suppressWarningsTest();
        safeVarargsTest();
        try {
            reflectionAnnotationTest();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
