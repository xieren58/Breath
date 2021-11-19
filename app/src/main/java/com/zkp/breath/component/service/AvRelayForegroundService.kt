package com.zkp.breath.component.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.os.IResultReceiver
import android.util.Log
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.ServiceUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.BackForegroundActivity

/**
 * 音视频采集的前台服务
 *
 * android 8 (O,26)及其以上，启动的服务都是前台服务Context # startForegroundService()，启动后五秒内需要调用Service # startForeground()
 *
 *
 * 为什么 Android 9 应用锁屏或切后台后采集音视频无效？
 * https://docs.agora.io/cn/All/faq/android_background
 */
class AvRelayForegroundService : Service() {

    val TAG = javaClass.name

    companion object {
        const val NOTIFICATION_ID = 1300
        fun startService(packageContext: Context, name: String) {
            val intent = Intent(packageContext, AvRelayForegroundService::class.java)
            intent.putExtra("name", name)
            ServiceUtils.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")
    }

    // 多次使用startService()开启方式会回调多次
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand()")
        // 点击事件
        val clickIntent = Intent(this, BackForegroundActivity::class.java)
        clickIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val clickPI = PendingIntent.getActivity(this, 982, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        val name = intent?.getStringExtra("name") ?: "通话中"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(NOTIFICATION_ID.toString(), "通话前台服务", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "通话前台服务"//设置渠道的描述信息
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val notification: Notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_ID.toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name)//设置通知标题
                .setContentText("通话中")//设置通知内容
                .setAutoCancel(true)//设为true，点击后通知栏移除通知，setAutoCancel需要和setContentIntent一起使用，否则无效
                .setContentIntent(clickPI)// 设置pendingIntent,点击通知时就会用到
                .build()
            startForeground(NOTIFICATION_ID, notification)
        }
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
        try {
            NotificationUtils.cancel(NOTIFICATION_ID)
        } catch (e: Exception) {
        }
    }

    inner class BinderImp : IResultReceiver.Stub() {
        override fun send(p0: Int, p1: Bundle?) {
            if (p0 == 1) {
                Log.i(TAG, "send_code_1")
            }
        }
    }

}
