package com.zkp.breath.designpattern.proxy;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class ProxyISubject implements ISubject {

    private ISubject mISubject;

    public ProxyISubject(ISubject iSubject) {
        mISubject = iSubject;
    }

    @Override
    public void function() {
        Log.i("ProxyISubject", "function: 扩展代码1");
        mISubject.function();
        Log.i("ProxyISubject", "function: 扩展代码2");
    }
}
