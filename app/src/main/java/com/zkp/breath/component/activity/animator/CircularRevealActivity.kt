package com.zkp.breath.component.activity.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityCircularRevealBinding

/**
 * 圆形揭露动画 （本例子是作用在view上）
 * ViewAnimationUtils.createCircularReveal()
 */
class CircularRevealActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCircularRevealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircularRevealBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnReveal.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val viewBg = binding.viewBg

        val startRadius: Float
        val endRadius: Float
        if (viewBg.visibility == View.VISIBLE) {
            //从有到无，即反揭露
            startRadius = viewBg.height.toFloat()
            endRadius = 0f
        } else {
            //从无到有，即揭露效果
            startRadius = 0f
            endRadius = viewBg.height.toFloat()
        }

        /**
         * ViewAnimationUtils的createCircularReveal()方法参数解释：
         * 1.作用目标
         * 2.3  从哪个xy点开始或者结束
         * 4. 开始半径（揭露动画一般都为0.反揭露都为目标view的宽或高）
         * 5. 结束半径（揭露都为目标view的宽或高，反揭露动画一般都为0.）
         */
        val animReveal = ViewAnimationUtils.createCircularReveal(viewBg,
                viewBg.width / 2,
                viewBg.height / 2,
                startRadius,
                endRadius
        )

        //构建好了揭露动画对象，开始设置动画的一些属性和相关监听
        animReveal.duration = 3000
        animReveal.interpolator = LinearInterpolator()
        animReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                viewBg.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (startRadius != 0f) {
                    viewBg.visibility = View.INVISIBLE
                    binding.btnReveal.text = "圆形揭露动画"
                } else {
                    viewBg.visibility = View.VISIBLE
                    binding.btnReveal.text = "圆形反揭露动画"
                }
            }
        })
        animReveal.start()

    }
}