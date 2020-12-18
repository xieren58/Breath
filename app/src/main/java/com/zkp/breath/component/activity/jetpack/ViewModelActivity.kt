package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.ActivityVmBinding
import com.zkp.breath.databinding.FragmentTestBinding
import com.zkp.breath.jetpack.viewmodel.JetPackAndroidViewModel
import com.zkp.breath.jetpack.viewmodel.JetPackViewModel

/**
 * https://juejin.cn/post/6844904100493017095
 *
 * ViewModel解决的问题：
 * 1.数据持久化：当Activity/Fragment被销毁重建时(如屏幕旋转)，onSaveInstanceState()只适合保存少量的可以被序列
 *   化、反序列化的数据。存储方式上看ViewModel是在内存中，所以读写速度更快，而onSaveInstanceState是写到本地文件。
 *
 * 2.避免内存泄漏：由于 ViewModel 的设计，使得 activity/fragment 依赖它，而 ViewModel 不依赖 activity/fragment。
 *   因此只要不让 ViewModel 持有 context 或 view 的引用，就不会造成内存泄漏。如果真的需要context那么请使用AndroidViewModel。
 *
 * 3.职能单一原则，数据请求或者管理不应该放在ui层，会导致ui层过臃肿。
 *
 * 4.作为activity和fragment，fragment和子fragment，同级fragment之间的通信方式进行数据共享。通信对方只关注
 *   ViewModel，而不需要关注对方。
 *
 *
 * 核心实现简述：就是Activity/Fragment内部有一个存放ViewModel的Map，管理我们存入的ViewModel。
 * 问题1：为什么activity 重建后 ViewModel 仍然存在？
 *       其实只要保证activity重建后管理ViewModel的Map不变即可，而Map会缓存在NonConfigurationInstances变量。
 *       在ActivityThread的performLaunchActivity()中启动Activity会传入ActivityClientRecord的
 *       NonConfigurationInstances变量，而ActivityClientRecord不受activity重建的影响。
 * 问题2：为什么Fragment重建后 ViewModel 仍然存在？
 *       Fragment管理ViewModel的FragmentManagerViewModel本身也是一个ViewModel，其内部存放自身的ViewModel和
 *       子Fragment的ViewModel。FragmentManagerViewModel存放在Activity管理ViewModel的Map中，所以activity
 *       重建不变那么Fragment重建也当然不变。
 *
 * 保存界面状态的方法：
 * https://developer.android.google.cn/topic/libraries/architecture/saving-states
 * https://juejin.im/post/5e738d12518825495d69cfb9#heading-10
 *
 */
class ViewModelActivity : BaseActivity() {

    private lateinit var binding: ActivityVmBinding
    private lateinit var viewModel: JetPackViewModel
    private lateinit var androidViewModel: JetPackAndroidViewModel

    private val testSaveKey = "test_key"
    private val testSaveValue = "test_value"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val string = savedInstanceState?.getString(testSaveKey)
        Log.i(ACTIVITY_TAG, "savedInstanceState的内容_1：$string")

        binding = ActivityVmBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)

        androidViewModel = ViewModelProvider(this).get(JetPackAndroidViewModel::class.java)

        // 主线程调用observe()方法
        viewModel.initData()?.observe(this, Observer<String> {
            ToastUtils.showShort("数据发生改变:$it")
        })

        // 传入application的vm
        androidViewModel.initData()?.observe(this, Observer<String> {
            ToastUtils.showShort("AndroidViewModel数据发生改变:$it")
        })

        // 模拟fragment之间的资源共享的场景。（因为vm的寄宿对象是activity）
        val viewModelAFragment = ViewModelAFragment()
        FragmentUtils.add(supportFragmentManager, viewModelAFragment, R.id.rlt, false)
        binding.mb.setOnClickListener {
            val viewModelBFragment = ViewModelBFragment()
            FragmentUtils.add(supportFragmentManager, viewModelBFragment, R.id.rlt, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(testSaveKey, testSaveValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val string = savedInstanceState.getString(testSaveKey)
        Log.i(ACTIVITY_TAG, "savedInstanceState的内容_2：$string")
    }

    class ViewModelAFragment : BaseFragment() {

        private lateinit var binding: FragmentTestBinding
        private lateinit var viewModel: JetPackViewModel

        override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
            binding = FragmentTestBinding.inflate(inflater, container, b)
            return binding.root
        }

        @SuppressLint("SetTextI18n")
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            binding.tv.text = "ViewModelAFragment_测试ViewModel的共享资源能力"

            activity?.run {
                viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)
                viewModel.data?.observe(this, Observer {
                    ToastUtils.showShort("模拟Fragment之间数据共享_存放数据：$it")
                })
                viewModel.initData()
            } ?: throw Exception("Invalid Activity")
        }

    }


    class ViewModelBFragment : BaseFragment() {

        private lateinit var binding: FragmentTestBinding
        private lateinit var viewModel: JetPackViewModel

        override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
            binding = FragmentTestBinding.inflate(inflater, container, b)
            return binding.root
        }

        @SuppressLint("SetTextI18n")
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            binding.tv.text = "ViewModelAFragment_测试ViewModel的共享资源能力"

            activity?.run {
                viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)
                viewModel.data?.observe(this, Observer {
                    ToastUtils.showShort("模拟Fragment之间数据共享_获取ViewModelAFragment存放的数据：$it")
                    binding.tv.text = it
                })
                viewModel.initData()
            } ?: throw Exception("Invalid Activity")
        }
    }

}