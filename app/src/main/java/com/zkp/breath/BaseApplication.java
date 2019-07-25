package com.zkp.breath;

import android.app.Application;

/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.blankj.utilcode.util.Utils.init(this);
    }
}
