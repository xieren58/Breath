package com.zkp.breath.component.fragment.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zkp.breath.R
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding

class TestFragmentF : BaseFragment() {

    private lateinit var binding: FragmentTestBinding

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
        binding = FragmentTestBinding.inflate(inflater, container, b)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "我是TestFragmentF"
        binding.root.setBackgroundResource(R.color.colorFFFFEB3B)
    }

}