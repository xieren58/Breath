package com.zkp.breath.designpattern.flyweight;

import java.util.HashMap;

/**
 * 享元模式：主要用于减少创建对象的数量，以减少内存占用和提高性能，属于结构型模式。
 * <p>
 * 在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，如果有相同的业务请求，直接返回在内存中
 * 已有的对象，避免重新创建。
 */
public class FlyWeightFactory {

    private static final HashMap<String, Flyweight> map = new HashMap<>();

    public static Flyweight getFlyweight(String type) {
        Flyweight flyweight = map.get(type);

        if (flyweight == null) {
            if (type.equals("1")) {
                flyweight = new ConreteFlyweightA();
                map.put("1", flyweight);
            } else if (type.equals("2")) {
                flyweight = new ConreteFlyweightB();
                map.put("2", flyweight);
            }
        }

        return flyweight;
    }

}
