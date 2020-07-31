package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLivedataBinding
import com.zkp.breath.jetpack.livedata.JetPackLiveDataViewModel

/**
 * https://developer.android.google.cn/topic/libraries/architecture/livedata#kotlin
 *
 * LiveData 具有生命周期感知能力的可观察的数据存储器类。（响应生命周期，数据存储器，可观察）
 * 1.响应生命周期：能够感知组件（Fragment、Activity、Service）的生命周期，这里的“组件”皆指实现了LifecycleOwner接口
 * Fragment、Activity,防止内存泄露。内部获取了组件的生理周期管理对象，然后创建自己的生命周期观察者对象注入，这样就能响应了。
 * 2.数据存储器：就是存放数据的容器。
 * 3.可观察：可以被观察者订阅，只有在组件出于激活状态（STARTED、RESUMED）才会通知观察者有数据更新。
 * 4.并不是所有数据都需要使用LiveData作为容器，使用LiveData的前提是因为数据需要感知组件的生命周期进行ui显示。
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
 *
 *
 * */
class LiveDataActivity : BaseActivity() {

    private lateinit var binding: ActivityLivedataBinding
    private lateinit var viewModel: JetPackLiveDataViewModel
    private var isObserveForever: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLivedataBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JetPackLiveDataViewModel::class.java)

        observe()
//        observeForever()

        binding.btn.setOnClickListener {
            viewModel.updateData()?.observe(this, Observer<String> {
                binding.tv.text = it
            })
        }

        // 测试自定LiveData
        viewModel.cusLiveData.observe(this, Observer<String> {
            ToastUtils.showShort(it)
        })

        // 测试MediatorLiveData
        viewModel.mediatorLiveData.observe(this, Observer<String> {
            Log.i(ACTIVITY_TAG, "MediatorLiveData: $it")
        })
    }

    /**
     * LiveData使用observe（）会在生命周期的OnDestory自动移除观察者（即回调监听）。
     */
    private fun observe() {
        isObserveForever = false

        // 数据转换Transformations.map
        viewModel.initData()?.let {
            Transformations.map(it) {
                it.plus("_拼接")
            }
        }?.observe(this, Observer<String> {
            binding.tv.text = it
        })

//        viewModel.initData()?.let {
//            Transformations.switchMap(it) {
//                MutableLiveData<String>()
//            }
//        }

    }

    /**
     * 没有关联LifecycleOwner对象，无法感知生命周期，在这种情况始终会收到关于修改的通知（不会对生命状态进行判断）。
     */
    private fun observeForever() {
        isObserveForever = true
        viewModel.initData()?.observeForever(observeForever)
    }

    private val observeForever = Observer<String> { s -> ToastUtils.showShort("数据发生改变:$s") }

    override fun onDestroy() {
        super.onDestroy()
        if (isObserveForever) {
            // 通过observeForever添加的观察者使用下面的方法进行移除
            viewModel.initData()?.removeObserver(observeForever)
        } else {
            // 通过observe注册的观察者通过下面的方法进行移除，该方法内部会判断是否响应生命周期组件，响应的才会进行
            // 移除，而通过observe注册的观察者会生成自己的LifecycleObserver然后添加到生命周期组件中。
            viewModel.initData()?.removeObservers(this)
        }
        // 判断是否存在LiveData的观察者
        val hasActiveObservers = viewModel.initData()?.hasActiveObservers()
        Log.i(ACTIVITY_TAG, "是否存在LiveData的注册观察者：$hasActiveObservers")
    }


}