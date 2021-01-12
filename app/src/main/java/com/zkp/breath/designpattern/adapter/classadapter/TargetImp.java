package com.zkp.breath.designpattern.adapter.classadapter;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class TargetImp implements ITarget {

    @Override
    public void request(String s) {
        System.out.println("TargetImp_request: " + s);
    }
}
