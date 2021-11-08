package com.zkp.breath.utils

import android.media.MediaMetadataRetriever

/**
 * 获取指定路径视频的时长
 */
fun getVideoDuration(videoPath: String?): Long {
    return try {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(videoPath)
        mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}
