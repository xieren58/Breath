package com.zkp.breath.component.activity.weight

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.ConstraintVpAdapter
import com.zkp.breath.bean.ConstraintFunctionBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.zkplib.anim.ObjectAnimatorAssist
import kotlinx.android.synthetic.main.activity_constraint_layout.*

/**
 * 约束布局的demo
 *
 * https://juejin.cn/post/6844904199004618765
 */
class ConstraintLayoutActivity : BaseActivity(R.layout.activity_constraint_layout) {

    private lateinit var constraintVpAdapter: ConstraintVpAdapter

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
        constraintVpAdapter = ConstraintVpAdapter(mutableListOf)
        constraintVpAdapter.addChildClickViewIds(R.id.layer)
        constraintVpAdapter.setOnItemChildClickListener(onItemChildClickListener)
        viewpager2.adapter = constraintVpAdapter
    }

    private val onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
        if (R.id.layer == view.id) {
            layerAnimator(view)
        }
    }

    private lateinit var layerAnim: ObjectAnimator
    private fun layerAnimator(view: View) {
        if (::layerAnim.isInitialized) {
            layerAnim.start()
            return
        }
        layerAnim = ObjectAnimatorAssist.Builder()
                .setView(view)
                .setDuration(2000)
                .setAnimatorType(ObjectAnimatorAssist.ObjectAnimatorType.ROTATION)
                .setStartValue(0f)
                .setEndValue(360f)
                .setRepeatCount(5)
                .build()
                .startAnimator()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::layerAnim.isInitialized) {
            layerAnim.cancel()
        }
    }

}