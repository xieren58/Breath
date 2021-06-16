package com.zkp.breath.weight.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ScreenUtils
import com.zkp.breath.R
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 范围裁切和几何变化
 *
 *  注意：
 * 1. 一般来说，canvas和view的坐标系是重叠的。
 * 2. canvas的scale，其实就是放大或者缩小像素点（格子），所以铺在这些格子上的图像也就放大或者缩小了。
 */
class CameraView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    val bitmapSize = AutoSizeUtils.dp2px(context, 200f).toFloat()
    val bitmapPadding = AutoSizeUtils.dp2px(context, 100f).toFloat()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(bitmapSize.toInt(), R.drawable.ic_wh_1_1)
    private val clipped = Path().apply {
        addOval(bitmapPadding, bitmapPadding, bitmapPadding + bitmapSize,
                bitmapPadding + bitmapSize, Path.Direction.CCW)
    }

    /**
     * 三维坐标系：x轴往右是正，y轴往上是正，z轴往屏幕内部是正。
     * camera是在屏幕外面，是虚拟的，并不存在。
     *
     * 实际上使用camera是将东西绘制在canvas上，绘制完成后使用camera往背后的view上面做的投影，就是最终的绘制内容。
     * 默认情况下canvas和view的坐标系是重叠的。
     */
    private val camera = Camera().apply {
        rotate(30f, 0f, 0f)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        clip(canvas)

        // 将camera的效果应用到canvas
        camera.applyToCanvas(canvas)
        canvas.drawBitmap(bitmap, bitmapPadding, bitmapPadding, paint)
    }

    /**
     * clipPath的demo
     */
    private fun clip(canvas: Canvas) {
        /**
         * 裁剪指定区域，裁剪后周围是有锯齿的，注意这不是bug，是特性。
         * 而Xfermode没有锯齿是因为它把锯齿部分给虚化了（就是使用透明度，以及添加或者减少像素）。
         */
        canvas.clipPath(clipped)
        canvas.drawBitmap(bitmap, bitmapPadding, bitmapPadding, paint)
    }

    fun getAvatar(width: Int, id: Int): Bitmap {
        val options = BitmapFactory.Options()
        // 开启只读宽高，不读像素
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, id, options)
        options.inJustDecodeBounds = false
        // 设置图片的原始宽高和目标宽高
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        // 获取和目标宽高配置一样的图片
        return BitmapFactory.decodeResource(resources, id, options)
    }


}