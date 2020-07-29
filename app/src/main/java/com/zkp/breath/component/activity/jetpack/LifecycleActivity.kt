package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLiferecycleBinding
import com.zkp.breath.jetpack.lifecycle.JetPackLifecycle

class LifecycleActivity : BaseActivity() {

    private lateinit var binding: ActivityLiferecycleBinding
    private lateinit var jetPackLifecycle: JetPackLifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        jetPackLifecycle = JetPackLifecycle()

        // 添加生命周期观察者
        lifecycle.addObserver(jetPackLifecycle)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除生命周期观察者
        lifecycle.removeObserver(jetPackLifecycle)
    }


}