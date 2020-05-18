package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.blankj.utilcode.util.FragmentUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.TestFragment
import com.zkp.breath.databinding.ActivityFragmentBinding

class ActivityFragment : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        val testFragment = TestFragment()
        FragmentUtils.add(supportFragmentManager, testFragment, R.id.clt_root, false)
    }

}