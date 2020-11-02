package com.zkp.breath.component.activity.weight

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityButtonBinding
import com.zkp.zkplib.anim.ObjectAnimatorAssist

/**
 * selector中的state_pressed="true"对于设置监听点击事件的TextView控件才有效，否则无效
 */
class ButtonActivity : BaseActivity() {

    private lateinit var binding: ActivityButtonBinding
    private val animatorSet1 = AnimatorSet()
    private val animatorSet2 = AnimatorSet()
    private val animatorSet3 = AnimatorSet()
    private val animatorSet4 = AnimatorSet()
    private val speed = 0.17f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.tv.setOnClickListener(onClick)
        // tv开启点击事件，这样selector中的state_pressed="true"才能生效
        binding.tvBtn.setOnClickListener(onClick)
    }

    val onClick = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            if (v == binding.tv) {
                binding.button.isEnabled = !binding.button.isEnabled
                binding.tvBtn.isEnabled = !binding.tvBtn.isEnabled
                rewardBannerAnimator()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun dynamicContentGiftAnimator(scrollX: Int) {

        // 准备好进入动画的x坐标
        binding.fltGiftBroadcast.translationX = ScreenUtils.getScreenWidth().toFloat()
        binding.tvGiftContent.translationX = 0f

        binding.hsvGift.setOnTouchListener { _, _ -> true }

        val animator1: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltGiftBroadcast,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.fltGiftBroadcast.x,
                0f)
        animator1.duration = 528L
        animator1.interpolator = LinearInterpolator()

        val contentShowTime = (scrollX / speed).toLong()

        val animator2: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.tvGiftContent,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.tvGiftContent.x,
                binding.tvGiftContent.x - scrollX)
        animator2.duration = contentShowTime
        animator2.startDelay = 528L + 1980L
        animator2.interpolator = LinearInterpolator()

        val animator3: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltGiftBroadcast,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                0f,
                -ScreenUtils.getScreenWidth().toFloat())
        animator3.duration = 990L
        animator3.startDelay = 528L + 1980L + 1650L + contentShowTime
        animator3.interpolator = LinearInterpolator()

        animatorSet1.playTogether(animator1, animator2, animator3)
        animatorSet1.start()
    }

    fun staticContentGiftAnimator() {
        // 准备好进入动画的x坐标
        binding.fltGiftBroadcast.translationX = ScreenUtils.getScreenWidth().toFloat()

        val animator1: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltGiftBroadcast,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.fltGiftBroadcast.x,
                0f)
        animator1.duration = 528L
        animator1.interpolator = LinearInterpolator()

        val animator2: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltGiftBroadcast,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                0f,
                -ScreenUtils.getScreenWidth().toFloat())
        animator2.duration = 990L
        animator2.startDelay = 528L + 2310L
        animator2.interpolator = LinearInterpolator()


        animatorSet2.playTogether(animator1, animator2)
        animatorSet2.start()
    }

    fun rewardBannerAnimator() {
        // 送礼
        binding.tvGiftRich.text = "卡卡罗特 给 "
        binding.tvGiftContent.text = "咖啡不加糖呀、天青色等烟雨、相机、小丑、面具、哈哈、呵呵、嘿嘿、嘻嘻火箭x1"

        // 文字长度是否超过边框宽度，是则进行滚动动画，否则进行不滚动动画
        SizeUtils.forceGetViewSize(binding.hsvGift, object : SizeUtils.OnGetSizeListener {
            override fun onGetSize(view: View?) {
                val scrollViewWidth = view?.width
                val tvContentWidth = SizeUtils.getMeasuredWidth(binding.tvGiftContent)

                if (scrollViewWidth == null) {
                    staticContentGiftAnimator()
                    return
                }

                if (tvContentWidth > scrollViewWidth) {
                    dynamicContentGiftAnimator(tvContentWidth - scrollViewWidth)
                } else {
                    staticContentGiftAnimator()
                }

                Log.i("获取测试宽", "scrollViewWidth1: ${scrollViewWidth}, tvContentWidth1:$tvContentWidth")
            }
        })

        // 抽奖
        binding.tvLotteryContent.text = "咖啡不加糖呀、天青色等烟雨、相机、小丑、面具、哈哈、呵呵、嘿嘿、嘻嘻火箭x1"

        SizeUtils.forceGetViewSize(binding.hsvLottery, object : SizeUtils.OnGetSizeListener {
            override fun onGetSize(view: View?) {
                val scrollViewWidth = view?.width
                val tvContentWidth = SizeUtils.getMeasuredWidth(binding.tvLotteryContent)

                if (scrollViewWidth == null) {
                    staticContentLotteryAnimator()
                    return
                }

                if (tvContentWidth > scrollViewWidth) {
                    dynamicContentLotteryAnimator(tvContentWidth - scrollViewWidth)
                } else {
                    staticContentLotteryAnimator()
                }
                Log.i("获取测试宽", "scrollViewWidth2: ${scrollViewWidth}, tvContentWidth2:$tvContentWidth")
            }
        })
    }

    fun staticContentLotteryAnimator() {
        // 准备好进入动画的x坐标
        binding.fltLottery.translationX = ScreenUtils.getScreenWidth().toFloat()

        val animator1: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltLottery,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.fltLottery.x,
                0f)
        animator1.duration = 528L
        animator1.interpolator = LinearInterpolator()

        val animator2: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltLottery,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                0f,
                -ScreenUtils.getScreenWidth().toFloat())
        animator2.duration = 528L
        animator2.startDelay = 528L + 1650L
        animator2.interpolator = LinearInterpolator()

        animatorSet3.playTogether(animator1, animator2)
        animatorSet3.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun dynamicContentLotteryAnimator(scrollX: Int) {
        // 准备好进入动画的x坐标
        binding.fltLottery.translationX = ScreenUtils.getScreenWidth().toFloat()
        binding.tvLotteryContent.translationX = 0f

        binding.hsvLottery.setOnTouchListener { _, _ -> true }

        val animator1: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltLottery,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.fltLottery.x,
                0f)
        animator1.duration = 528L
        animator1.interpolator = LinearInterpolator()

        val contentShowTime = (scrollX / speed).toLong()
        val animator2: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.tvLotteryContent,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                binding.tvLotteryContent.x,
                binding.tvLotteryContent.x - scrollX)
        animator2.duration = contentShowTime
        animator2.startDelay = 528L + 1650L
        animator2.interpolator = LinearInterpolator()

        val animator3: ObjectAnimator = ObjectAnimator.ofFloat(
                binding.fltLottery,
                ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X,
                0f,
                -ScreenUtils.getScreenWidth().toFloat())
        animator3.duration = 990L
        animator3.startDelay = 528L + 1650L + 1650L + contentShowTime
        animator3.interpolator = LinearInterpolator()

        animatorSet4.playTogether(animator1, animator2, animator3)
        animatorSet4.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        animatorSet1.cancel()
        animatorSet2.cancel()
        animatorSet3.cancel()
        animatorSet4.cancel()
    }

}