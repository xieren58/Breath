package com.zkp.breath.component.activity.weight

import android.os.Bundle
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab_layout.*
import java.util.*


/**
 * TabLayout去掉指示线：给tabIndicatorHeight属性设置0dp，或者给tabIndicatorColor属性设置透明，就不显示指示线了。
 *
 *
 * ？？？下划线的宽度怎么设置
 */
class TabLayoutActivity : BaseActivity(R.layout.activity_tab_layout) {

    private val colors = intArrayOf(-0xff7a89, -0xfc560c, -0x27e4a0, -0x6800, -0xb350b0, -0x98c549)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val tabTexts = arrayOf("三个字", "两个", "四个字啊", "一", "五个字哈哈", "六个字你看吧")

        val random = Random()
        for (item in tabTexts) {
            val tab = tab_layout.newTab()
            val tabView = tab.view
//            tabView.setBackgroundColor(colors[random.nextInt(colors.size)])
            tab.text = item
            tab_layout.addTab(tab)
        }
    }

}