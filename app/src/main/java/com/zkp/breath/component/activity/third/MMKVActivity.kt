package com.zkp.breath.component.activity.third

import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.mmkv.template.AppConfiguration
import kotlinx.android.synthetic.main.activity_mmkv.*

/**
 * https://github.com/Tencent/MMKV
 */
class MMKVActivity : BaseActivity(R.layout.activity_mmkv) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        tv_write.setOnClickListener(onClickListener)
        tv_read.setOnClickListener(onClickListener)
        tv_remove.setOnClickListener(onClickListener)
    }

    private fun write() {
        AppConfiguration.getDefault(this).testA = true
    }

    private fun read() {
        if (AppConfiguration.getDefault(this).containsKeyTestA()) {
            val testA = AppConfiguration.getDefault(this).testA
            Log.i("read", "testA: $testA")
        }
    }

    private fun remove() {
        AppConfiguration.getDefault(this).removeTestA()
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            if (v == null) {
                return
            }

            if (v == tv_write) {
                write()
                return
            }

            if (v == tv_read) {
                read()
                return
            }

            if (v == tv_remove) {
                remove()
            }
        }
    }

}