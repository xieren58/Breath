package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityDatabindingBinding
import com.zkp.breath.jetpack.databinding.DataBindingViewModel

/**
 * DataBinding是 MVVM 模式在 Android 上的一种实现。
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        // 绑定view_model
        binding.vm = DataBindingViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}