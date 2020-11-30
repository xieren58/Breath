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
        addItemType(ConstraintFunctionBean.iv_filter_btn_function, R.layout.adapter_constraint_iv_filter_btn)
        addItemType(ConstraintFunctionBean.mock_function, R.layout.adapter_constraint_mock)
        addItemType(ConstraintFunctionBean.space_function, R.layout.adapter_constraint_space)
        addItemType(ConstraintFunctionBean.flow_api_function, R.layout.adapter_constraint_flow_api)
        addItemType(ConstraintFunctionBean.group_function, R.layout.adapter_constraint_group)
        addItemType(ConstraintFunctionBean.custom_helper_function, R.layout.adapter_constraint_custom_helper)
    }

    override fun convert(holder: BaseViewHolder, item: ConstraintFunctionBean) {
    }

}