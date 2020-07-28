package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityViewbindingBinding

class ViewBindingActivity : BaseActivity() {

    private lateinit var binding: ActivityViewbindingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewbindingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        // 调用include内部控件的方式：binding + include布局id + 控件id
        binding.appbar.tvTitle.text = "ViewBindingActivity"
    }

}