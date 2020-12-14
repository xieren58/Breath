package com.zkp.breath.component.fragment.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding
import com.zkp.breath.jetpack.viewmodel.JetPackViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestFragmentA(val arg: Int) : BaseFragment() {

    private lateinit var binding: FragmentTestBinding
    private val mainScope by lazy {
        MainScope()
    }

    //共享范围activity
    private val mViewModel by activityViewModels<JetPackViewModel>()
    private lateinit var viewModel: JetPackViewModel

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
        binding = FragmentTestBinding.inflate(inflater, container, b)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "我是TestFragmentA"
        val int = requireArguments().getInt("some_int")
        Log.i("TestFragmentA获取参数", "int: $int")
        Log.i("TestFragmentA获取构造参数", "arg: $arg")

        binding.tv.setOnClickListener(onClickListener)

        Log.i("获取fragmentManager", "TestFragmentA_parentFragmentManager:$parentFragmentManager ")
        Log.i("获取fragmentManager", "TestFragmentA_childFragmentManager:$childFragmentManager ")

        val data = mViewModel.data
        val value = data?.value
        Log.i("获取共享范围activity的vm", "value: $value")

        viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)
        viewModel.initData()?.observe(viewLifecycleOwner, Observer<String> {
            goToFragmentB()
        })
    }

    private fun goToFragmentB() {
        mainScope.launch {

            delay(4000)

            // 添加子fragment
            val bundle = bundleOf("some_int" to 0)
            childFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fcv, TestFragmentB::class.java, bundle, TestFragmentB::class.java.simpleName)
            }

            ToastUtils.showShort("跳转FragmentB")
        }
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            // 通过tag获取对应fragment实例
            val findFragmentByTag = childFragmentManager.findFragmentByTag(TestFragmentB::class.java.simpleName)
            childFragmentManager.commit {
                // 删除对应的fragment
                findFragmentByTag?.let { remove(it) }
                ToastUtils.showShort("删除Fragment")
            }
        }
    }

    class AFragmentFactory(private val arg: Int) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            if (className == TestFragmentA::class.java.name) {
                return TestFragmentA(arg)
            }
            return super.instantiate(classLoader, className)
        }
    }

}