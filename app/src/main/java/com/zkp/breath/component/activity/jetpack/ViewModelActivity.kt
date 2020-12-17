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
 *   化、反序列化的数据。
 *
 * 2.异步回调问题：拥有声明周期感知，防止异步获取数据操作情况下页面销毁没有取消操作出现内存泄漏。
 *
 * 3.职能单一原则，数据请求或者管理不应该放在ui层，会导致ui层过臃肿。
 *
 * 4.作为activity和fragment，fragment和子fragment，同级fragment之间的通信方式。只关注ViewModel，而不需要关注
 *   通信对方。
 *
 *
 * 核心实现简述：就是Activity/Fragment内部有一个存放ViewModel的Map，管理我们存入的ViewModel。
 * 问题1：为什么activity 重建后 ViewModel 仍然存在？
 *       其实只要保证activity 重建后管理ViewModel的Map不便即可。
 *
 *
 * 注意：
 * 1.Activity销毁的时候，框架会调用 ViewModel 对象的 onCleared() 方法，我们可以在这个方法进行释放资源的操作。
 * 2.ViewModel 绝不能引用视图、Lifecycle 或可能存储对 Activity 上下文的引用的任何类。因为vm是数据持久，而视图，Lifecycle
 *  （其实就是activity，fragment），引用activity上下文的类都会引起内存泄露。但是可以包含LifecycleObservers，如 LiveData 对象
 * 3.如果 ViewModel 需要 Application 上下文（例如，为了查找系统服务），它可以扩展 AndroidViewModel 类并设置用
 *   于接收 Application 的构造函数。
 * 4.Fragment 之间共享数据。由于 两个 fragment 使用的都是 activity 范围的 ViewModel （ViewModelProvider 构造
 *   器传入的activity ），因此它们获得了相同的 ViewModel 实例，自然其持有的数据也是相同的，这也 保证了数据的一致性。
 *   > Activity 不需要执行任何操作，也不需要对此通信有任何了解。
 *   > Fragment 不需要相互了解，并且不受另一个 fragment 的生命周期影响，如果其中一个 fragment 消失了，则另一个继续照常工作。
 *
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