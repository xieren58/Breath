package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLivedataBinding
import com.zkp.breath.jetpack.livedata.JetPackLiveDataViewModel

/**
 * https://developer.android.google.cn/topic/libraries/architecture/livedata#kotlin
 * https://juejin.cn/post/6844904111112978439
 *
 * LiveData 具有生命周期感知能力的可观察的数据存储器类。（响应生命周期，数据存储器，可观察）
 *
 * 1.并不是所有数据都需要使用LiveData作为容器，使用LiveData的前提是因为数据需要感知组件的生命周期（一般来说ui数据都
 *   需要感知组件的生命周期），请确保将用于更新界面的 LiveData 对象存储在 ViewModel 对象中（数据的持久化和感知组件的生命周期）。
 *
 * 2.响应生命周期：能够感知组件（Fragment、Activity、Service）的生命周期，这里的“组件”皆指实现了LifecycleOwner接口
 *   其实就是获取组件存放LifecycleObserver的map，然后创建LiveData的LifecycleObserver存入。在LiveData数据发生
 *   变化或者绑定的LifecycleOwner生命周期发生变化，都会去调用LiveData的dispatchingValue()方法，内部会检查绑定
 *   的LifecycleOwner生命周期。
 *
 * 3.数据存储器：就是存放数据的容器，仅持有 单个且最新 的数据。
 *
 * 4.可观察：可以被观察者订阅，只有在组件出于激活状态（STARTED、RESUMED）才会通知观察者有数据更新。
 *
 * 5.组件的 onCreate() 是开始观察 LiveData 对象的正确着手点。避免onResume()重复注入同一个观察者（同一个观察者
 *   也会抛出异常）。
 *
 * 6.LiveData更新值，在主线程必须调用 setValue(T)，工作线程可以使用postValue(T)。
 *
 * 7.推荐使用observe()注入回调，因为在数据变动情况下可以根据组件的生命周期选择是否触发回调，在组件的onDestroy()方
 *   法还会自动移除注入的回调。observeForever()不会感知生命周期，只要数据发生变动则触发回调，而且需要手动在onDestroy()
 *   方法移除注入的回调。
 *
 * 8. androidx fragment 1.2.0 起，添加了新的 Lint 检查，以确保您在从 onCreateView()、onViewCreated() 或
 *    onActivityCreated() 观察 LiveData 时使用 getViewLifecycleOwner() 而不传this（即Fragment）。因为
 *    有时候Fragment只是进行视图销毁和重建（onDestroyView()和onCreateView()），由于Fragment没有真正的销毁(
 *    onDestroy()), 所以LiveData并不会执行移除观察者，这就导致在下次重建视图(onCreateView())又添加了一个LiveData
 *    观察者，往返多次会添加多个，那么就会造成回调多次（可能发生多次ui刷新），所以应该传入getViewLifecycleOwner()
 *    感知的是Fragment的视图的生命周期。
 */
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

        val repositoryLiveData = viewModel.repositoryLiveData as MediatorLiveData
        repositoryLiveData.observe(this) {
            Log.i("测试Transformations.map", "value: $it")
        }
        viewModel.repository.generateData()

        viewModel.repository.getSwitchMapData(true).observe(this) {
            Log.i("测试SwitchMap", "value: $it")
        }
        viewModel.repository.liveData1.value = 2331
    }

    /**
     * LiveData使用observe（）会在生命周期的OnDestory自动移除观察者（即回调监听）。
     */
    @SuppressLint("SetTextI18n")
    private fun observe() {
        isObserveForever = false

        // Transformations.map会生成一个新的MediatorLiveData
        viewModel.initData()?.let {
            binding.tv.text = it.value + "_拼接"
        }
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
            // 通过observe注册的livedata会绑定组件的生命周期的，所以内部会自动回收。下面的示例只是为了展示提前移除观察者，
            // 可以注释下面的移除代码，然后观察打印结果就可以知道是有自动回收的。

            // 通过observe注册的观察者通过下面的方法进行移除，该方法内部会判断是否响应生命周期组件，响应的才会进行
            // 移除，而通过observe注册的观察者会生成自己的LifecycleObserver然后添加到生命周期组件中。
            viewModel.initData()?.removeObservers(this)
        }
        // 判断是否存在LiveData的观察者
        val hasActiveObservers = viewModel.initData()?.hasActiveObservers()
        Log.i(ACTIVITY_TAG, "是否存在LiveData的注册观察者：$hasActiveObservers")
    }


}