package com.zkp.breath.arouter.utils

import android.util.Log
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.blankj.utilcode.util.AppUtils

private val TAG = "IInterceptor拦截器LogI"

/**
 * 建议所有拦截器内部的打印都使用此方法，方便对所有拦截器的信息进行分析。
 * 打印process()的log
 */
fun IInterceptor.logProcessMethod(str: String) {
    if (AppUtils.isAppDebug()) {
        Log.i(TAG, this.javaClass.simpleName + "_process(): " + str)
    }
}

/**
 * 建议所有拦截器内部的打印都使用此方法，方便对所有拦截器的信息进行分析。
 * 打印init()的log
 */
fun IInterceptor.logInitMethod(str: String = "") {
    if (AppUtils.isAppDebug()) {
        Log.i(TAG, this.javaClass.simpleName + "_init(): " + str)
    }
}