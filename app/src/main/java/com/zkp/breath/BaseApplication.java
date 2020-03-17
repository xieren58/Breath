package com.zkp.breath;


import androidx.multidex.MultiDexApplication;


/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 常用工具库初始化
        com.blankj.utilcode.util.Utils.init(this);
    }

}
