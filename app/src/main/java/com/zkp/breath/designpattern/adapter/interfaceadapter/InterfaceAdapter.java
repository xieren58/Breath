package com.zkp.breath.designpattern.adapter.interfaceadapter;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 * 一个接口有太多方法，而在某种情况下我们只需要几个方法的时候，我们先提供默认实现类，等需要用的时候再重写
 */
public abstract class InterfaceAdapter implements CallBackListener {

    @Override
    public void a() {
        Log.i("InterfaceAdapter", "a: ");
    }

    @Override
    public void b() {
        Log.i("InterfaceAdapter", "a: ");
    }

    @Override
    public void c() {
        Log.i("InterfaceAdapter", "a: ");
    }

    @Override
    public void d() {
        Log.i("InterfaceAdapter", "a: ");
    }
}
