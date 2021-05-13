package com.zkp.breath.component.activity.weight.custom

import android.graphics.Path
import android.os.Bundle
import android.util.TypedValue
import com.blankj.utilcode.util.ColorUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCustomViewBinding
import com.zkp.breath.databinding.ActivityEntranceBinding


/**
 * 1. Paint.ANTI_ALIAS_FLAG: 抗锯齿，一般都要开启。原理就是 模糊边缘（增加/减少像素，透明度）使其看起来顺畅
 * 2. TypedValue.applyDimension()，将不同单位的值转换成px像素值
 * 3. onSizeChanged()，view的大小发生变化会回调这个方法。
 * 4. Path.Direction 和 Path#fillType(),PathMeasure 是难点，但不是重点(视频自定义11)
 */
class CustomViewActivity : BaseActivity() {

    private lateinit var binding: ActivityCustomViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}