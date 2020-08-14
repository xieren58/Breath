package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityDatabindingBinding
import com.zkp.breath.jetpack.databinding.DataBindingViewModel

/**
 *
 * DataBinding 会为每个在布局声明 layout 标签的 xml 布局文件生成一个绑定类， 默认情况下类的名称基于布局文件的名称，
 * DataBinding是 MVVM 模式在 Android 上的一种实现。
 *
 * DataBinding各层责任划分：
 * 1. xml：负责定义数据类型和定义变量名，具体控件使用变量名进行数据填充，xml的责任很明确也很简洁，就是在进行数据填充。
 * 2. view层：使用DataBindingUtil绑定xml，对xml定义的变量进行赋值操作。
 * 3. BaseObservable：vm层，操控具体的m层，数据真正的获取的层级。本身具备自动响应数据变化后刷新ui的功能,
 *    真正起到双向绑定的作用，所有的数据应该都放入这一层中。
 *
 *
 * 原理解析：使用DataBinding会生成一个"布局文件名 + Binding + Impl"的类，在xml的variable标签中定义的name会在该类
 * 自动生成"set+Name（）"的方法，所以在view层进行数据赋值的时候其实就是调用了这个类的"set+Name（）"方法。
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        // 绑定view_model
        binding.vm = DataBindingViewModel()
        binding.vmStr = "我们"

        // 相当于viewbinding的用法
        binding.tv.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}