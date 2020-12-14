package com.zkp.breath.component.activity

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentA
import com.zkp.breath.databinding.ActivityFragmentBinding
import com.zkp.breath.jetpack.viewmodel.JetPackViewModel

/**
 * https://juejin.cn/post/6900739309826441224
 * https://juejin.cn/post/6844904079697657863
 *
 * 1. 强烈建议始终使用 FragmentContainerView 作为 fragment 的容器。FragmentContainerView 是专门为 fragment
 *    设计的自定义View，它继承自 FrameLayout，它修复了一些动画 z轴索引顺序问题和窗口插入调度，这意味着两个fragment
 *    之间的退出和进入过渡不会互相重叠。使用FragmentContainerView将先开启退出动画然后才是进入动画。
 *    android:name 属性允许您添加fragment，android:tag 属性可以为fragment设置tag。
 *
 * 2. 设置tag时尽量用全名或者常量，不要用simpleName，避免因为混淆导致获取不正确。
 *
 * 3. 使用FragmentViewModelLazyKt的activityViewModels()获取宿主Activity的vm，viewModels()获取宿主
 *    Fragment的vm，作为Fragment之间，Fragment和Activity的通信手段。
 *
 * 4. 过去只能使用默认的空构造函数实例化Fragment，如果不是默认空构造函数系统将不知道如何重新初始化Fragment实例。
 *    现在可以使用FragmentFactory来解决此限制。（很鸡肋的作用，因为数据我们可以使用Bundle的方式传递）
 *
 *    > 创建自定义的FragmentFactory子类。
 *    > 容器的onCreate方法中 super.onCreate(savedInstanceState) 之前设置。
 *    > 如果没有参数的构造函数则无需使用FragmentFactory，但是有则必须使用否则会抛出异常。
 */
class FragmentDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding
    private lateinit var viewModel: JetPackViewModel
    private val customFragmentFactory = TestFragmentA.AFragmentFactory(23)


    override fun onCreate(savedInstanceState: Bundle?) {
        // 设置构造函数带参数的TestFragmentA的fragmentFactory
        supportFragmentManager.fragmentFactory = customFragmentFactory
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)
        viewModel.initData()?.observe(this, Observer<String> {
            ToastUtils.showShort("数据发生改变:$it")
            initView(savedInstanceState)
            initListener()
        })
    }

    private fun initListener() {
        /**
         *  Fragment 生命周期监听。
         *  参数recursive为true自动为子Fragment设置此回调。
         */
        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(@NonNull fm: FragmentManager, @NonNull f: Fragment) {
                super.onFragmentResumed(fm, f)
                Log.i("onFragmentResumed", "name : ${f.javaClass.simpleName}")
            }
        }, true)

        /**
         * 后退栈变动监听器。
         * 执行addToBackStack和（需要执行addToBackStack为前提）点击返回键都会触发
         */
        supportFragmentManager.addOnBackStackChangedListener(object : FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                Log.i("onBackStackChanged", "哈哈哈哈")
            }
        })
    }

    private fun initView(savedInstanceState: Bundle?) {
        /**
         * 仅当 saveInstanceState 为 null 时才创建 fragment 事务。这是为了确保当 activity 第一次 create
         * 时 fragment 仅被添加一次。当系统资源回收或配置发生变化时 saveInstanceState 不为 null 无需再次添加该
         * fragment，因为 fragment 会自动从 savedInstanceState 中恢复。
         */
        if (savedInstanceState == null) {
            // 参数
            val bundle = bundleOf("some_int" to 21)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val simpleName = TestFragmentA::class.java.simpleName
                addToBackStack(simpleName)  // 调用前一定要 setReorderingAllowed(true)
                add(R.id.fcv, TestFragmentA::class.java, bundle, simpleName)
            }
            Log.i("获取fragmentManager", "FragmentDemoActivity_supportFragmentManager:$supportFragmentManager ")
        }
    }

}