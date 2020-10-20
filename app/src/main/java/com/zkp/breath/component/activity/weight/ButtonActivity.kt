package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityButtonBinding


class ButtonActivity : BaseActivity() {

    private lateinit var binding: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.tv.setOnClickListener(onClick)
    }

    val onClick = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            if (v == binding.tv) {
                binding.button.isEnabled = !binding.button.isEnabled
            }
        }
    }

}