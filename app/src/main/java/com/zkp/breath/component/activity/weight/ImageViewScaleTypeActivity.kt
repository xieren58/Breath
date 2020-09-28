package com.zkp.breath.component.activity.weight

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityIvScaleTypeBinding


class ImageViewScaleTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityIvScaleTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIvScaleTypeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvScale.setOnClickListener(onClick)
    }

    private val onClick: ClickUtils.OnDebouncingClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            when (v) {
                binding.tvScale -> {
                    xx()
//                    yyy()


//                    //通过系统的文件浏览器选择一个文件
//                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//                    //筛选，只显示可以“打开”的结果，如文件(而不是联系人或时区列表)
//                    intent.addCategory(Intent.CATEGORY_OPENABLE)
//                    //过滤只显示图像类型文件
//                    intent.type = "image/*"
//                    startActivityForResult(intent, 123)


//                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//                    startActivityForResult(intent, 123)

                    return
                }
            }
        }
    }

    private fun xx() {
        val musicResolver: ContentResolver = contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        //Android Q 公有目录只能通过Content Uri + id的方式访问，以前的File路径全部无效，如果是Video，
        // 记得换成MediaStore.Videos
//        var path = ""
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//                    .buildUpon()
//                    .appendPath(thisalbumId.toString())
//                    .build()
//                    .toString()
//        }


//        val musicUri: Uri = Uri.parse("content://media/external_primary/file/73")
        val musicCursor: Cursor? = musicResolver.query(musicUri, null,
                null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            // 标题
            val titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            // id
            val idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            // 创建音频文件的艺术家（如果有），即作者
            val artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            // 音频文件来自的专辑的ID（如果有）
            val albumId = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            // 磁盘上媒体项的绝对文件系统路径
            val data = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            // 音频文件的录制年份（如果有）
            val columnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
            // 时长
            val columnIndex1 = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            do {
                val thisId = musicCursor.getLong(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = try {
                    musicCursor.getString(artistColumn)
                } catch (e: Exception) {
                    ""
                }
                val thisalbumId = try {
                    musicCursor.getLong(albumId)
                } catch (e: Exception) {
                    0L
                }
                val thisdata = try {
                    musicCursor.getString(data)
                } catch (e: Exception) {
                    ""
                }
                val year = try {
                    musicCursor.getString(columnIndex)
                } catch (e: Exception) {
                    ""
                }
                val duration = try {
                    musicCursor.getString(columnIndex1)
                } catch (e: Exception) {
                    ""
                }
                Log.i("xxxx", "thisId:$thisId,thisTitle:$thisTitle,thisArtist:$thisArtist," +
                        "thisalbumId:$thisalbumId,thisdata:$thisdata,year:$year,duration:$duration")
            } while (musicCursor.moveToNext())
        }
        musicCursor?.close()
    }

    private val MIMETable = arrayOf<kotlin.Array<kotlin.String>>(
            arrayOf<kotlin.String>("mp3", "audio/x-mpeg"))

    private fun yyy() {


        var t = ""
        val type = "mp3" //要查找的文件后缀

        val resolver = contentResolver
        val uri = MediaStore.Files.getContentUri("external")
        for (i in MIMETable.indices) {
            if (type == MIMETable[i][0]) {
                t = MIMETable.get(i).get(1)
            }
        }

        val cursor = resolver.query(uri, /*arrayOf(MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED, MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.TITLE)*/null,
                MediaStore.Files.FileColumns.MIME_TYPE + " = '" + t + "'",
                null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val title = try {
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE))
                } catch (e: Exception) {
                    ""
                }
                val path = try {
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                } catch (e: Exception) {
                    ""
                }
                val time = try {
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED))
                } catch (e: Exception) {
                    -1
                }
                val size = try {
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE))
                } catch (e: Exception) {
                    -1
                }
                Log.i("xxxx", "title:$title,path:$path,time:$time," +
                        "size:$size")
            }
        }
        cursor!!.close()

    }


}