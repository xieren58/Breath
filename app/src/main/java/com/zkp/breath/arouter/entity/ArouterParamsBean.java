package com.zkp.breath.arouter.entity;

import java.io.Serializable;

/**
 * Rrouter传递的自定义参数必须实现Serializable或者Parcelable
 */
public class ArouterParamsBean implements Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ArouterParamsBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
