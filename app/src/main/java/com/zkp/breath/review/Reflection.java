package com.zkp.breath.review;


import com.zkp.breath.bean.ReflectionBean;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created b Zwp on 2019/8/23.
 * 反射例子
 */
public class Reflection {

    public static void main(String[] args) {
        // =====================获取class对象=====================
        // =====================获取class对象=====================
        Class<ReflectionBean> beanClass = getClazz();
        System.out.println();
        System.out.println();

        // =====================获取构造方法=====================
        // =====================获取构造方法=====================
        getConstructor(beanClass);
        System.out.println();
        System.out.println();

        // =====================获取字段=====================
        // =====================获取字段=====================
        getField(beanClass);
        System.out.println();
        System.out.println();

        // =====================获取方法=====================
        // =====================获取方法=====================
        getMethod(beanClass);
    }

    private static void getMethod(Class<ReflectionBean> beanClass) {
        // 获取所有"公有方法"；（包含了父类的方法也包含Object类）
        Method[] methods = beanClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            System.out.println("获取所有公共方法_i: " + i + ",name: " + method.toString());
        }
        System.out.println();
        System.out.println();
        // 获取所有的成员方法，包括私有的(不包括继承的)
        Method[] declaredMethods = beanClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method method = declaredMethods[i];
            System.out.println("获取所有方法_i: " + i + ",name: " + method.toString());
        }
        System.out.println();
        System.out.println();
        try {
            Method privateMethod = beanClass.getDeclaredMethod("privateMethod");
            System.out.println("获取指定的方法:" + privateMethod.toGenericString());
            ReflectionBean reflectionBean = beanClass.newInstance();
            privateMethod.setAccessible(true);
            privateMethod.invoke(reflectionBean);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private static void getField(Class<ReflectionBean> beanClass) {
        // 获取所有公共字段
        Field[] fields = beanClass.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            System.out.println("获取所有公共字段_i: " + i + ",name: " + field.toString());
        }
        System.out.println();
        System.out.println();
        // 获取所有字段
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            System.out.println("获取所有字段_i: " + i + ",name: " + field.toString());
        }
        System.out.println();
        System.out.println();

        try {
            Field s = beanClass.getDeclaredField("s");
            System.out.println("获取指定字段: " + s.toString());
            ReflectionBean reflectionBean = beanClass.newInstance();
            s.setAccessible(true);
            s.set(reflectionBean, "str");
            System.out.println("设置字段值: " + reflectionBean.toString());
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void getConstructor(Class<ReflectionBean> beanClass) {
        // 所有"公有的"构造方法
        Constructor<?>[] constructors = beanClass.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Constructor<?> constructor = constructors[i];
            System.out.println("获取公共构造方法_i：" + i + ",name：" + constructor.toString());
        }
        System.out.println();
        System.out.println();
        // 获取所有的构造方法(包括私有、受保护、默认、公有)
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (int i = 0; i < declaredConstructors.length; i++) {
            Constructor<?> constructor = declaredConstructors[i];
            System.out.println("获取所有的构造方法_i：" + i + ",name：" + constructor.toString());
        }
        System.out.println();
        System.out.println();

        try {
            // 获取指定公共构造函数
            Constructor<ReflectionBean> constructor = beanClass.getConstructor();
            System.out.println("获取指定公共构造函数: " + constructor.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            // 获取指定构造函数
            Constructor<ReflectionBean> declaredConstructor = beanClass.getDeclaredConstructor(boolean.class, String.class, int.class);
            System.out.println("获取指定构造函数: " + declaredConstructor.toString());
            declaredConstructor.setAccessible(true);
            ReflectionBean reflectionBean = declaredConstructor.newInstance(true, "我擦", 33);
            System.out.println("构造函数创建对象: " + reflectionBean.toString());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static Class<ReflectionBean> getClazz() {
        ReflectionBean reflectionBean = new ReflectionBean();
        // 1.
        Class<? extends ReflectionBean> aClass1 = reflectionBean.getClass();
        // 2.
        Class<ReflectionBean> beanClass = ReflectionBean.class;
        // 3.
        try {
            // 为什么使用下面方法对静态内部类反射会ClassNotFoundException？
            Class<?> aClass = Class.forName("com.zkp.breath.bean.ReflectionBean");
            ReflectionBean o = (ReflectionBean) aClass.newInstance();
            o.i = 22;
            System.out.println("创建对象: " + o.toString());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return beanClass;
    }


}
