package com.zkp.breath.component.activity

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentA
import com.zkp.breath.component.fragment.test.TestFragmentB
import com.zkp.breath.component.fragment.test.TestFragmentC
import com.zkp.breath.component.fragment.test.TestFragmentD
import com.zkp.breath.databinding.ActivityFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * https://juejin.cn/post/6900739309826441224
 *
 * FragmentManager：执行添加/移除/替换 fragment 并将这些操作加入到返回栈中的操作，这些操作被称为「事务」
 *
 * 1. 每个 FragmentActivity 及其子类（如 AppCompatActivity）都可以通过getSupportFragmentManager() 来访问 FragmentManager。
 * 2. Fragment 也能管理一个或多个子 fragment（译者注：嵌套 fragment，即一个 fragment 的直接宿主可能是 activity
 *      或另一个 fragment）。在 fragment 中，您可以通过 getChildFragmentManager() 来获取管理子 fragment 的
 *      FragmentManager 实例。如果需要访问该 fragment 宿主的 FragmentManager，可以使用  getParentFragmentManager()。
 */
class FragmentDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)
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
                add(R.id.fcv, TestFragmentA::class.java, bundle, TestFragmentA::class.java.simpleName)
            }
        }
    }

}