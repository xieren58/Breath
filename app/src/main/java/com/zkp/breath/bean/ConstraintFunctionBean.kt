package com.zkp.breath.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class ConstraintFunctionBean(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val base_function = 0
        const val flow_function = 1
        const val layer_function = 2
    }
}


