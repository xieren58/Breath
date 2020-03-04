package com.zkp.breath.review;

import java.io.Serializable;

/**
 * instanceof关键字用于判断某个对象是否为某个类（其子类或其实现类）的实例，不能用于基本类型和null的判断
 * (类，不能是基本类型)数组类型也可以使用 instanceof 来判断
 */
public class InstanceOfDemo {

    public static void main(String[] args) {
        boolean b = "String" instanceof String;
        boolean b1 = null instanceof String;
        boolean b2 = (String) null instanceof String;
        boolean b3 = "String" instanceof Serializable;
        boolean b4 = new String[]{} instanceof String[];    //(类，不能是基本类型)数组类型也可以使用 instanceof 来判断

        Class<String> stringClass = String.class;
        // 表明这个对象能不能被转化为这个类
        boolean ss = stringClass.isInstance("ss");
        boolean ss1 = stringClass.isInstance(null);

    }
}
