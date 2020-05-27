package com.zkp.breath;


import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.zkp.breath.mmkv.template.AppConfiguration;
import com.zkp.breath.utils.UmUtils;


/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance = null;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (ProcessUtils.isMainProcess()) {
            // 常用工具库初始化
            com.blankj.utilcode.util.Utils.init(this);
            // 初始化界面卡顿检查工具
            BlockCanary.install(this, new BlockCanaryContext()).start();
            // 初始化阿里路由器框架
            initARouter();
            // 初始化滴滴研发助手
            DoraemonKit.install(this);
            AppConfiguration.getDefault(this);

            try {
                UmUtils.initUmAnalytics(this);
                // 接入推送就闪退，5。18号发现，之前不会
//                UmUtils.initUmPush(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        try {
//            UmUtils.initUmPushOnUmPushProcess(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initARouter() {
        if (AppUtils.isAppDebug()) {
            // 下面两句代码必须在init前，否则无效
            // 打印日志
            ARouter.openLog();
            // 开启调试模式
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
