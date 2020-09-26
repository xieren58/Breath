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
//        val externalMusicPath = PathUtils.getExternalMusicPath()
//        ToastUtils.showShort(externalMusicPath)
//        val file = File(externalMusicPath)
//        val listFiles = file.listFiles()
//        val fileExists = FileUtils.isFileExists(file)
//        val listFilesInDir = FileUtils.listFilesInDir(file)
//        ToastUtils.showShort("${listFilesInDir?.size},是否存在：${fileExists}}")
//
//        val s = PathUtils.getExternalStoragePath() + "/xiami/audios/"
//        val fileExists1 = FileUtils.isFileExists(file)


        val musicResolver: ContentResolver = contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor? = musicResolver.query(musicUri, null,
                null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {

            val titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumId = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val data = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val albumkey = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY)


            val columnIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
            val columnIndex1 = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)


            do {
                val thisId = musicCursor.getLong(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = musicCursor.getString(artistColumn)
                val thisalbumId = musicCursor.getLong(albumId)
                val thisdata = musicCursor.getString(data)
                val AlbumKey = musicCursor.getString(albumkey)
                val AlbumKey1 = musicCursor.getString(columnIndex)
                val AlbumKey2 = musicCursor.getString(columnIndex1)


                //Android Q 公有目录只能通过Content Uri + id的方式访问，以前的File路径全部无效，如果是Video，
                // 记得换成MediaStore.Videos
//                var path = ""
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    path = MediaStore.Audio.Media
//                            .EXTERNAL_CONTENT_URI
//                            .buildUpon()
//                            .appendPath(thisalbumId.toString())
//                            .build()
//                            .toString()
//                }

                Log.i("xxxx", "thisId:$thisId,thisTitle:$thisTitle,thisArtist:$thisArtist,thisalbumId:$thisalbumId,thisdata:$thisdata,AlbumKey:$AlbumKey")
//                Log.i("xxxx", "thisId:$thisId,thisTitle:$thisTitle,thisArtist:$thisArtist,thisalbumId:$thisalbumId,path:$data")

            } while (musicCursor.moveToNext())

        }
    }


}