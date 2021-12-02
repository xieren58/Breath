package com.zkp.httpprotocol.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 头部添加拦截器
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        // 添加所需的header
        builder.addHeader("xxxx", "yyyyy")
        val request = builder.build()
        return chain.proceed(request)
    }
}