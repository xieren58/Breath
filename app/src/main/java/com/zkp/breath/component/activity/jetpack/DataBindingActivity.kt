package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityDatabindingBinding
import com.zkp.breath.jetpack.databinding.DataBindingViewModel

/**
 *
 * DataBinding 会为每个在布局声明 layout 标签的 xml 布局文件生成一个绑定类。 默认情况下，类的名称基于布局文件的名称。
 *
 *
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

        // 相当于viewbinding的用法
        binding.tv.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}