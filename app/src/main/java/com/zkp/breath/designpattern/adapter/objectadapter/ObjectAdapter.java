package com.zkp.breath.designpattern.adapter.objectadapter;

import android.util.Log;

import com.zkp.breath.designpattern.adapter.classadapter.IAdaptee;
import com.zkp.breath.designpattern.adapter.classadapter.ITarget;

/**
 * Created b Zwp on 2019/7/17.
 */
public class ObjectAdapter implements ITarget {

    private IAdaptee mIAdaptee;

    public ObjectAdapter(IAdaptee iAdaptee) {
        mIAdaptee = iAdaptee;
    }

    @Override
    public void request(String s) {
        Log.i("ITarget", "request: " + mIAdaptee.filter(s));
    }
}
