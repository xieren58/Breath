package com.zkp.breath;


import android.content.Context;
import android.util.DebugUtils;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMLogUtils;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.Arrays;


/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMainProcess()) {
            // 常用工具库初始化
            com.blankj.utilcode.util.Utils.init(this);
            // 初始化界面卡顿检查工具
            BlockCanary.install(this, new BlockCanaryContext()).start();
            initARouter();

            initUMConfigure();
        }

        // 主进程和推送进程都需要初始化
        if (ProcessUtils.getCurrentProcessName().equals("com.zkp.breath:channel")) {
            initUmPush();
            LogUtils.i("当前进程为：" + ProcessUtils.getCurrentProcessName());
        }

    }

    private void initUMConfigure() {
        // 当debug模式下开启log
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        // 初始化SDK
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "765597c10774728c396403cbf461ff6a");
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        // 用于集成测试获取设备信息
//        String[] testDeviceInfo = getTestDeviceInfo(this);
//        LogUtils.i("UM_设备信息", Arrays.toString(testDeviceInfo));

        initUmPush();
    }

    private void initUmPush() {
        // 推送流程
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                // 注册成功会返回deviceToken，deviceToken是推送消息的唯一标志
                LogUtils.i("UM_Push", "注册成功：deviceToken：" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.i("UM_Push", "注册失败：" + "s:" + s + ",s1:" + s1);
            }
        });
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
        return deviceInfo;
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
