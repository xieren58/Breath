package com.zkp.breath.component.activity.weight

import android.os.Bundle
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab_layout.*
import java.util.*


/**
 *
 * Tab里面包含TabView（LinearLayout）和TabLayout等成员变量，Tab类相当于一个资源配置类，会生成一个TabView然后将数
 * 据赋值给TabView，TabView内部会有TextView和ImageView，所以才能设置文字和icon,同时也能设置自定义view。
 *
 *
 *
 *
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
            // 设置TabView背景，会导致指标不可见
//            tabView.setBackgroundColor(colors[random.nextInt(colors.size)])
            // 设置文本
            tab.text = item
            // 设置图片，但是不灵活，默认显示在文字上面。
            tab.setIcon(R.drawable.block_canary_icon)
            tab_layout.addTab(tab)
        }
    }

}