package com.zkp.breath.component.activity.sdkVersion

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.*
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityScopedStorageBinding
import java.io.*


/**
 * https://juejin.cn/post/6844904004032413703#heading-2
 * https://juejin.cn/post/6844904073024503822
 * https://zhuanlan.zhihu.com/p/128558892
 *
 */
class ScopedStorageQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopedStorageBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        innerOrOuterAppPath()
        externalStoragePublicDir()

        varargSetClickListener(binding.tvMediaSave, binding.tvDownloadSave, binding.tvApkInstall)
    }

    override fun onDebouncingClick(v: View) {
        if (v == binding.tvMediaSave) {
            // 示例将应用内部存储文件插入到公共媒体目录
            val internalAppFilesPath = PathUtils.getInternalAppFilesPath() + "/ic_test.png"
            ResourceUtils.copyFileFromAssets("ic_test.png", internalAppFilesPath)
            val saveImageWithAndroidQ = saveImageWithAndroidQ(this, File(internalAppFilesPath), "testQ.png")
            ToastUtils.showShort("保存: $saveImageWithAndroidQ")
            return
        }

        if (v == binding.tvDownloadSave) {
            // 示例将应用内部存储文件插入到公共目录Download
            val internalAppFilesPath = PathUtils.getInternalAppFilesPath() + "/ic_test.png"
            ResourceUtils.copyFileFromAssets("ic_test.png", internalAppFilesPath)
            val copyToDownloadAndroidQ = copyToDownloadAndroidQ(this,
                    internalAppFilesPath, "testQ.png", "", 0)
            ToastUtils.showShort("保存: $copyToDownloadAndroidQ")
            return
        }

        // 安装apk的例子
        if (v == binding.tvApkInstall) {
            // 将内置文件拷贝到应用内部储存，然后再插入到公共目录Download
            val internalAppFilesPath = PathUtils.getInternalAppFilesPath() + "/apk_test.apk"
            ResourceUtils.copyFileFromAssets("apk_test.apk", internalAppFilesPath)
            if (!FileUtils.isFileExists(PathUtils.getExternalDownloadsPath() + "/apk_test.apk")) {
                val copyToDownloadAndroidQ = copyToDownloadAndroidQ(this,
                        internalAppFilesPath, "apk_test", "", 1)
                ToastUtils.showShort("保存: $copyToDownloadAndroidQ")
            }
            // 通过FileProvider获取公共目录下文件的uri
            val authority = Utils.getApp().packageName + ".utilcode.provider"
            val uri = FileProvider.getUriForFile(Utils.getApp(), authority, File(internalAppFilesPath))
            // 通过uri安装apk
            AppUtils.installApp(uri)
        }
    }

    /**
     * 判断公有目录文件是否存在，自Android Q开始，公有目录File API都失效，不能直接通过new File(path).exists();
     * 判断公有目录文件是否存在.
     */
    private fun isAndroidQFileExists(filePath: String): Boolean {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                val uri = Uri.parse(filePath)
                val cr = Utils.getApp().contentResolver
                val afd = cr.openAssetFileDescriptor(uri, "r") ?: return false
                try {
                    afd.close()
                } catch (ignore: IOException) {
                }
            } catch (e: FileNotFoundException) {
                return false
            }
            return true
        }
        return false
    }

    /**
     * 保存资源到Download公共目录
     */
    private fun copyToDownloadAndroidQ(context: Context,
                                       sourcePath: String,
                                       fileName: String?,
                                       saveDirName: String,
                                       mimeType: Int): Boolean {

        // 以下api需要sdk>=Q
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return false
        }

        val values = ContentValues()
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        if (mimeType == 0) {
            values.put(MediaStore.Downloads.MIME_TYPE, "image/png")
        } else if (mimeType == 1) {
            values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive") // apk类型
        }
        values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/$saveDirName")
        val external = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver
        val insertUri = resolver.insert(external, values) ?: return false
        val mFilePath = insertUri.toString()
        var `is`: InputStream? = null
        var os: OutputStream? = null
        var result = false
        try {
            os = resolver.openOutputStream(insertUri)
            if (os == null) {
                return result
            }
            var read: Int
            val sourceFile = File(sourcePath)
            if (sourceFile.exists()) { // 文件存在时
                `is` = FileInputStream(sourceFile) // 读入原文件
                val buffer = ByteArray(1024 * 4)
                while (`is`.read(buffer).also { read = it } != -1) {
                    os.write(buffer, 0, read)
                }
            }
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            `is`?.close()
            os?.close()
        }
        return result
    }


    /**
     * 通过MediaStore保存，兼容AndroidQ，保存成功自动添加到相册数据库，无需再发送广播告诉系统插入相册。
     *
     * @param context      context
     * @param sourceFile   源文件
     * @param saveFileName 保存的文件名
     * @param saveDirName  picture子目录,可以
     * @return 成功或者失败
     */
    private fun saveImageWithAndroidQ(context: Context,
                                      sourceFile: File,
                                      saveFileName: String?,
                                      saveDirName: String = ""): Boolean {

        // 以下api需要sdk>=Q
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return false
        }

        val values = ContentValues()
//        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image")   // 可忽略
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
//        values.put(MediaStore.Images.Media.TITLE, "Image.png")    // 可忽略
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$saveDirName")
        val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val resolver: ContentResolver = context.contentResolver
        val insertUri = resolver.insert(external, values)
        var inputStream: BufferedInputStream? = null
        var os: OutputStream? = null
        var result = false
        try {
            inputStream = BufferedInputStream(FileInputStream(sourceFile))
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri)
            }
            if (os != null) {
                val buffer = ByteArray(1024 * 4)
                var len: Int
                while (inputStream.read(buffer).also { len = it } != -1) {
                    os.write(buffer, 0, len)
                }
                os.flush()
            }
            result = true
        } catch (e: IOException) {
            result = false
        } finally {
            os?.close()
            inputStream?.close()
        }
        return result
    }


    /**
     * 1. Android Q开始，针对外部公有目录的File API失效，当进行创建或者读取操作时无论是否开启权限都会
     *    回"Permission denied"异常信息。
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

                // 其实和下面的写法一样，就是内部做了个封装
//                 val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())

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