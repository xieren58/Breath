package com.zkp.breath;


import androidx.multidex.MultiDexApplication;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;


/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 常用工具库初始化
        com.blankj.utilcode.util.Utils.init(this);
        // 初始化界面卡顿检查工具
        BlockCanary.install(this, new BlockCanaryContext()).start();
    }

}
