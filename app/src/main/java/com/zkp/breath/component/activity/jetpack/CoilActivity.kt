package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import coil.load
import coil.transform.GrayscaleTransformation
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCoilBinding

/**
 * https://juejin.cn/post/6844904159527829518#heading-8
 *
 * 优势：纯kotlin，语法糖能代码更加简洁。
 *
 * 核心：ImageLoader，相当于GlideOptions（Glide），基本上所有的配置都在这个类进行
 */
class CoilActivity : BaseActivity() {

    private lateinit var binding: ActivityCoilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoilBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.iv.load("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png") {
            crossfade(true) // 交叉淡入
            placeholder(R.drawable.ic_yq)//占位图
//            transformations(CircleCropTransformation())//圆形变化，可传入多个
//            transformations(RoundedCornersTransformation(100f, 0f, 0f, 100f))//圆角变化
//            transformations(BlurTransformation(binding.iv.context))//高斯模糊变化
            transformations(GrayscaleTransformation())//灰度变化
        }

        //上面的请求基本等价于：
//        val imageLoader = Coil.imageLoader(this)// 获取默认的imageLoader
//        val request = ImageRequest.Builder(binding.iv.context)//请求配置
//            .data("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png")
//            .target(binding.iv)
//            .build()
//        imageLoader.enqueue(request)// 真正发起请求
    }

}