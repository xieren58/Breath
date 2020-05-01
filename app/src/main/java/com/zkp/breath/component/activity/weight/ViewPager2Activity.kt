package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.zkp.breath.adpter.ViewPagerAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityViewpager2Binding


/**
 * https://zhuanlan.zhihu.com/p/97511079?from_voters_page=true
 */
class ViewPager2Activity : BaseActivity() {

    lateinit var binding: ActivityViewpager2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewpager2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val arrayListOf = arrayListOf("ViewPager2_A",
                "ViewPager2_B", "ViewPager2_C", "ViewPager2_D", "ViewPager2_E",
                "ViewPager2_F", "ViewPager2_G", "ViewPager2_H", "ViewPager2_I"
        )

        val viewpager2 = binding.viewpager2
        // 设置方向
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val viewPagerAdapter = ViewPagerAdapter(arrayListOf)
        // 设置适配器
        viewpager2.adapter = viewPagerAdapter
        /**
         * 找不出规律
         */
        viewpager2.offscreenPageLimit = 1
        // 注册监听器
        viewpager2.registerOnPageChangeCallback(onPageChangeCallback)

        // 获取当前页面
        val currentItem = viewpager2.currentItem
        // 设置当前页面
        viewpager2.currentItem = 0
        // 获取当前的滚动状态
        val scrollState = viewpager2.scrollState
    }

    val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

}