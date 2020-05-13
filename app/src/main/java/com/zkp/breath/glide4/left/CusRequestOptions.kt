package com.zkp.breath.glide4.left

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zkp.breath.R


val sharedOptions: RequestOptions
    get() = RequestOptions()
            .placeholder(R.drawable.block_canary_icon)
            .error(R.drawable.block_canary_icon)
            // 允许用户指示null为可接受的正常情况，避免将null作为错误处理
            .fallback(R.drawable.block_canary_icon)
            .centerCrop()
            .priority(Priority.NORMAL)
            .diskCacheStrategy(DiskCacheStrategy.ALL)


val sharedOptions1: RequestOptions
    get() = RequestOptions()
            .placeholder(ColorDrawable(Color.BLUE))
            .error(ColorDrawable(Color.BLUE))
            // 允许用户指示null为可接受的正常情况，避免将null作为错误处理
            .fallback(ColorDrawable(Color.BLUE))
            .centerCrop()
            .priority(Priority.NORMAL)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

