package com.zkp.breath.component.activity

import android.animation.Animator
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityLayoutTransitionBinding
import kotlinx.android.synthetic.main.activity_layout_transition.*
import kotlinx.android.synthetic.main.activity_layout_transition.view.*

/**
 *
 * LayoutTransition.APPEARING  作用新添加view的
 * LayoutTransition.DISAPPEARING 作用新移除的view
 * LayoutTransition.CHANGE_APPEARING   作用除了新添加的view之外的view
 * LayoutTransition.CHANGE_DISAPPEARING 作用除了新移除的view之外的view
 *
 * 先使用PropertyValuesHolder执行动画类型，再用ObjectAnimator去包裹。因为上面有些类型如果不用PropertyValuesHolder
 * 先去指定而直接使用ObjectAnimator是不生效的。
 */
class LayoutTransitionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLayoutTransitionBinding
    private var layoutTransition: LayoutTransition? = null
    private var flag = false
    private var view: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutTransitionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btn.setOnClickListener(this)

        layoutTransition = LayoutTransition()
        // 设置不同类型的动画时长
        layoutTransition?.setDuration(LayoutTransition.APPEARING, 100)
        layoutTransition?.setDuration(LayoutTransition.DISAPPEARING, 100)
        layoutTransition?.setDuration(LayoutTransition.CHANGE_APPEARING, 100)
        layoutTransition?.setDuration(LayoutTransition.CHANGE_DISAPPEARING, 100)
        // 设置不同类型开始延时时长
        layoutTransition?.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0)
        layoutTransition?.setStartDelay(LayoutTransition.APPEARING, 0)
        layoutTransition?.setStartDelay(LayoutTransition.DISAPPEARING, 0)
        layoutTransition?.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0)
        // 设置动画
        layoutTransition?.setAnimator(LayoutTransition.APPEARING, getInAnim(binding.rlt))
        layoutTransition?.setAnimator(LayoutTransition.DISAPPEARING, getOutAnim(binding.rlt))
        // 为viewgroup设置layoutTransition
        binding.rlt.layoutTransition = layoutTransition
    }

    private fun getInAnim(v: View?): Animator? {
        val trX = PropertyValuesHolder.ofFloat("translationX", 100f, 0f)
        val trY = PropertyValuesHolder.ofFloat("translationY", 0f, 0f)
        val trAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        return ObjectAnimator.ofPropertyValuesHolder(v, trY, trAlpha, trX)
    }

    private fun getOutAnim(v: View?): Animator? {
        val trY2 = PropertyValuesHolder.ofFloat("translationY", 0f, -100f)
        val trX = PropertyValuesHolder.ofFloat("translationX", 0f, 0f)
        val trAlpha2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f)
        return ObjectAnimator.ofPropertyValuesHolder(v, trY2, trAlpha2, trX)
    }

    override fun onClick(v: View?) {
        if (!flag) {
            view = View(this)
            view?.setBackgroundColor(Color.YELLOW)
            val layoutParams = RelativeLayout.LayoutParams(300, 300)
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            binding.rlt.addView(view, layoutParams)
            flag = true
        } else {
            binding.rlt.removeView(view)
            flag = false
        }
    }
}