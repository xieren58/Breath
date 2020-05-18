package com.zkp.breath.component.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.zkp.breath.adpter.GlideAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityGlideBinding
import com.zkp.breath.glide4.left.GlideCacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * 变化：对请求的资源进行裁剪，拉伸等操作。不会作用域占位图，错误图，后备图。
 * 1.内置变换：RequestOptions内配置或者直接配置都可以(其实内部就是调用了使用transform()方法)：
 *      fitCenter()：居中缩放图片的最长边和控件对应的一边等长后停止缩放。保证图片完全显示
 *      centerCrop()：
 *      centerInside()：
 *      circleCrop()：
 * 2.多重变换：使用transform（），参数为可变参数，只能直接配置，多个变化效果叠加(存在多个变化的时候内部调用MultiTransformation)。
 *     FitCenter(),
 *     CircleCrop(): 变化为圆图。
 *     CenterCrop(),
 *     CenterInside()
 *     GranularRoundedCorners（）：定制圆角变化，可以对四个角进行不同定制
 *     RoundedCorners()：圆角变化，统一对四个角进行定制。
 *     Rotate(): 旋转变化
 * 3.定制变化：一般继承BitmapTransformation，BitmapTransformation 为我们处理了一些基础的东西，可以参考BitmapTransformation
 *   的子类。
 * 4.如果ImageView的ScaleType设置了value也会发作用，glide内部会去判断imageview的scaletype然后匹配对应的内置变化规则。
 *
 * 过渡：调用transition()，直接配置。定义了占位符到新加载的图片，或从缩略图到全尺寸图像的过渡效果。glide提供了一些
 * 内置过渡效果，这些过渡效果当从内存缓存中加载出来则不会执行（因为从内存中加载出来贼快，不需要过渡），其余从本地或远程
 * 加载资源则会执行过渡。glide提示动画的代价比较大且通常比解码还要耗时，滥用动画可能会让图片的加载显得缓慢和卡顿，为了
 * 提升性能在列表控件RecyclerView加载的时候避免开启动画。作为替代方案，请使用预加载，而recycleview其实就有该功能。
 * 如果当占位图比实际加载的图片要大或者图片部分透明，那么没有开启加交叉淡入则还是能看到占位图，这时候可以选择开启。
 *
 *
 *  清理缓存注意：glide内部会检查是否在要求的线程中执行相关的清理api
 *   //清空内存缓存，要求在主线程中执行
 *   Glide.get(this).clearMemory()
 *   //清空磁盘缓存，要求在后台线程中执行
 *   Glide.get(this).clearDiskCache()
 *
 */
class GlideActivity : BaseActivity() {

    private lateinit var binding: ActivityGlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlideBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val arrayListOf = arrayListOf(
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/b01eb218背景5.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/49d209e3背景2.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/f6620121背景7.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/5da173a0背景10.png"
        )

        val viewpager2 = binding.vp2
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val viewPagerAdapter = GlideAdapter(arrayListOf)
        viewpager2.adapter = viewPagerAdapter
        viewpager2.offscreenPageLimit = 1

        val cacheSize = GlideCacheUtil.getInstance().getCacheSize(this)
        LogUtils.i("获取应用内部缓存大小：$cacheSize")

        onpreload()
//        downloadThenCopy()
//        downloadBitmap()
    }

    // 推荐！！！！
    // 预加载，提前加载图片。测试的时候先清除内存缓存和本地缓存，然后开启这个方法去加载，关闭网络再去加载这条url后显示出来，如果能显示则证明有效。
    private fun onpreload() {
        Glide.with(this)
                .load("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png")
                // 这个监听只是为了查看数据是否下载完成
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        // 如果return true，则不会再回调Target的onLoadFailed（也就是不再往下传递）。
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        // 如果return true，则不会再回调Target的onResourceReady（也就是不再往下传递），imageView也就不会显示加载到的图片了。
                        return true
                    }
                })
                .preload()
    }

    // 下载完的file是存放在glide指定的路径下，然后使用copy文件的方法移植到你的目标路径。
    fun downloadThenCopy() {
        GlobalScope.launch(Dispatchers.IO) {
            val submit = Glide.with(this@GlideActivity)
                    .asFile()
                    .load("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png")
                    .listener(object : RequestListener<File> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            val s = PathUtils.getExternalStoragePath() + "/glideBitmap1.png"
                            FileUtils.copy(resource.toString(), s)
                            LogUtils.i("resource路径:" + resource.toString())
                            return true
                        }

                    })
                    //加载原图大小
                    //也可以指定大小，内部会以最短边计算比例后下载。
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        }
    }

    // 下载bitmap的方法
    fun downloadBitmap() {
        GlobalScope.launch(Dispatchers.IO) {
            Glide.with(this@GlideActivity)
                    .asBitmap()
                    .load("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png")
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            LogUtils.i()
                            return false
                        }

                        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            val bitmap2Bytes = ImageUtils.bitmap2Bytes(resource)
                            val path = PathUtils.getExternalStoragePath() + "/glideBitmap.png"
                            val writeFileFromBytesByStream = FileIOUtils.writeFileFromBytesByStream(path, bitmap2Bytes)
                            LogUtils.i("是否写入成功:$writeFileFromBytesByStream")
                            return true
                        }

                    })
                    //加载原图大小
                    //也可以指定大小，内部会以最短边计算比例后下载。
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        }
    }

}