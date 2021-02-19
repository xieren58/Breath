package com.zkp.breath.component.activity.weight

import android.content.res.ColorStateList
import android.os.Bundle
import com.blankj.utilcode.util.ColorUtils
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityButtonQmuiBinding


class QMUIButtonActivity : BaseActivity(R.layout.activity_button_qmui) {

    private lateinit var binding: ActivityButtonQmuiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonQmuiBinding.inflate(layoutInflater)
    }

}