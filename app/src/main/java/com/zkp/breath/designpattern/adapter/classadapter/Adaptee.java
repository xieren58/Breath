package com.zkp.breath.designpattern.adapter.classadapter;

/**
 * Created b Zwp on 2019/7/17.
 */
public class Adaptee implements IAdaptee {

    @Override
    public String filter(String s) {
        return s + "Adaptee";
    }
}
