package com.zkp.breath.adpter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.CollectionUtils

class Vp2FragmentAdapter(var data: List<Fragment>, fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = if (CollectionUtils.isEmpty(data)) 0 else data.size

    override fun createFragment(position: Int): Fragment {
        return data[position]
    }

}