package com.zkp.breath.component.fragment.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.R
import com.zkp.breath.adpter.VpFragmentAdapter
import com.zkp.breath.component.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vp.*

class TestFragmentC : BaseFragment(R.layout.fragment_vp) {

    lateinit var mutableListOf: MutableList<Fragment>
    lateinit var adapter: VpFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mutableListOf = mutableListOf(TestFragmentD(), TestFragmentE(), TestFragmentF())
        vp.addOnPageChangeListener(onPageChangeListener)
        vp.offscreenPageLimit = 1;
        adapter = VpFragmentAdapter(mutableListOf, childFragmentManager)
        vp.adapter = adapter

        tv_refresh.setOnClickListener(onClickListener)


        val testFragmentD = TestFragmentD()
        val hashCode = testFragmentD.hashCode()
        val testFragmentD1 = TestFragmentD()
        val hashCode1 = testFragmentD1.hashCode()
        Log.i("hashCode测试", "hashCode: $hashCode,  hashCode1: $hashCode1 ")
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            if (v == null) {
                return
            }

            if (v == tv_refresh) {
                val mutableListOf = mutableListOf(TestFragmentG())
                adapter = VpFragmentAdapter(mutableListOf, childFragmentManager)
                vp.adapter = adapter
            }
        }
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

}