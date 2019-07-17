package com.zkp.breath.designpattern.adapter.objectadapter;

import com.zkp.breath.designpattern.adapter.classadapter.IAdaptee;

/**
 * Created b Zwp on 2019/7/17.
 */
public class Adaptee2 implements IAdaptee {

    @Override
    public String filter(String s) {
        return s + "Adaptee2";
    }
}
