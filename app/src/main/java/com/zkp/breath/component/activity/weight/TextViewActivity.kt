package com.zkp.breath.component.activity.weight

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.TextView
import com.blankj.utilcode.util.ResourceUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tv.*
import me.jessyan.autosize.utils.AutoSizeUtils


class TextViewActivity : BaseActivity(R.layout.activity_tv) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compoundDrawables()
        textGradient(tv)
    }

    /**
     * 使用场景：TextView没有设置背景的情况下适合，但如果要调整图片和文字的间距只能通过设置TextView的宽高进行处理，
     * 可以避免写多一个ImageView控件。
     */
    private fun compoundDrawables() {
        val rightDrawable = ResourceUtils.getDrawable(R.drawable.shape_r18_a0_ff8844ff_ff4488ff_rect)
        // 设置图片的宽高
        rightDrawable.setBounds(0, 0,
                AutoSizeUtils.dp2px(this, 50f),
                AutoSizeUtils.dp2px(this, 10f))
        // 设置图片的位置
        tv.setCompoundDrawables(null, null, null, rightDrawable)
    }


    /**
     * 为文字设置渐变色
     */
    private fun textGradient(textView: TextView) {
        val endX = textView.paint.textSize * textView.text.length
        val linearGradient = LinearGradient(
                0f, 0f, endX, 0f,   // 从左到右
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#FFFF5722"),
                Shader.TileMode.CLAMP
        )
        // 获取画笔后设置shader属性
        textView.paint.shader = linearGradient
        textView.invalidate()
    }

}