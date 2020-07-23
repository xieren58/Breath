package com.zkp.breath.adpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R


class EntranceAdapter(data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.adpter_entrance, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }

}