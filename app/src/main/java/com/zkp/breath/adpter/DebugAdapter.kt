package com.zkp.breath.adpter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R
import java.util.*


class DebugAdapter(data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.adpter_debug, data) {

    private val colors = intArrayOf(-0xff7a89, -0xfc560c, -0x27e4a0, -0x6800, -0xb350b0, -0x98c549)

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name1, item)

        val random = Random()
        val i = random.nextInt(colors.size)
        val convertView: View = holder.itemView
        convertView.setBackgroundColor(colors[i])
    }

}