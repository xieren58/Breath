package com.zkp.breath.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.airbnb.lottie.parser.ColorParser
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.utils.AutoSizeUtils
import kotlin.math.cos
import kotlin.math.sin

/**
 * 饼图
 */
class PieView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    // 半径
    private val radius = AutoSizeUtils.dp2px(context, 150f).toFloat()

    // 偏移长度
    private val offset = AutoSizeUtils.dp2px(context, 20f).toFloat()

    // 那个扇形进行偏移
    private val offsetIndex = 0

    // 扇形的角度
    private val angles = floatArrayOf(60f, 90f, 150f, 60f)

    // 扇形的颜色
    private val colors = listOf(
            Color.parseColor("#FFD81B60"),
            Color.parseColor("#FF3F51B5"),
            Color.parseColor("#FFFF5722"),
            Color.parseColor("#FFFFEB3B"))

    // 抗锯齿
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 开始角度
        var startAngle = 0f
        for ((index, angle) in angles.withIndex()) {
            paint.color = colors[index]
            if (index == offsetIndex) {
                canvas.save()
                /**
                 * 偏移出来的扇形间距其它扇形是一样大
                 * 1.sweep角度的一半，角度 = sweep / 2。
                 * 2.知道角度和偏移长度（斜边），构建直角三角形，
                 * 3.x（临边） = cos角度 * 斜边          【cos 斜边和邻边构成的）角度 = 邻边 / 斜边】
                 * 4.y（对边） = sin角度 * 斜边。【sin（斜边和邻边构成的）角度 = 对边 / 斜边】
                 */
                // 在即将绘制的地方的基础上进行偏移。
                canvas.translate(
                        (offset * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat()),
                        (offset * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat())
                )
            }
            canvas.drawArc(width / 2f - radius,
                    height / 2f - radius,
                    width / 2f + radius,
                    height / 2f + radius,
                    startAngle, angle, true, paint)
            // 累加以此得到下一个扇形的起始点
            startAngle += angle
            if (index == offsetIndex) canvas.restore()
        }
    }

}