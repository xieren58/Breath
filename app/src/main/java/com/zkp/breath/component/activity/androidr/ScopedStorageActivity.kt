package com.zkp.breath.component.activity.androidr

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import com.blankj.utilcode.util.PathUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityScopedStorageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets


class ScopedStorageActivity : BaseActivity() {

    private lateinit var binding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopedStorageBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

//        innerOrOuterPath()
//        innerDemo()
        util()
    }


    //这里的fileName指文件名，不包含路径
    //relativePath 包含某个媒体下的子路径
    private fun insertFileIntoMediaStore(fileName: String, fileType: String, relativePath: String): Uri? {
        if (Build.VERSION.SDK_INT < Q) {
            return null
        }
        val resolver: ContentResolver = this.getContentResolver()
        //设置文件参数到ContentValues中
        val values = ContentValues()
        //设置文件名
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        //设置文件描述，这里以文件名为例子
//        values.put(MediaStore.Downloads.DESCRIPTION, fileName)
        //设置文件类型
        values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive")
        //注意RELATIVE_PATH需要targetVersion=29
        //故该方法只可在Android10的手机上执行
        values.put(MediaStore.Downloads.RELATIVE_PATH, relativePath)
        //EXTERNAL_CONTENT_URI代表外部存储器
        val external: Uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        //insertUri表示文件保存的uri路径
        return resolver.insert(external, values)
    }

    private fun util() {
        // 外部
        val externalAppFilesPath = PathUtils.getExternalAppFilesPath()
        val externalAppCachePath = PathUtils.getExternalAppCachePath()
        // 内部
        val internalAppFilesPath = PathUtils.getInternalAppFilesPath()
        val internalAppCachePath = PathUtils.getInternalAppCachePath()
        println()

        val externalMusicPath = PathUtils.getExternalMusicPath()
        val externalAppMusicPath = PathUtils.getExternalAppMusicPath()
        com.blankj.utilcode.util.FileUtils.createOrExistsDir(externalAppMusicPath)

        val downloadCachePath = PathUtils.getDownloadCachePath()
        val externalDownloadsPath = PathUtils.getExternalDownloadsPath()
        val externalAppDownloadPath = PathUtils.getExternalAppDownloadPath()
        com.blankj.utilcode.util.FileUtils.createOrExistsDir(externalAppDownloadPath)

    }

    private fun innerDemo() {
        val apkFilePath: String? = this.getExternalFilesDir("apk")?.absolutePath
        // 会报错误
        // java.lang.IllegalArgumentException: Primary directory  not allowed for content://media/external_primary/file; allowed directories are [Download, Documents]
//        val apkFilePath: String? =Environment.getExternalStorageDirectory().absoluteFile.toString()
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
    }

    fun innerOrOuterPath() {
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

        // 使用Environment.isExternalStorageLegacy()来检查APP的运行模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            // 可以在用户升级后，能方便的将用户的数据移动至应用的特定目录
        }
    }

}