package com.zkp.breath.component.activity.base

import me.jessyan.autosize.internal.CustomAdapt

/**
 * 开发之前要和UI设计师沟通好基准设计图的宽高尺寸，单位是px或dp，通常Android是以360*640dp为基准。
 * 这个类是以高为基准。
 */
abstract class HeightBaseActivity : BaseActivity(), CustomAdapt {

    //取消以宽度为基准进行适配(默认是以宽度为基准)
    override fun isBaseOnWidth(): Boolean {
        return false
    }

    //返回高度的单位尺寸（设计稿高的dp值）
    override fun getSizeInDp(): Float {
        return 640f
    }
}