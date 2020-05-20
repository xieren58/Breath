package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLiferecycleBinding

class LifecycleActivity : BaseActivity() {

    private lateinit var binding: ActivityLiferecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }


}