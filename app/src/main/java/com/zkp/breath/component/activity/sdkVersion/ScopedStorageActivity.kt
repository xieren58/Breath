package com.zkp.breath.component.activity.sdkVersion

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityScopedStorageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets


/**
 * https://juejin.cn/post/6844904004032413703#heading-2
 */
class ScopedStorageActivity : BaseActivity() {

    private lateinit var binding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopedStorageBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        innerOrOuterPath()
//        innerDemo()
    }


    private fun scanMusic() {

        val s = PathUtils.getExternalStoragePath() + "/xiami/"
        if (FileUtils.isFileExists(s)) {
            val listFilesInDir = FileUtils.listFilesInDir(s)
            Log.i("ssd", "scanMusic: ")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ToastUtils.showShort("sdk 29")
        }

        val musicResolver: ContentResolver = contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor? = musicResolver.query(musicUri,
                null, null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            // 标题
            val titleColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            // id
            val idColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            // 创建音频文件的艺术家（如果有），即作者
            val artistColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            // 音频文件来自的专辑的ID（如果有）
            val albumIdColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            // 磁盘上媒体项的绝对文件系统路径
            val dataColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            // 音频文件的录制年份（如果有）
            val yearColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
            // 时长
            val durationColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            // 是否音乐
            val isMusicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)

            do {
                val id = if (idColumnIndex != -1) musicCursor.getLong(idColumnIndex) else -1L
                val title = if (titleColumnIndex != -1) musicCursor.getString(titleColumnIndex) else ""
                val artist = if (artistColumnIndex != -1) musicCursor.getString(artistColumnIndex) else ""
                val albumId = if (albumIdColumnIndex != -1) musicCursor.getLong(albumIdColumnIndex) else -1L
                val data = if (dataColumnIndex != -1) musicCursor.getString(dataColumnIndex) else ""
                val year = if (yearColumnIndex != -1) musicCursor.getString(yearColumnIndex) else ""
                val duration = if (durationColumnIndex != -1) musicCursor.getString(durationColumnIndex) else ""
                val isMusic = if (isMusicColumnIndex != -1) musicCursor.getString(isMusicColumnIndex) else ""

                Log.i("音乐信息", "id:$id, title:$title, artist:$artist," +
                        " albumId:$albumId, data:$data, year:$year, duration:$duration, isMusic:$isMusic")

            } while (musicCursor.moveToNext())
        }
        musicCursor?.close()
    }


    //这里的fileName指文件名，不包含路径
    //relativePath 包含某个媒体下的子路径
    private fun insertFileIntoMediaStore(fileName: String, fileType: String, relativePath: String): Uri? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
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

    /**
     * 1. 应用内部和外部存储的常用路径（files/cache），因为都是应用自身的目录，所以不需要权限可以直接访问(读写)。
     * 2. 外部公共目录，不需要读写权限，android10及以上需要通过MediaStore访问。
     */
    private fun innerOrOuterPath() {
        // 内部。无需权限，且卸载应用时会自动删除
        val filesDir = this.filesDir    // 常用
        val cacheDir = this.cacheDir    // 常用
        val codeCacheDir = this.codeCacheDir    // 没用过，仅作为了解
        val noBackupFilesDir = this.noBackupFilesDir    // 没用过，仅作为了解
        val databaseList = this.databaseList()  // 数据库
        Log.i("内部存储路径", "filesDir: $filesDir")
        Log.i("内部存储路径", "cacheDir: $cacheDir")
        Log.i("内部存储路径", "codeCacheDir: $codeCacheDir")
        Log.i("内部存储路径", "noBackupFilesDir: $noBackupFilesDir")
        for (path in databaseList) {
            Log.i("内部存储路径", "databaseList: $path")
        }

        // 外部应用存储。无需权限，且卸载应用时会自动删除
        val externalCacheDir = this.externalCacheDir    // 常用
        val externalFilesDir = this.getExternalFilesDir(null)   // 常用
        // 常用，其实就是在files文件下再分文件夹
        val externalFilesMusicDir = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        Log.i("外部存储路径", "externalCacheDir: $externalCacheDir")
        Log.i("外部存储路径", "externalFilesDir: $externalFilesDir")
        Log.i("外部存储路径", "externalFilesDir: $externalFilesMusicDir")

        fun util() {
            // 外部
            val externalAppFilesPath = PathUtils.getExternalAppFilesPath()
            val externalAppCachePath = PathUtils.getExternalAppCachePath()
            Log.i("工具类应用外部", "externalAppFilesPath: $externalAppFilesPath")
            Log.i("工具类应用外部", "externalAppCachePath: $externalAppCachePath")

            // 内部
            val internalAppFilesPath = PathUtils.getInternalAppFilesPath()
            val internalAppCachePath = PathUtils.getInternalAppCachePath()
            Log.i("工具类应用内部", "internalAppFilesPath: $internalAppFilesPath")
            Log.i("工具类应用内部", "internalAppCachePath: $internalAppCachePath")

            // 外部files文件夹下的分包示例
            val externalAppMusicPath = PathUtils.getExternalAppMusicPath()
            val externalAppDownloadPath = PathUtils.getExternalAppDownloadPath()

            // 外部公共目录，android10及以上需要通过MediaStore访问。
            val externalMusicPath = PathUtils.getExternalMusicPath()
            val externalDownloadsPath = PathUtils.getExternalDownloadsPath()
            Log.i("工具类外部公共目录", "externalMusicPath: $externalMusicPath")
            Log.i("工具类外部公共目录", "externalDownloadsPath: $externalDownloadsPath")
        }
        util()
    }

}