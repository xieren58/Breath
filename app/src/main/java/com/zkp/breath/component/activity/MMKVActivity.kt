package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityMmkvBinding

class MMKVActivity : BaseActivity() {

    private lateinit var binding: ActivityMmkvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMmkvBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }


}