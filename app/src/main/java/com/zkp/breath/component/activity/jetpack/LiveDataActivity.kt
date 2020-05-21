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
 * LiveData 具有生命周期感知能力的可观察的数据存储器类。（响应生命周期，数据存储器，可观察）
 * 1.响应生命周期：能够感知组件（Fragment、Activity、Service）的生命周期，这里的“组件”皆指实现了LifecycleOwner接口
 * Fragment、Activity,防止内存泄露。内部获取了组建的生理周期管理对象，然后创建自己的生命周期观察者对象注入，这样就能响应了。
 * 2.数据存储器：就是存放数据的容器。
 * 3.可观察：可以被观察者订阅，只有在组件出于激活状态（STARTED、RESUMED）才会通知观察者有数据更新。
 *
 *
 * 注意：
 * 请确保将用于更新界面的 LiveData 对象存储在 ViewModel 对象中，而不是将其存储在 Activity 或 Fragment 中，原因如下：
 * 1. 避免 Activity 和 Fragment 过于庞大，职能单一原则
 * 2. 数据持久化（viewmodel的功能），防止Activity被杀死导致数据失效。
 *
 * 应用组件的 onCreate() 方法是开始观察 LiveData 对象的正确着手点：
 * 1.确保系统不会从 Activity 或 Fragment 的 onResume() 方法进行冗余调用，因为会 onResume()会存在重复调用的情况。
 * 2.确保 Activity 或 Fragment 变为活跃状态后具有可以立即显示的数据，假如放在onResume()，当数据不存在需要耗时
 *   请求接口数据，那么这个时间可能会造成视觉效果的卡顿。而放在oncreate中，等到执行到了onresume（）可能数据早已
 *   请求完毕。
 *
 * LiveData更新值：
 * 在主线程必须调用 setValue(T)； 工作线程可以使用postValue(T)。
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

        binding.btn.setOnClickListener {
            viewModel.updateData()?.observe(this, Observer<String> {
                binding.tv.text = it
            })
        }
    }

    private fun observe() {
        // 主线程调用observe()方法
        viewModel.initData()?.observe(this, Observer<String> {
            binding.tv.text = it
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
//         使用observe（）会在生命周期的OnDestory自动调用
//        viewModel.initData().removeObserver()
    }


}