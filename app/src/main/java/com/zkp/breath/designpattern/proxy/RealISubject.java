package com.zkp.breath.designpattern.proxy;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class RealISubject implements ISubject {

    @Override
    public void function() {
        Log.i("RealISubject", "function: ");
    }
}
