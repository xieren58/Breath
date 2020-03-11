package com.zkp.breath.designpattern.adapter.classadapter;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 * ITarget可以看成是我要的格式，而Adaptee是用户提供的格式。这样我们就能获取到我们指定的格式，但实际操作的是用户提供的数据
 * 我们创建完对象后用ITarget去指向，但是内部内部调用Adaptee的方法。
 */
public class ClassAdapter extends Adaptee implements ITarget {

    @Override
    public void request(String s) {
        Log.i("ITarget", "request: " + filter(s));
    }

}
