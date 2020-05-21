package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLivedataBinding
import com.zkp.breath.jetpack.livedata.JetPackLiveData

/**
 * https://developer.android.google.cn/topic/libraries/architecture/livedata#kotlin
 *
 * 这里的“组件”皆指实现了LifecycleOwner接口Fragment、Activity。
 *
 * LiveData 具有生命周期感知能力的可观察的数据存储器类。（响应生命周期，数据存储器，可观察）
 * 1.响应生命周期： 能够感知组件（Fragment、Activity、Service）的生命周期，防止内存泄露。内部获取了组建的生理周期
 * 管理对象，然后创建自己的生命周期观察者对象注入，这样就能响应了。
 * 2.数据存储器：就是存放数据的容器。
 * 3.可观察：可以被观察者订阅，只有在组件出于激活状态（STARTED、RESUMED）才会通知观察者有数据更新。
 *
 *
 * */
class LiveDataActivity : BaseActivity() {

    private lateinit var binding: ActivityLivedataBinding
    private lateinit var viewModel: JetPackLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLivedataBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JetPackLiveData::class.java)

        observe()
//        observeForever()
    }

    private fun observe() {
        // 主线程调用observe()方法
        viewModel.initData()?.observe(this, Observer<String> {
            binding.tv.text = ""
            ToastUtils.showShort("数据发生改变:$it")
        })
    }

    /**
     * 没有关联LifecycleOwner对象，无法感知生命周期。在这种情况始终会收到关于修改的通知。
     */
    private fun observeForever() {
        viewModel.initData()?.observeForever(Observer<String> {
            ToastUtils.showShort("数据发生改变:$it")
        })

        // 可以手动调用移除观察者
        // 使用observe（）会在生命周期的OnDestory自动调用
//        viewModel.initData().removeObserver()
    }


}