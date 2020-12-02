package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityMotionBinding

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
 */
class MotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMotionBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
    }

}