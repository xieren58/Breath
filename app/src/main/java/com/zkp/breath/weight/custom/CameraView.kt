package com.zkp.breath.weight.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zkp.breath.R
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 范围裁切和几何变化
 * https://rengwuxian.com/ui-1-4/
 *
 *  注意：
 * 1. 一般来说，canvas和view的坐标系是重叠的。
 * 2. canvas的scale，其实就是放大或者缩小像素点（格子），所以铺在这些格子上的图像也就放大或者缩小了。
 * 3. camera有x，y，z坐标轴，y轴和view的y轴正反相反，轴心固定（x0，y0，z0）,一般要达到理想效果需要移动canvas到
 *    其轴心位置，可以实现三维效果
 */
class CameraView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    val bitmapSize = AutoSizeUtils.dp2px(context, 200f).toFloat()
    val bitmapPadding = AutoSizeUtils.dp2px(context, 100f).toFloat()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(bitmapSize.toInt(), R.drawable.ic_wh_1_1)
    private val clipped = Path().apply {
        addOval(
            bitmapPadding, bitmapPadding, bitmapPadding + bitmapSize,
            bitmapPadding + bitmapSize, Path.Direction.CCW
        )
    }

    /**
     * 三维坐标系：x轴往右是正，y轴往上是正，z轴往屏幕内部是正。
     * camera是在屏幕外面，是虚拟的，并不存在。
     *
     * 实际上使用camera是将东西绘制在canvas上，绘制完成后使用camera往背后的view上面做的投影，就是最终的绘制内容。
     * 默认情况下canvas和view的坐标系是重叠的。
     *
     * camera的轴心是不能改变的（x0，y0，z0），所以想要达到理想效果，只能对canvas进行调整
     */
    private val camera = Camera().apply {
        rotate(40f, 0f, 0f)
        /**
         * 设置 camera 位置，这个⽅法⼀般前两个参数都填 0 (没有想到使用的场景，主要是沿z轴)，第三个参数为负值(默认值-8)。
         * 第三个参数单位不是像素，是英寸（1英寸 ≈ 72像素 ），如果绘制的内容过大，当它翻转起来的时候，就有可能出现图像
         * 投影过大的「糊脸」效果。而且由于换算单位被写死成了 72 像素，而不是和设备 dpi 相关的，所以在像素越大的手机上，
         * 这种「糊脸」效果会越明显。
         *
         * 糊脸效果的原因：
         * 1. 绘制的内容过大
         * 2. 像素大的手机，那么物理尺寸一样的手机，camera离view的距离会越小（z轴），越近的话投影的到view上的内容就约大
         *
         * 解决：根据设备 dpi对camera的z轴位置做适配
         */
        setLocation(0f, 0f, -8f * resources.displayMetrics.density)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        clip(canvas)
//        camera(canvas)
        combination(canvas)
    }

    /**
     * clip和camera组合实现
     * 翻页效果(折叠效果)
     */
    private fun combination(canvas: Canvas) {
        // 上半部分
        canvas.save()
        canvas.translate((bitmapPadding + bitmapSize / 2), (bitmapPadding + bitmapSize / 2)) // 4
        canvas.clipRect(-bitmapSize / 2f, -bitmapSize / 2f, bitmapSize / 2f, 0f)
        canvas.translate(-(bitmapPadding + bitmapSize / 2), -(bitmapPadding + bitmapSize / 2)) //2
        canvas.drawBitmap(bitmap, bitmapPadding, bitmapPadding, paint)  // 1
        canvas.restore()

        // 下半部分
        canvas.save()
        canvas.translate((bitmapPadding + bitmapSize / 2), (bitmapPadding + bitmapSize / 2)) // 4
        camera.applyToCanvas(canvas) //3
        canvas.clipRect(-bitmapSize / 2f, 0f, bitmapSize / 2f, bitmapSize / 2f)
        canvas.translate(-(bitmapPadding + bitmapSize / 2), -(bitmapPadding + bitmapSize / 2)) //2
        canvas.drawBitmap(bitmap, bitmapPadding, bitmapPadding, paint)  // 1
        canvas.restore()
    }

    /**
     * 注意：camera在使用的时候要倒着想，倒着写。
     */
    private fun camera(canvas: Canvas) {
        canvas.translate(-(bitmapPadding + bitmapSize / 2), -(bitmapPadding + bitmapSize / 2)) // 4
        // 将camera的效果应用到canvas
        camera.applyToCanvas(canvas) //3
        // camera的轴心不可变，所以只能通过调整canvas达到理想效果
        canvas.translate(-(bitmapPadding + bitmapSize / 2), -(bitmapPadding + bitmapSize / 2)) //2
        canvas.drawBitmap(bitmap, bitmapPadding, bitmapPadding, paint)  // 1
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