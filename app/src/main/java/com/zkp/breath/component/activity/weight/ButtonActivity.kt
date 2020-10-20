package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityButtonBinding

/**
 * selector中的state_pressed="true"对于设置监听点击事件的TextView控件才有效，否则无效
 */
class ButtonActivity : BaseActivity() {

    private lateinit var binding: ActivityButtonBinding

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
            }
        }
    }

}