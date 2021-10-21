package com.zkp.breath.utils

import android.app.ActivityManager

/**
 * https://droidyue.com/blog/2015/08/01/dive-into-android-large-heap/index.html
 */

/**
 * 获得内用正常情况下内存的大小
 * @param getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
 */
fun getMemoryClass(manager: ActivityManager): Int {
    return manager.getMemoryClass()
}

/**
 * 获得开启largeHeap最大的内存大小
 * @param getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
 */
fun getLargeMemoryClass(manager: ActivityManager): Int {
    return manager.getLargeMemoryClass()
}