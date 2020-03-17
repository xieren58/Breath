package com.zkp.breath;

import android.os.StrictMode;

import androidx.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 常用工具库初始化
        com.blankj.utilcode.util.Utils.init(this);
        setupLeakCanary();
    }

    protected void setupLeakCanary() {
        // 判断是否是 HeapAnalyzerService 所属进程
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        // 注册 LeakCanary
        LeakCanary.install(this);
    }

}
