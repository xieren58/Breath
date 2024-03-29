package com.zkp.breath

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.*
import com.didichuxing.doraemonkit.DoraemonKit
import com.simple.spiderman.SpiderMan
import com.zkp.breath.mmkv.template.AppConfiguration
import com.zkp.breath.utils.HookUtils
import com.zkp.breath.utils.UmUtils

/**
 * Created b Zwp on 2019/7/25.
 * 继承MultiDexApplication或者MultiDex.install(this);
 */
class BaseApplication : MultiDexApplication() {

//    private val observer by lazy { JetPackDefaultLifecycleObserverImp() }

    override fun onCreate() {
        super.onCreate()
        if (ProcessUtils.isMainProcess()) {
            HookUtils.hookMacAddress("违规获取mac的Tag", this)
            // 初始化工具库
            Utils.init(this)
            initARouter()
            initAssistKit()
            // 初始化mmkv
            AppConfiguration.getDefault(this)
//            initUmAnalytics()
            appStatusChanged()
        }
//        initUmPushOnUmPushProcess()
    }

    /**
     * 监听应用前后台切换
     */
    private fun appStatusChanged() {
        // 新的监听应用前后台切换的方式
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
        // 旧的监听应用前后台切换的方式
        AppUtils.registerAppStatusChangedListener(onAppStatusChangedListener)
        // 监听所有activity的生命周期变化
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    var isKilled = true

    /**
     * Activity 生命周期监听
     */
    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            /**
             * 重建发生进行重启app的操作，防止其余页面或者当前页面的资源被回收导致出现异常。
             * debug环境下不使用，让问题暴露才能更好解决。
             */
            if (!BuildConfig.DEBUG) {
                if (StringUtils.equals(ActivityUtils.getLauncherActivity(), activity::class.java.name)) {
                    // 启动activity则设置为false
                    isKilled = false
                } else {
                    // 非启动页且isKilled为true则表示application被重建，重启Launcher页，并且杀掉顶部重建的activity
                    // 保证所有初始化流程不受影响
                    if (isKilled) {
                        val topActivity = ActivityUtils.getTopActivity()
                        Log.i("onActivityCreated", "发生app重建，发生页面: ${topActivity::class.java.name}")
                        ActivityUtils.startLauncherActivity()
                        ActivityUtils.finishActivity(topActivity)
                    }
                }
            }
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    }

    val observer = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onCreate()")
        }

        override fun onStart(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onStart()")
        }

        override fun onResume(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onResume()")
        }

        override fun onPause(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onPause()")
        }

        override fun onStop(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onStop()")
        }

        override fun onDestroy(owner: LifecycleOwner) {
            Log.i("ProcessLifecycle", "onDestroy()")
        }
    }

    @kotlin.jvm.JvmField
    val onAppStatusChangedListener = object : Utils.OnAppStatusChangedListener {
        override fun onForeground(activity: Activity) {
            Log.i("AppStatusChanged", "onForeground：切到桌面")
        }

        override fun onBackground(activity: Activity) {
            Log.i("AppStatusChanged", "onBackground：切回app")
        }
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

    private fun initAssistKit() {
        // 初始化滴滴研发助手
        DoraemonKit.install(this)
        // 初始化崩溃可视化小工具，需要放在bugly初始化前面，bugly源码会在finally里面会将处理权下放
        SpiderMan.init(this)
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