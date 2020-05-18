package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.FragmentUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.TestFragment
import com.zkp.breath.databinding.ActivityFragmentBinding

class ActivityFragment : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding
    private var fragmentsList: ArrayList<Fragment> = ArrayList()
    private var fragmentsTagList: Array<String?> = arrayOfNulls(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        for (i in 0..2) {
            val testFragmentA = TestFragment()
//            FragmentUtils.add(supportFragmentManager, testFragmentA, R.id.clt_root, true)
            FragmentUtils.add(supportFragmentManager, fragmentsList, R.id.clt_root, fragmentsTagList, i)
        }
    }


}