package com.zkp.breath.component.activity.weight

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.zkp.breath.R
import com.zkp.breath.adpter.ConstraintVpAdapter
import com.zkp.breath.bean.ConstraintFunctionBean
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_constraint_layout.*

/**
 * 约束布局的demo
 */
class ConstraintLayoutActivity : BaseActivity(R.layout.activity_constraint_layout) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        val mutableListOf: MutableList<ConstraintFunctionBean> = mutableListOf()
        mutableListOf.run {
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.base_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.flow_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.layer_function))
        }

        // 设置方向
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.offscreenPageLimit = 2
        val viewPagerAdapter = ConstraintVpAdapter(mutableListOf)
        viewpager2.adapter = viewPagerAdapter
    }

}