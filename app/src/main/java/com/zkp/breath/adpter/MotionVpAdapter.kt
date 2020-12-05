package com.zkp.breath.adpter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R
import com.zkp.breath.bean.MotionFunctionBean

class MotionVpAdapter(data: MutableList<MotionFunctionBean>) :
        BaseMultiItemQuickAdapter<MotionFunctionBean, BaseViewHolder>(data) {

    init {
        addItemType(MotionFunctionBean.base_function, R.layout.adapter_motion_base)
        addItemType(MotionFunctionBean.coordinator_function, R.layout.adapter_motion_coordinator)
    }

    override fun convert(holder: BaseViewHolder, item: MotionFunctionBean) {
    }

}