package com.zkp.breath.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 仪表盘
 */
class DashBoardView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)    // 抗锯齿
    private val paintDash = Paint(Paint.ANTI_ALIAS_FLAG)    // 抗锯齿
    private val paintPointer = Paint(Paint.ANTI_ALIAS_FLAG)    // 抗锯齿
    private val dash = Path()   // 刻度
    private val path = Path()

    // 刻度的宽高
    private val DASH_WIDTH = AutoSizeUtils.dp2px(context, 2f).toFloat()
    private val DASH_HEIGHT = AutoSizeUtils.dp2px(context, 5f).toFloat()

    // 半径
    private val RADIUS = AutoSizeUtils.dp2px(context, 150f).toFloat()

    // 指针长度，要比半径下，才能显示在半径内
    private val LENGTH = AutoSizeUtils.dp2px(context, 120f).toFloat()

    // 开放的角度
    private val OPEN_ANGLE = 120f

    init {
        paint.strokeWidth = AutoSizeUtils.dp2px(context, 3f).toFloat()
        paint.style = Paint.Style.STROKE  // 空心

        // 设置刻度的宽高
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 1.要测量圆弧的长度从而指定刻度的位置，path有现成的api可以测量，所以使用path画弧
        // 2.addArc()方法会累加，所以需要调用reset()重置
        // 3.圆弧的大小会随着控件的size变化而变化，所以addArc()方法在onSizeChanged()中执行
        path.reset()
        path.addArc(width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS,
                height / 2f + RADIUS, 90 + OPEN_ANGLE / 2f, 360 - OPEN_ANGLE)
        // 参数2：不封口，这里画的是圆弧
        val pathMeasure = PathMeasure(path, false)
        // 圆弧的长度 - 20个刻度 = 间隔
        // 实际上，刻度多出一个，所以在计算的时候要先减去一个刻度的宽度
        val dashMargin = (pathMeasure.length - DASH_WIDTH) / 20f

        /**
         * 参数2：间隔多少画一次。
         * 参数3：提前量，其实就是起点的意思。
         * 参数4：有时间可以看看，不重要
         */
        val pathDashPathEffect = PathDashPathEffect(dash, dashMargin, 0f, PathDashPathEffect.Style.ROTATE)
        paintDash.pathEffect = pathDashPathEffect
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
        canvas.drawPath(path, paintDash)

//        canvas.drawLine(width / 2f, height / 2f,
//                width / 2f + LENGTH * ?, height / 2f + LENGTH * ?, paintPointer)
    }

}