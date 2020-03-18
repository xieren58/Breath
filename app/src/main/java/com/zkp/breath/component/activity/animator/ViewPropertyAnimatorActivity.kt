package com.zkp.breath.component.activity.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityViewPropertyAnimatorBinding

/**
 * ViewPropertyAnimator,即提供对view的便捷属性动画
 */
class ViewPropertyAnimatorActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityViewPropertyAnimatorBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPropertyAnimatorBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!flag) {
            binding.rlt.animate()
                    .alpha(0f)
                    .setDuration(1000L)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.rlt.visibility = View.GONE
                            flag = true
                        }
                    })
        } else {
            binding.rlt.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                        .alpha(1f)
                        .setDuration(1000L)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                flag = false
                            }
                        })
            }
        }
    }
}