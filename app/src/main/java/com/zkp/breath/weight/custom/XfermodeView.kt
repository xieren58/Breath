package com.zkp.breath.weight.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.zkp.breath.R
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * Xfermode (美国人为了省事把 trans 叫成 X)
 */
class XfermodeView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    // 抗锯齿
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val px200 = AutoSizeUtils.dp2px(context, 200f).toFloat()
    private val px50 = AutoSizeUtils.dp2px(context, 50f).toFloat()
    private val px300 = AutoSizeUtils.dp2px(context, 300f).toFloat()
    private val px150 = AutoSizeUtils.dp2px(context, 150f).toFloat()
    private val px100 = AutoSizeUtils.dp2px(context, 100f).toFloat()
    private val px250 = AutoSizeUtils.dp2px(context, 250f).toFloat()

    private val circleBmp = Bitmap.createBitmap(px150.toInt(), px150.toInt(), Bitmap.Config.ARGB_8888)
    private val squareBmp = Bitmap.createBitmap(px150.toInt(), px150.toInt(), Bitmap.Config.ARGB_8888)

    private val imgPadding = AutoSizeUtils.dp2px(context, 20f).toFloat()
    private val imgWidth = AutoSizeUtils.dp2px(context, 200f).toFloat()

    // PorterDuff.Mode.SRC_IN，显示两个图案的交接处
    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    // 离屏缓冲的区域大小，这里和展示的图片大小一致
    private val bounds = RectF(0f, 0f, imgWidth, imgWidth)

    init {
        /**
         * 我们画的东西都是存放在指定的Bitmap上的。canvas只是起到一个中介的作用，它承载了画的方法（
         * 比如drawLine，drawCircle等） 会画线，画圆保存到bitmap中。没有画的区域为透明区域
         */
        val canvas = Canvas(circleBmp)  // 构造一个带有给定bitmap的画布，画东西到bitmap里面去。
        paint.setColor(Color.parseColor("#D81B60"))
        canvas.drawOval(px50, 0f, px150, px100, paint)

        paint.setColor(Color.parseColor("#2196F3"))
        canvas.setBitmap(squareBmp)
        canvas.drawRect(px100, px50, px200, px150, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        circleAvatat(canvas)
        testXfermode(canvas)
    }

    /**
     * 演示官方展示的xfermode效果
     *
     * 注意！！！！
     * 官方demo的方形和圆形图是存在透明区域的，如果我们只画圆形和方形那么得出的效果和官方demo是不一致的，
     * 所以我们也要构造类似的效果（使用bitmap实现）。
     */
    private fun testXfermode(canvas: Canvas) {
        val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val saveLayer = canvas.saveLayer(bounds, null)

        canvas.drawBitmap(circleBmp, px150, px150, paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(squareBmp, px150, px150, paint)
        paint.setXfermode(null) // 取消Xfermode

        canvas.restoreToCount(saveLayer)
    }

    /**
     * 实现圆形头像
     */
    private fun circleAvatat(canvas: Canvas) {
        /**
         * 开启离屏缓冲：单独开一块区域进行绘制，绘制完成关闭后把绘制的内容贴回原来的画布。
         * 在使用setXfermode的时候，不仅仅把注释1处所绘制的内容作为底板，这个类所处的n级父类也会作为底板，
         * 那么其实这时候的底板是完全实心的，所以使用PorterDuff.Mode.SRC_IN画出来是没有任何变化的，所以
         * 需要开启离屏缓冲保证没有其它底板（单独开出来的区域没有其它内容，只有你指定绘制的内容）。
         *
         * 注意：
         * 1. canvas.saveLayer()的参数1：离屏缓冲的区域大小，因为离屏缓冲很耗费资源，性能比较差，所以能多小就多小。
         * 2. canvas.saveLayer()的参数2：可以直接用null。
         * 3. saveLayer和restoreToCount一般来说要成对出现。
         */
        //
        val saveLayer = canvas.saveLayer(bounds, null)  // 开启离屏缓冲绘制所需内容
        canvas.drawOval(imgPadding, imgPadding, imgWidth, imgWidth, paint)  // 1
        paint.setXfermode(porterDuffXfermode)   // 2
        // 参数2，3表示相当于整个控件bitmap显示的起始点
        canvas.drawBitmap(
                ImageUtils.getBitmap(R.drawable.ic_wh_1_1, imgWidth.toInt(), imgWidth.toInt()),
                imgPadding, imgPadding, paint) // 3
        canvas.restoreToCount(saveLayer)    // 关闭离屏缓冲绘将所绘制内容贴回画布
    }

    fun getAvatar(width: Int, id: Int): Bitmap? {
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