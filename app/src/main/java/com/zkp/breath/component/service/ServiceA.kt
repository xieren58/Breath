package com.zkp.breath.component.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.os.IResultReceiver
import android.util.Log

/**
 * 如何保证Service不被杀死 ？
 *1.onStartCommand方式中，返回START_STICKY或则START_REDELIVER_INTENT
 *START_STICKY：如果返回START_STICKY，表示Service运行的进程被Android系统强制杀掉之后，Android系统会将该Service依然设置为started状态（即运行状态），但是不再保存onStartCommand方法传入的intent对象
 *START_NOT_STICKY：如果返回START_NOT_STICKY，表示当Service运行的进程被Android系统强制杀掉之后，不会重新创建该Service
 *START_REDELIVER_INTENT：如果返回START_REDELIVER_INTENT，其返回情况与START_STICKY类似，但不同的是系统会保留最后一次传入onStartCommand方法中的Intent再次保留下来并再次传入到重新创建后的Service的onStartCommand方法中
 *2.提高Service的优先级：在AndroidManifest.xml文件中对于intent-filter可以通过android:priority = "1000"这个属性设置最高优先级，1000是最高值，如果数字越小则优先级越低，同时适用于广播；
 *3.在onDestroy方法里重启Service：当service走到onDestroy()时，发送一个自定义广播，当收到广播时，重新启动service；
 *4.提升Service进程的优先级：进程优先级由高到低：前台进程 一 可视进程 一 服务进程 一 后台进程 一 空进程。可以使用startForeground将service放到前台状态，这样低内存时，被杀死的概率会低一些；
 *5.系统广播监听Service状态
 *6.将APK安装到/system/app，变身为系统级应用
 *注意：以上机制都不能百分百保证Service不被杀死，除非做到系统白名单，与系统同生共死。
 *
 *了解ActivityManagerService吗？发挥什么作用
 *参考回答：ActivityManagerService是Android中最核心的服务 ， 主要负责系统中四大组件的启动、切换、调度及应用进程的管理和调度等工作，其职责与操作系统中的进程管理和调度模块类似；
 */
class ServiceA : Service() {

    val TAG = javaClass.name

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")
    }

    // 多次使用startService()开启方式会回调多次
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand()")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind()")
        return BinderImp()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        super.onDestroy()
    }

    fun f1() {
        Log.i(TAG, "f1")
    }

    inner class BinderImp : IResultReceiver.Stub() {
        override fun send(p0: Int, p1: Bundle?) {
            if (p0 == SendCode.Code1) {
                Log.i(TAG, "send_code_1")
            }
        }
    }

    companion object SendCode {
        val Code1 = 1
    }

}
