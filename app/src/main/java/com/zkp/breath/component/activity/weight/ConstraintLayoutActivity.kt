package com.zkp.breath.component.activity.weight

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.ConstraintVpAdapter
import com.zkp.breath.bean.ConstraintFunctionBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.zkplib.anim.ObjectAnimatorAssist
import kotlinx.android.synthetic.main.activity_constraint_layout.*
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 约束布局的demo
 *
 *
 * https://github.com/google/flexbox-layout
 *
 * https://juejin.cn/post/6844904199004618765
 * https://juejin.cn/post/6854573221312725000
 * https://juejin.cn/post/6844903872255754248
 * https://blog.csdn.net/weixin_34677811/article/details/90719945
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
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.iv_filter_btn_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.mock_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.space_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.flow_api_function))
        }

        // 设置方向
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.offscreenPageLimit = 2
        constraintVpAdapter = ConstraintVpAdapter(mutableListOf)
        constraintVpAdapter.addChildClickViewIds(R.id.layer, R.id.tv_flow_api)
        constraintVpAdapter.setOnItemChildClickListener(onItemChildClickListener)
        viewpager2.adapter = constraintVpAdapter
    }

    private val onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
        if (R.id.layer == view.id) {
            layerAnimator(view)
            return@OnItemChildClickListener
        }

        if (R.id.tv_flow_api == view.id) {
            constraintProperties(view)
            return@OnItemChildClickListener
        }
    }

    /**
     * 2.0以后，对属性的修改提供了流式API。
     *
     * 好像很多属性都没效果！！！！
     */
    private fun constraintProperties(view: View) {
        ConstraintProperties(view)
                .alpha(0.5f)
                .constrainWidth(AutoSizeUtils.dp2px(view.context, 200f))    // 无效果
                .constrainHeight(AutoSizeUtils.dp2px(view.context, 200f))   // 无效果
                .margin(ConstraintSet.TOP, AutoSizeUtils.dp2px(view.context, 100f)) // 无效果
                .apply()
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
                .setRepeatCount(2)
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