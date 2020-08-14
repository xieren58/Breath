package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityDatabindingBinding
import com.zkp.breath.jetpack.databinding.DataBindingViewModel


/**
 *
 * DataBinding 会为每个在布局声明 layout 标签的 xml 布局文件生成一个绑定类， 默认情况下类的名称基于布局文件的名称，
 * DataBinding是 MVVM 模式在 Android 上的一种实现。在xml中的变量都是自动空安全的，即便没有在外面赋值，在xml中直接
 * 调用也不会有Null异常（很牛的设计）。
 *
 * DataBinding各层责任划分：
 * 1. xml：负责定义数据类型和定义变量名，具体控件使用变量名进行数据填充，xml的责任很明确也很简洁，就是在进行数据填充。
 * 2. view层：使用DataBindingUtil绑定xml，对xml定义的变量进行赋值操作。
 * 3. BaseObservable：vm层，操控具体的m层，数据真正的获取的层级。本身具备自动响应数据变化后刷新ui的功能,
 *    真正起到双向绑定的作用，所有的数据应该都放入这一层中。
 *
 *
 * 原理解析：使用DataBinding会生成一个"布局文件名 + Binding + Impl"的类，在xml的variable标签中定义的name会在该类
 * 自动生成"set+Name（）"的方法，所以在view层进行数据赋值的时候其实就是调用了这个类的"set+Name（）"方法；而BaseObservable
 * 起到双向绑定的作用，内部的操作逻辑可以看成是也在调用"布局文件名 + Binding + Impl"该类的方法。
 *
 * 缺点：1.BaseObservable的实现类作为的Vm层是不具备数据持久化的，不像ViewModel的实现类是具备数据持久化的；
 *      2.ViewModel内部的LiveData可以感知组件的生命周期，而且数据可以进行数据刷新回调；而BaseObservable的实现类
 *      本身就具备双向绑定的功能，但是不具备感觉感知组件的生命周期的功能。
 *      3.处理ImageView的加载图片，需要自定义属性后自定义BindingAdapter进行处理。（无法使用import glide进入xml中，
 *        因为需要上下文，而xml没有上下文可以获取）
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
        binding.tvPlainUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // 测试空安全
                val plainUser = PlainUser()
                plainUser.lastName.set("我是最后的LastName")
                binding.plainUser = plainUser
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    /**
     * 一种更细粒度的绑定方式，可以具体到成员变量，这种方式无需继承 BaseObservable，一个简单的 POJO 就可以实现。
     * 其实内部帮我们自己继承了BaseObservable
     */
    class PlainUser {
        val firstName = ObservableField<String>()
        val lastName = ObservableField<String>()
        val age = ObservableInt()
    }
}