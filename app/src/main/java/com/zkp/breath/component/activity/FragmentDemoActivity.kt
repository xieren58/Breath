package com.zkp.breath.component.activity

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentA
import com.zkp.breath.databinding.ActivityFragmentBinding

/**
 * https://juejin.cn/post/6900739309826441224
 * https://juejin.cn/post/6844904079697657863
 */
class FragmentDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)
        initView()
    }

    private fun initView() {
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