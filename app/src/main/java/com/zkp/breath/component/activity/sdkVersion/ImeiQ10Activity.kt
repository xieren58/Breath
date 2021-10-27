package com.zkp.breath.component.activity.sdkVersion

import android.os.Bundle
import android.view.View
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityImeiBinding


class ImeiQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityImeiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImeiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        varargSetClickListener(binding.tvImei)
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvImei == v) {
            return
        }
    }

}