package com.zkp.breath

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.zkp.breath.mmkv.template.AppConfiguration
import com.zkp.breath.utils.UmUtils

/**
 * Created b Zwp on 2019/7/25.
 */
class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (ProcessUtils.isMainProcess()) {
            // 初始化工具库
            Utils.init(this)
            initARouter()
            initDoraemonKit()
            // 初始化mmkv
            AppConfiguration.getDefault(this)
            initUmAnalytics()
        }
//        initUmPushOnUmPushProcess()
    }

    private fun initUmPushOnUmPushProcess() {
        try {
            UmUtils.initUmPushOnUmPushProcess(this)
        } catch (e: Exception) {
        }
    }

    private fun initUmAnalytics() {
        try {
            UmUtils.initUmAnalytics(this)
            // 接入推送就闪退，5。18号发现，之前不会
            //                UmUtils.initUmPush(this);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化滴滴研发助手
     */
    private fun initDoraemonKit() {
        DoraemonKit.install(this)
        DoraemonKit.disableUpload()
        DoraemonKit.setAwaysShowMainIcon(true)
    }

    /**
     * 初始化阿里路由器框架
     */
    private fun initARouter() {
        if (AppUtils.isAppDebug()) {
            // 下面两句代码必须在init前，否则无效
            // 打印日志
            ARouter.openLog()
            // 开启调试模式
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}