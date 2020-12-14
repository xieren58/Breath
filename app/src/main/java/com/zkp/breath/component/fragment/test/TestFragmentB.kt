package com.zkp.breath.component.fragment.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding
import com.zkp.breath.jetpack.viewmodel.JetPackViewModel

class TestFragmentB : BaseFragment() {

    private lateinit var binding: FragmentTestBinding

    // 获取父fragment的vm
    private val mViewModel by viewModels<JetPackViewModel>({ requireParentFragment() })

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
        binding = FragmentTestBinding.inflate(inflater, container, b)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "我是TestFragmentB"

        val int = requireArguments().getInt("some_int")
        Log.i("TestFragmentB获取参数", "int: $int")

        Log.i("获取fragmentManager", "TestFragmentB_parentFragmentManager:$parentFragmentManager ")
        Log.i("获取fragmentManager", "TestFragmentB_childFragmentManager:$childFragmentManager ")

        val data = mViewModel.data
        val value = data?.value
        Log.i("共享范围fragment 内部", "value: $value")
    }


}