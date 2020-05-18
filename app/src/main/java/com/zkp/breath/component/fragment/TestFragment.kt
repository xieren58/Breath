package com.zkp.breath.component.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zkp.breath.R
import com.zkp.breath.component.fragment.base.BaseFragment
import com.zkp.breath.databinding.FragmentTestBinding

class TestFragment : BaseFragment() {

    private lateinit var binding: FragmentTestBinding

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View? {
        binding = FragmentTestBinding.inflate(inflater, container, b)
        return binding.root
    }


}