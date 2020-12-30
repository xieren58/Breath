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
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityScopedStorageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets


/**
 * https://juejin.cn/post/6844904004032413703#heading-2
 * https://juejin.cn/post/6844904073024503822
 * https://zhuanlan.zhihu.com/p/128558892
 */
class ScopedStorageActivity : BaseActivity() {

    private lateinit var binding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopedStorageBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        innerOrOuterAppPath()
        externalStoragePublicDir()
//        innerDemo()
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
     * 1. Android Q开始，针对外部公有目录的File API失效(可能是因为国内厂商的问题，File的exists()是有效的，反正
     *    就把File全部的Api都看成是无效的)，当进行创建或者读取操作时无论是否开启权限都会返回"Permission denied"
     *    异常信息。
     * 2. 对于图片，视频，影频等媒体资源需要使用MediaStore进行操作。如果是自己应用创建文件或者访问应用自己创建文件，
     *    不需要申请存储权限；如果是访问其他应用创建的则需要申请权限，未申请存储权限，通过ContentResolver查询不到
     *    文件Uri，即使通过其他方式获取到文件Uri，读取或创建文件会抛出异常。建议始终申请权限，避免判断是否为自身应用
     *    的文件。
     * 3. 非媒体文件(pdf、office、doc、txt等)只能够通过Storage Access Framework方式访问，不需要申请权限。
     *
     *  测试：
     *    a应用写入一个文件，b应用访问是否需要权限
     *    a应用写入一个文件，a应用访问是否需要权限
     */
    private fun externalStoragePublicDir() {
        val externalMusicPath = PathUtils.getExternalMusicPath()
        val externalDownloadsPath = PathUtils.getExternalDownloadsPath()
        Log.i("工具类外部公共目录", "externalMusicPath: ${externalMusicPath}, " +
                "是否存在： ${File(externalMusicPath).exists()}")
        Log.i("工具类外部公共目录", "externalDownloadsPath: ${externalDownloadsPath}, " +
                "是否存在： ${File(externalDownloadsPath).exists()}")

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val musicPath = "$externalMusicPath/无畏.mp3"
            // exists()有效
            Log.i("工具类外部公共目录_目录下的文件", "musicPath: ${musicPath}, " +
                    "是否存在： ${File(musicPath).exists()}")
            // 如果在Q及以上版本进行下面的读取操作, 将会抛出以下异常：
            // "/storage/emulated/0/Music/无畏.mp3: open failed: EACCES (Permission denied)"
            val readFile2BytesByStream = FileIOUtils.readFile2BytesByStream(musicPath)

            // 如果在Q及以上版本进行下面的创建操作, 将会抛出以下异常：
            // "java.io.IOException: Permission denied"
            val s = "$externalDownloadsPath/1024.txt"
            FileUtils.createOrExistsFile(s)
            Log.i("工具类外部公共目录_目录下的文件", "s: ${s}, " + "是否存在： ${File(s).exists()}")
        } else {
            scanMusic()
            scanVideo()
            scanImage()
        }
    }

    private fun scanImage() {
        val IMAGE_PROJECTION = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        val imageCursor: Cursor? = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[0] + " DESC")

        if (imageCursor != null && imageCursor.moveToFirst()) {
            do {
                var path = imageCursor.getString(imageCursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                val name = imageCursor.getString(imageCursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))
                val id = imageCursor.getInt(imageCursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
                val folderPath = imageCursor.getString(imageCursor.getColumnIndexOrThrow(IMAGE_PROJECTION[3]))
                val folderName = imageCursor.getString(imageCursor.getColumnIndexOrThrow(IMAGE_PROJECTION[4]))

                Log.i("音乐信息", "path:$path, name:$name, id:$id," +
                        " folderPath:$folderPath, folderPath:$folderPath, folderName:$folderName")

                //Android Q 公有目录只能通过Content Uri，以前的File路径全部无效，如果是Video，记得换成MediaStore.Videos
                path = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        .buildUpon()
                        .appendPath(id.toString())
                        .build()
                        .toString()
                Log.i("Content Uri", "path: $path")

            } while (imageCursor.moveToNext())
        }
    }

    private fun scanVideo() {
        val contentResolver: ContentResolver = contentResolver
        val videoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoCursor: Cursor? = contentResolver.query(videoUri,
                null, null, null, null)

        if (videoCursor != null && videoCursor.moveToFirst()) {
            // 标题
            val titleColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE)
            // id
            val idColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.Media._ID)
            // 创建音频文件的艺术家（如果有），即作者
            val artistColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.Media.ARTIST)
            // 磁盘上媒体项的绝对文件系统路径
            val dataColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.Media.DATA)
            // 时长
            val durationColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.Media.DURATION)

            do {
                val id = if (idColumnIndex != -1) videoCursor.getLong(idColumnIndex) else -1L
                val title = if (titleColumnIndex != -1) videoCursor.getString(titleColumnIndex) else ""
                val artist = if (artistColumnIndex != -1) videoCursor.getString(artistColumnIndex) else ""
                val data = if (dataColumnIndex != -1) videoCursor.getString(dataColumnIndex) else ""
                val duration = if (durationColumnIndex != -1) videoCursor.getString(durationColumnIndex) else ""

                Log.i("视频信息", "id:$id, title:$title, artist:$artist, data:$data, duration:$duration")

            } while (videoCursor.moveToNext())
        }
        videoCursor?.close()
    }

    private fun scanMusic() {
        val contentResolver: ContentResolver = contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor? = contentResolver.query(musicUri,
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


    /**
     * 1. 应用内部和外部存储的常用路径（files/cache），不需要申请储存空间读写权限，且卸载应用时会自动删除。
     * 2. 应用内部和外部在Android Q及以上，File API还是生效。
     */
    private fun innerOrOuterAppPath() {
        // 内部。无需权限，且卸载应用时会自动删除
        val filesDir = this.filesDir    // 常用
        val cacheDir = this.cacheDir    // 常用
        val codeCacheDir = this.codeCacheDir    // 没用过，仅作为了解
        val noBackupFilesDir = this.noBackupFilesDir    // 没用过，仅作为了解
        val databaseList = this.databaseList()  // 数据库
        Log.i("内部存储路径", "filesDir: ${filesDir}, 是否存在： ${filesDir.exists()}")
        Log.i("内部存储路径", "cacheDir: $cacheDir")
        Log.i("内部存储路径", "codeCacheDir: $codeCacheDir")
        Log.i("内部存储路径", "noBackupFilesDir: $noBackupFilesDir")
        for (path in databaseList) {
            Log.i("内部存储路径", "databaseList: $path")
        }

        val createFilePath = "$cacheDir/1024.txt"
        FileUtils.createOrExistsFile(createFilePath)
        Log.i("内部存储路径_用户创建", "createFilePath: ${createFilePath}, " +
                "是否存在： ${File(createFilePath).exists()}")

        // 外部应用存储。无需权限，且卸载应用时会自动删除
        val externalCacheDir = this.externalCacheDir    // 常用
        val externalFilesDir = this.getExternalFilesDir(null)   // 常用
        // 常用，其实就是在files文件下再分文件夹
        val externalFilesMusicDir = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        Log.i("外部存储路径", "externalCacheDir: ${externalCacheDir}, 是否存在：${externalCacheDir?.exists()}")
        Log.i("外部存储路径", "externalFilesDir: $externalFilesDir")
        Log.i("外部存储路径", "externalFilesDir: $externalFilesMusicDir")

        val externalCreateFilePath = "$externalCacheDir/1024.txt"
        FileUtils.createOrExistsFile(externalCreateFilePath)
        Log.i("外部存储路径_用户创建", "createFilePath: ${externalCreateFilePath}, " +
                "是否存在： ${File(externalCreateFilePath).exists()}")

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

            // 外部files文件夹下的分包示例，需要自己创建
            val externalAppMusicPath = PathUtils.getExternalAppMusicPath()
            val externalAppDownloadPath = PathUtils.getExternalAppDownloadPath()
        }
        util()
    }

}