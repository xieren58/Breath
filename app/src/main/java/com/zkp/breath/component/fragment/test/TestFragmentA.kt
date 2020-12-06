package com.zkp.breath.component.fragment.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.R
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding

class TestFragmentA : BaseFragment() {

    private lateinit var binding: FragmentTestBinding

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
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            goToFragmentB()
        }
    }

    private fun goToFragmentB() {
        val bundle = bundleOf("some_int" to 0)
        this@TestFragmentA.childFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv, TestFragmentB::class.java, bundle)
        }
    }

}