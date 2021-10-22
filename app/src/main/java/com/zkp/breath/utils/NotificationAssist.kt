package com.zkp.breath.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.provider.Settings

/**
 * （不管怎么修改 NotificationChannel 振动属性都没有效果，解决方案就是主动让手机振动。）
 *
 * 手机震动一下，振动需要添加权限
 * 清单文件添加振动权限 <uses-permission android:name="android.permission.VIBRATE" />
 */
fun playNotificationVibrate(context: Context) {
    val vibrator: Vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    // a为振动前等待的时长，b为振动的持续时长
    val vibrationPattern = longArrayOf(0, 180, 80, 120)
    // 第一个参数为开关开关的时间，第二个参数是重复次数
    vibrator.vibrate(vibrationPattern, -1)
}

/**
 * 播放通知声音
 */
fun playNotificationRing(context: Context) {
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val rt: Ringtone = RingtoneManager.getRingtone(context, uri)
    rt.play()
}

/**
 * 打开通知权限设置
 */
fun openNotification(context: Context) {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra("app_package", context.packageName)
        intent.putExtra("app_uid", context.applicationInfo.uid)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.packageName, null)
    } else {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
        intent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
    }
    context.startActivity(intent)
}