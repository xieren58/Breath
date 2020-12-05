package com.zkp.breath.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class MotionFunctionBean(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val base_function = 0
        const val coordinator_function = 1
    }
}


