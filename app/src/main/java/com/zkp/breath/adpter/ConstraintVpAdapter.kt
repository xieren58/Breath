package com.zkp.breath.adpter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R
import com.zkp.breath.bean.ConstraintFunctionBean

class ConstraintVpAdapter(data: MutableList<ConstraintFunctionBean>) :
        BaseMultiItemQuickAdapter<ConstraintFunctionBean, BaseViewHolder>(data) {

    init {
        addItemType(ConstraintFunctionBean.base_function, R.layout.adapter_constraint_layout)
        addItemType(ConstraintFunctionBean.flow_function, R.layout.adapter_constraint_flow)
        addItemType(ConstraintFunctionBean.layer_function, R.layout.adapter_constraint_layer)
    }

    override fun convert(holder: BaseViewHolder, item: ConstraintFunctionBean) {
    }

}