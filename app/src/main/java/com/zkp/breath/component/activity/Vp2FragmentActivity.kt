package com.zkp.breath.component.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.zkp.breath.R
import com.zkp.breath.adpter.Vp2FragmentAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentD
import com.zkp.breath.component.fragment.test.TestFragmentE
import com.zkp.breath.component.fragment.test.TestFragmentF
import kotlinx.android.synthetic.main.activity_fragment_vp2.*

class Vp2FragmentActivity : BaseActivity(R.layout.activity_fragment_vp2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val arrayListOf = mutableListOf(TestFragmentD(), TestFragmentE(), TestFragmentF())
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.adapter = Vp2FragmentAdapter(arrayListOf, this)
    }

}