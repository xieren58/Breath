package com.zkp.breath.component.fragment.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestFragmentA : BaseFragment() {

    private lateinit var binding: FragmentTestBinding
    val mainScope by lazy {
        MainScope()
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
        binding = FragmentTestBinding.inflate(inflater, container, b)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "我是TestFragmentA"
        val int = requireArguments().getInt("some_int")
        Log.i("TestFragmentA获取参数", "int: $int")

        binding.tv.setOnClickListener(onClickListener)

        goToFragmentB()
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

}