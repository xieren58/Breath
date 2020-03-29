package com.zkp.breath.component.activity.androidr

import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.zkp.breath.component.activity.base.BaseActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets


class ScopedStorageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        // 使用Environment.isExternalStorageLegacy()来检查APP的运行模式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
//            // 可以在用户升级后，能方便的将用户的数据移动至应用的特定目录
//        }

        val apkFilePath: String = this.getExternalFilesDir("apk")?.getAbsolutePath()
                ?: throw NullPointerException("File为null")
        val newFile = File(apkFilePath + File.separator.toString() + "temp.apk")
        var os: OutputStream? = null
        try {
            os = FileOutputStream(newFile)
            os.write("file is created".toByteArray(StandardCharsets.UTF_8))
            os.flush()
        } catch (e: IOException) {
        } finally {
            try {
                os?.close()
            } catch (e1: IOException) {
            }
        }


        // 使用Environment.isExternalStorageLegacy()来检查APP的运行模式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
//            // 可以在用户升级后，能方便的将用户的数据移动至应用的特定目录
//        }
    }

    fun openFile() {
        val filesDir = this.filesDir
        Log.i(TAG, "filesDir: $filesDir");

        val cacheDir = this.cacheDir
        Log.i(TAG, "cacheDir: $cacheDir");

        // 无需权限，且卸载应用时会自动删除
        val externalCacheDir = this.externalCacheDir
        Log.i(TAG, "externalCacheDir: $externalCacheDir");

        // 无需权限，且卸载应用时会自动删除
        val externalFilesDir = this.getExternalFilesDir("apk")
        Log.i(TAG, "externalFilesDir: $externalFilesDir")

        val absoluteFile = Environment.getExternalStorageDirectory().absoluteFile
        Log.i(TAG, "absoluteFile: $absoluteFile")
    }

}