package com.zkp.breath.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity


/**
 * 电池
 */


/**
 * 忽略电池优化（电池优化其实就是限制后台运行，减少后台运行的进程来达到省电的目的）
 */
fun ignoreBatteryOptimization(activity: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
            val powerManager = activity.getSystemService(AppCompatActivity.POWER_SERVICE) as PowerManager
            val hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.packageName)
            /**
             * 判断当前APP是否有加入电池优化的白名单，
             * 如果没有，弹出加入电池优化的白名单的设置对话框
             */
            if (!hasIgnored) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:" + activity.packageName)
                activity.startActivity(intent)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}