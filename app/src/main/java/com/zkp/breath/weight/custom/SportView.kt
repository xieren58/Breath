package com.zkp.breath.weight.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.blankj.utilcode.util.ImageUtils
import com.zkp.breath.R
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * text绘制的demo
 *
 * 文字5条线：
 * 1. top：最高限制
 * 2. ascent:核心部分的顶（和内容无关，和内容的字体有关）
 * 3. baseline:基线
 * 4. descent:核心部分的底（和内容无关，和内容的字体有关）
 * 5. bottom:最低限制
 */
class SportView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    // 抗锯齿
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        /**
         * sp和像素密度有关，还和系统设置有关。
         * dp和像素密度有关。
         * 在这里应该选择dp，因为如果使用sp，那么在android系统中设置字体大小可能产生文字压过进度圈。
         * 注意并不是所有文字都需要sp，只有当需要大量的阅读的时候才需要，比如那种阅读场景。
         */
        textSize = AutoSizeUtils.dp2px(context, 100f).toFloat()
        /**
         * textAlign只作用于drawText（）的x轴，即参数2。
         *
         * drawText（）参数解释：
         * x轴：方法参数二为定点
         *   Paint.Align.LEFT：文字最左边和定点重叠
         *   Paint.Align.RIGHT：文字最右边和定点重叠
         *   Paint.Align.CENTER：文字中间和定点重叠
         * y轴：方法参数三为基线（baseline），文字摆放在基线上。
         */
        textAlign = Paint.Align.CENTER
        /**
         * font:res文件夹下的font文件夹，文件的后缀为ttf
         * typeface：如微软雅黑
         * font（字重）:如粗体/正常/细体/斜体的微软雅黑。每一个字体有多个字重（子集）
         */
//        typeface = ResourcesCompat.getFont(context, R.font.font)
        /**
         * （Fake：伪造的）假粗。不是使用粗体的字重来设置，而是使用细体的字重后android用自身的算法描粗了。
         */
//        setFakeBoldText
    }

    private val rect: Rect = Rect()
    private val fontMetrics = Paint.FontMetrics()

    private val px20 = AutoSizeUtils.dp2px(context, 20f).toFloat()
    private val radius = AutoSizeUtils.dp2px(context, 150f).toFloat()
    private val circleColor = Color.parseColor("#90a4ae")
    private val highlightColor = Color.parseColor("#ff4081")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制环
        paint.style = Paint.Style.STROKE
        paint.color = circleColor
        paint.strokeWidth = px20
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        // 绘制进度条
        paint.color = highlightColor
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
                width / 2f - radius,
                height / 2f - radius,
                width / 2f + radius,
                height / 2f + radius,
                -90f, 225f, false, paint)

        // 绘制文字
        paint.style = Paint.Style.FILL

        /**
         * 为了使绘制的文字在纵向居中，y轴只减去文字高度的二分之一
         */
//        staticContent(canvas)
        dynamicContent(canvas)
    }

    fun dynamicContent(canvas: Canvas) {
        /**
         * 动态计算，适合变化的内容的测量。
         * 获取到相对中间的区域，即核心文字的顶和底。
         */
        paint.getFontMetrics(fontMetrics)  // 和绘制的内容无关，只和字体有关
        canvas.drawText("abaq", width / 2f, height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f, paint)
    }


    fun staticContent(canvas: Canvas) {
        /**
         * 静态计算，适合不变的内容的测量。（如果以这种方式实时计算动态内容，那么内容会变得“上蹿下跳”）
         *
         * 获取指定文字的边界
         * 参数1：指定的文字
         * 参数2，3：指定参数1的范围
         * 参数4：存放文字测量的结果
         */
        paint.getTextBounds("abab", 0, "abab".length, rect)
        canvas.drawText("abab", width / 2f, height / 2f - (rect.top + rect.bottom) / 2f, paint)
    }

}