package com.zkp.breath.component.activity.blankj

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityBlankjLogBinding

class LogActivity : BaseActivity() {

    private lateinit var binding: ActivityBlankjLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlankjLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val config = LogUtils.getConfig()

        LogUtils.dTag("测试Tag", "测试内容")
    }

}