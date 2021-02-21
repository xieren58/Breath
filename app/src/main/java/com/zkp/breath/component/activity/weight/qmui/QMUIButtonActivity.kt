package com.zkp.breath.component.activity.weight.qmui

import android.os.Bundle
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityQmuiButtonBinding


class QMUIButtonActivity : BaseActivity(R.layout.activity_qmui_button) {

    private lateinit var binding: ActivityQmuiButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQmuiButtonBinding.inflate(layoutInflater)
    }

}