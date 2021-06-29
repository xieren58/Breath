package com.zkp.breath.utils

import android.app.Service
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Vibrator

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