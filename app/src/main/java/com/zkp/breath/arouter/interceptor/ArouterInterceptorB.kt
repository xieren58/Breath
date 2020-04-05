package com.zkp.breath.arouter.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.zkp.breath.arouter.InterceptorPriority
import com.zkp.breath.arouter.utils.logInitMethod
import com.zkp.breath.arouter.utils.logProcessMethod

@Interceptor(priority = InterceptorPriority.B_INTERCEPTOR_PRIORITY, name = "拦截器ArouterInterceptorB")
class ArouterInterceptorB : IInterceptor {

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val group = postcard.group
        val path = postcard.path
        logProcessMethod("group: " + group + ",path: " + path)
        callback.onContinue(postcard)
    }

    override fun init(context: Context) {
        logInitMethod()
    }
}