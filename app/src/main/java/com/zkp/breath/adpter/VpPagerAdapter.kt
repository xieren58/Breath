package com.zkp.breath.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.blankj.utilcode.util.CollectionUtils
import com.zkp.breath.databinding.AdpterViewpagerBinding

class VpPagerAdapter(var data: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return if (CollectionUtils.isEmpty(data)) 0 else data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflate = AdpterViewpagerBinding.inflate(
                LayoutInflater.from(container.context), container, false)
        inflate.tv.text = data[position]
        container.addView(inflate.root)
        return inflate.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}