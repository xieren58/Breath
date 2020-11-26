package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.didichuxing.doraemonkit.kit.core.BaseActivity
import com.zkp.breath.databinding.ActivityDataStoreBinding


class DataStoreActivity : BaseActivity() {

    private lateinit var binding: ActivityDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

}