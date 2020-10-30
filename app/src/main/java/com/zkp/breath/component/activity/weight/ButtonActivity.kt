package com.zkp.breath.component.activity.weight

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.CollectionUtils
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

    val broadcastEventList: MutableList<String> = mutableListOf()

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
//                binding.button.isEnabled = !binding.button.isEnabled
//                binding.tvBtn.isEnabled = !binding.tvBtn.isEnabled

//                animator()
//                animator2()
                xxx()
            }
        }
    }

    private fun pushBroadcastEvent(event: String) {
        if (CollectionUtils.isEmpty(broadcastEventList)) {
            broadcastEventList.add(event)
            // todo 根据数据判断类型，启动礼物广播或者送礼广播
        } else {
            broadcastEventList.add(event)
        }
    }

    private fun popBroadcastEvent() {
        if (!CollectionUtils.isEmpty(broadcastEventList)) {
            try {
                broadcastEventList.removeAt(0)
            } catch (e: Exception) {
            }
            if (!CollectionUtils.isEmpty(broadcastEventList)) {
                // todo 根据数据判断类型，启动礼物广播或者送礼广播
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

        val contentShowTime = (scrollX / 0.17f).toLong()    // 时间

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

        // todo 声明为成员变量，及时回收
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2, animator3)
        animatorSet.addListener(animatorListener)
        animatorSet.start()
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

        // todo 声明为成员变量，及时回收
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2)
        animatorSet.addListener(animatorListener)
        animatorSet.start()
    }

    fun xxx() {
        // 送礼
        binding.tvGiftRich.setText("卡卡罗特 给 ")
        binding.tvGiftContent.setText("咖啡不加糖呀、天青色等烟雨，sdasda,dasdsdas,2312321,423335,5454343火箭x1")

        // 文字长度是否超过边框宽度，是则进行滚动动画，否则进行不滚动动画
        SizeUtils.forceGetViewSize(binding.hsvGift, object : SizeUtils.OnGetSizeListener {
            override fun onGetSize(view: View?) {
                val width = view?.width
                val measuredWidth2 = SizeUtils.getMeasuredWidth(binding.tvGiftContent)

                if (width == null) {
                    staticContentGiftAnimator()
                    return
                }

                if (measuredWidth2 > width) {
                    dynamicContentGiftAnimator(measuredWidth2 - width)
                } else {
                    staticContentGiftAnimator()
                }


                Log.i("获取测试宽", "measuredWidth1: ${width}, measuredWidth2:$measuredWidth2")
            }
        })


        // 抽奖
        binding.tvLotteryContent.setText("咖啡不加糖呀、天青色等烟雨，sdasda,dasdsdas,2312321,423335,5454343火箭x1")

        SizeUtils.forceGetViewSize(binding.hsvLottery, object : SizeUtils.OnGetSizeListener {
            override fun onGetSize(view: View?) {
                val width = view?.width
                val measuredWidth2 = SizeUtils.getMeasuredWidth(binding.tvLotteryContent)

                if (width == null) {
                    staticContentLotteryAnimator()
                    return
                }

                if (measuredWidth2 > width) {
                    dynamicContentLotteryAnimator(measuredWidth2 - width)
                } else {
                    staticContentLotteryAnimator()
                }
                Log.i("获取测试宽", "measuredWidth1: ${width}, measuredWidth2:$measuredWidth2")
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

        // todo 声明为成员变量，及时回收
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2)
        animatorSet.addListener(animatorListener)
        animatorSet.start()
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

        val contentShowTime = (scrollX / 0.17f).toLong()
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

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2, animator3)
        animatorSet.addListener(animatorListener)
        animatorSet.start()
    }


    val animatorListener = object : ObjectAnimatorAssist.SimpleAnimatorListener() {

        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            // todo 准备好拿下一条数据
            popBroadcastEvent()
        }
    }


}