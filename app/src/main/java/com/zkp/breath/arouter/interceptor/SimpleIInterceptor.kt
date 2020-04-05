package com.zkp.breath.arouter.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.zkp.breath.arouter.InterceptorPriority
import com.zkp.breath.arouter.utils.logInitMethod
import com.zkp.breath.arouter.utils.logProcessMethod

/**
 * 1.实现类必须添加注解如下：@Interceptor(priority = 1, name = "我是拦截器SimpleIInterceptor")，priority越小越先执行，注意priority不能相同否则会报错。
 * 2.init()方法会在sdk初始化的时候调用该方法，仅会调用一次
 * 3.注意在process()不执行callback.onContinue(postcard)则会对所有跳转都进行拦截，如果有多个拦截器，那么每个
 * 拦截器都需要调用，否则会在没调用的拦截器处被拦截。
 * 4.实现IInterceptor的类不能作为其他拦截器的父类，即拦截器不存在继承关系，只能各自实现IInterceptor接口。
 * 5.这里的拦截器是面向切面编程，比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查。
 */
@Interceptor(priority = InterceptorPriority.SIMPLE_INTERCEPTOR_PRIORITY, name = "SimpleIInterceptor拦截器")
class SimpleIInterceptor : IInterceptor {

    private val TAG = "IInterceptor拦截器LogI"

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val group = postcard.group
        val path = postcard.path
        logProcessMethod("group: " + group + ",path: " + path)
        callback.onContinue(postcard)   // 继续路由
//        callback.onInterrupt(RuntimeException("我觉得有点异常"))   // 中断路由
    }

    override fun init(context: Context) {
        logInitMethod()
    }
}