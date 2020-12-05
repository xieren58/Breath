package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scroll_view.*

class ScrollViewActivity : BaseActivity(R.layout.activity_scroll_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        scroll_view.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            var height = 0
            val white = ColorUtils.getColor(R.color.colorFFFFFFFF)
            val black = ColorUtils.getColor(R.color.colorFF000000)
            var type = -1   // 避免多次调用


            /**
             * 模拟顶部栏渐变效果。
             *
             * 1.获取顶部栏高度，以这个高度为阀值
             * 2.当前滚动为0，则设置原始颜色
             * 3.当滚动<=阀值，值渐变
             * 4.当>阀值，则设置结束颜色
             */
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (height == 0) {
                    height = rlt_top_bar.height
                }

                when {
                    scrollY == 0 -> {
                        if (type != 0) {
                            // 原始颜色
                            rlt_top_bar.setBackgroundColor(0)
                            tv_title.setTextColor(white)
                            type = 0
                        }
                    }
                    scrollY <= height -> {
                        // 渐变颜色
                        val fl = scrollY * 1f / height
                        rlt_top_bar.setBackgroundColor(ColorUtils.setAlphaComponent(white, fl))

                        val blueComponent = ColorUtils.setBlueComponent(white, 1f - fl)
                        val redComponent = ColorUtils.setRedComponent(blueComponent, 1f - fl)
                        val greenComponent = ColorUtils.setGreenComponent(redComponent, 1f - fl)
                        tv_title.setTextColor(greenComponent)
                        type = -1
                    }
                    else -> {
                        if (type != 1) {
                            // 结束颜色
                            rlt_top_bar.setBackgroundColor(ColorUtils.setAlphaComponent(white, 1f))
                            tv_title.setTextColor(black)
                            type = 1
                        }
                    }
                }
            }
        })
    }
}