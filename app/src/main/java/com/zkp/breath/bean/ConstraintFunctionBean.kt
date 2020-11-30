package com.zkp.breath.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class ConstraintFunctionBean(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val base_function = 0
        const val flow_function = 1
        const val layer_function = 2
        const val iv_filter_btn_function = 3
        const val mock_function = 4
        const val space_function = 5
        const val flow_api_function = 6
        const val group_function = 7
        const val custom_helper_function = 8
    }
}


