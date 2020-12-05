package com.zkp.breath.component.activity.weight

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.zkp.breath.R
import com.zkp.breath.adpter.MotionVpAdapter
import com.zkp.breath.bean.MotionFunctionBean
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_motion.*

/**
 * 运动布局的demo
 * https://juejin.im/post/6854573206653812743
 *
 * https://juejin.cn/post/6844903918598635534
 *
 * 1.根标签MotionScene有一个defaultDuration属性，表示所有未指定时间的动画的默认时间，默认为300毫秒。
 * 2.MotionScene根标签 必须包含Transition标签，可以有多个Transition标签。Transition标签是用来指定动画的开始和
 * 结束状态、任何中间状态以及触发动画的动作，可以理解为一个Transition标签对应一个动画。
 * 3.MotionScene标签可以包含ConstraintSet标签，这是可选的。ConstraintSet标签主要为Transition标签提供起始和
 * 结束状态的位置和属性。而ConstraintSet标签必须包含一个或多个Constraint子标签。Constraint标签用来定义布局中
 * 某个View在动画中某个状态下位置。（通过ConstraintLayout的相关属性来约束）
 *
 * doc：
 * MotionLayout的构成.png
 * KeyPosition属性.png
 * KeyAttribute属性.png
 */
class MotionLayoutActivity : BaseActivity(R.layout.activity_motion) {

    private lateinit var constraintVpAdapter: MotionVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        val mutableListOf: MutableList<MotionFunctionBean> = mutableListOf()
        mutableListOf.run {
            mutableListOf.add(MotionFunctionBean(MotionFunctionBean.base_function))
            mutableListOf.add(MotionFunctionBean(MotionFunctionBean.coordinator_function))
        }

        // 设置方向
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        constraintVpAdapter = MotionVpAdapter(mutableListOf)
        viewpager2.adapter = constraintVpAdapter
    }

}