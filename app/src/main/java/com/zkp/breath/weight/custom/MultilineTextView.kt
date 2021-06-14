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
 * 多行绘制文字的demo
 *
 * 如果你需要进行多行文字的绘制，并且对文字的排列和样式没有太复杂的花式要求，那么使用 StaticLayout 就好。
 */
class MultilineTextView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    // lorem ipsum
    val text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor" +
            " congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus " +
            "malesuada libero, sit amet commodo magna eros quis urna. Nunc viverra imperdiet enim." +
            " Fusce est. Vivamus a tellus. Pellentesque habitant morbi tristique senectus et netus" +
            " et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci." +
            " Aenean nec lorem."

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = AutoSizeUtils.dp2px(context, 16f).toFloat()
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = AutoSizeUtils.dp2px(context, 16f).toFloat()
    }

    val dp150 = AutoSizeUtils.dp2px(context, 150f)
    val dp50 = AutoSizeUtils.dp2px(context, 50f)
    val statusBarHeight = BarUtils.getStatusBarHeight()

    private val bitmap = getAvatar(dp150, R.drawable.ic_wh_1_1)
    private val fontMetrics = Paint.FontMetrics()
    private val measureWidth = floatArrayOf(0f)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        staticLayoutMethod(canvas)

        breakText(canvas)
    }

    /**
     * 根据需求绘制多行的demo
     */
    private fun breakText(canvas: Canvas) {
        canvas.drawBitmap(bitmap, (width - dp150).toFloat(), statusBarHeight.toFloat(), paint)
        paint.getFontMetrics(fontMetrics)

        var start = 0
        var count = 0
        var verticalOffset = -fontMetrics.top + statusBarHeight  // 默认文字是在指定的y轴上绘制，如果没有偏移量，那么在y轴为0上面绘制的内容将看不见
        var maxWidth: Float
        while (start < text.length) {
            if (verticalOffset + fontMetrics.bottom < statusBarHeight ||
                    verticalOffset + fontMetrics.top > statusBarHeight + bitmap.height) {
                // 文字底部比小于图片的顶部 或者 文字的顶部大于图片的底部
                maxWidth = width.toFloat()
            } else {
                // 文字和图片有所交汇
                maxWidth = (width - dp150).toFloat()
            }

            /**
             * 在给出宽度上限的前提下测量文字的宽度.如果文字的宽度超出了上限，那么在临近超限的位置截断文字。
             * 参数1：需要测量的文字内容
             * 参数2：是否往前测量，一般都为true
             * 参数3：宽度上限
             * 参数4：存放测量结果。测量完成后会把截取的文字宽度（如果宽度没有超限，则为文字总宽度）赋值给 measuredWidth[0]
             *
             * 返回值: 截取的文字个数（如果宽度没有超限，则是文字的总个数）
             */
            count = paint.breakText(text, start, text.length, true, maxWidth, measureWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }

    /**
     * StaticLayout的示例
     */
    private fun staticLayoutMethod(canvas: Canvas) {
        /**
         * 参数3：是文字区域的宽度，文字到达这个宽度后就会自动换行。一般都是view的宽度
         * 参数4：文字的对齐方式
         * 参数5: 相对行间距，相对字体大小，1.5f表示行间距为1.5倍的字体高度。一般为1
         * 参数6：在基础行距上添加多少。一般为0
         * 参数7：指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。一般是false
         */
        val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
        staticLayout.draw(canvas)
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