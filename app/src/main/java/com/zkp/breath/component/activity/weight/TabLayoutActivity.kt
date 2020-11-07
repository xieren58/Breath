package com.zkp.breath.component.activity.weight

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.tabs.TabLayout
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab_layout.*
import me.jessyan.autosize.utils.AutoSizeUtils
import java.util.*


/**
 * 注意：
 * 1. Tab里面包含TabView（LinearLayout）和TabLayout等成员变量，Tab类相当于一个资源配置类，会生成一个TabView然后将数
 * 据赋值给TabView，TabView内部会有TextView和ImageView（默认tv在iv下面，两者都是水平居中），所以才能设置文字和icon,
 * 同时也能设置自定义view。
 * 2. TabView中的ImageView的宽高是指定的，可以通过代码动态设置。
 * 3. TabView中的ImageView和TextView的间距是指定的，可以通过代码动态设置。
 * 4. TabView推荐使用自定义view，灵活性更强。
 * 5. TabLayout去掉指示线：给tabIndicatorHeight属性设置0dp，或者给tabIndicatorColor属性设置透明，就不显示指示线了。
 */
class TabLayoutActivity : BaseActivity(R.layout.activity_tab_layout) {

    private val colors = intArrayOf(-0xff7a89, -0xfc560c, -0x27e4a0, -0x6800, -0xb350b0, -0x98c549)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
//        ripple()

        val tabTexts = arrayOf("三个字", "两个", "四个字啊", "一", "五个字哈哈", "六个字你看吧")
        val random = Random()
        for (item in tabTexts) {
            // 创建tab
            val tab = tab_layout.newTab()
            // 获取tabView
            val tabView = tab.view
            // 设置TabView背景，会导致指标不可见
//            tabView.setBackgroundColor(colors[random.nextInt(colors.size)])
            // 设置文本
            tab.text = item
            // 设置图片，但是不灵活，默认显示在文字上面。
            tab.setIcon(R.drawable.ic_wh_1_1)
            // 将tabView添加到TabLayout中
            tab_layout.addTab(tab)

            // xml中没有那么多属性，可以使用动态实现理想效果。（也可以使用自定义View实现）
//            customImageView(tabView)
//            customTextView(tabView)
        }

        tab_layout.addOnTabSelectedListener(onTabSelectedListener)
    }

    private fun ripple() {
        // 是否无边界波纹：true的波纹效果不只是在当前tabView显示，会扩展到左右两个tabView；false则只在当前tabView显示
        tab_layout.setUnboundedRipple(true)
        // 波纹颜色，注意如果设置了tabBackground，那么该资源如果是selector的话那么下面的方法不生效。
        tab_layout.setTabRippleColorResource(R.color.colorFFFF5722)
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            // 重复选中
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            // 取消选中
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 被选中
        }
    }

    /**
     * 代码动态指定理想效果，也可以使用自定义View实现。
     */
    private fun customTextView(tabView: TabLayout.TabView) {
        try {
            val childAt = tabView.getChildAt(1) as TextView
            val layoutParams = childAt.layoutParams as LinearLayout.LayoutParams
            layoutParams.bottomMargin = AutoSizeUtils.dp2px(childAt.context, 20f)
        } catch (e: Exception) {
        }
    }

    /**
     * 代码动态指定理想效果，也可以使用自定义View实现。
     */
    private fun customImageView(tabView: TabLayout.TabView) {
        try {// tabView的0角标是ImageView，设置icon的。
            val childAt = tabView.getChildAt(0) as ImageView
            val layoutParams = childAt.layoutParams as LinearLayout.LayoutParams
            // 一般情况下可以使用tabPaddingBottom，tabPaddingEnd，tabPaddingStart，tabPaddingTop属性达到效果
            // 也可以使用以下方式进行设置。
            layoutParams.bottomMargin = AutoSizeUtils.dp2px(childAt.context, 100f)
            layoutParams.topMargin = AutoSizeUtils.dp2px(childAt.context, 20f)
            // 因为内部指定了宽高大小，但一般都不是我们想要的大小，使用下面的方式进行指定宽高。
            layoutParams.width = AutoSizeUtils.dp2px(childAt.context, 40f)
            layoutParams.height = AutoSizeUtils.dp2px(childAt.context, 40f)
        } catch (e: Exception) {
        }
    }

}