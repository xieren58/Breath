package com.zkp.breath.designpattern.proxy;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class RealSubject implements ISubject {

    @Override
    public void function() {
        System.out.println("RealISubject_function");
    }
}
