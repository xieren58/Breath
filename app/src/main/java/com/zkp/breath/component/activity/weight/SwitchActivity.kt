package com.zkp.breath.component.activity.weight

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivitySwitchBinding


class SwitchActivity : BaseActivity() {

    private lateinit var binding: ActivitySwitchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

//        thumbTint：按钮（圆）颜色
//        trackTint：轨道（椭圆）颜色

        binding.switchCompat.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.switchCompat.thumbTintList = ColorStateList.valueOf(0xffFAFAFA.toInt())
                binding.switchCompat.trackTintList = ColorStateList.valueOf(0xffE8E8E8.toInt())
            } else {
                binding.switchCompat.thumbTintList = ColorStateList.valueOf(0xff8844FF.toInt())
                binding.switchCompat.trackTintList = ColorStateList.valueOf(0x808844FF.toInt())
            }
        }
    }

}