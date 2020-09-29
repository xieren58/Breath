package com.zkp.breath.component.activity.weight

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
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
                    scanMusic()
                    return
                }
            }
        }
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


}