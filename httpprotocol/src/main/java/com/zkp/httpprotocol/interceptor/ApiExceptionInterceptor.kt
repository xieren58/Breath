package com.zkp.httpprotocol.interceptor

import com.blankj.utilcode.util.GsonUtils
import com.zkp.httpprotocol.bean.ApiResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 响应拦截器，如果需要对接口请求进行异常log日志上传。
 */
class ApiExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val builder: Response.Builder = response.newBuilder()
        val clone: Response = builder.build()

        val url = request.url.toString()

        val code = clone.code
        if (code != 200) { // 服务器异常 上报
//            BuglyReport.report("ApiExceptionInterceptor[服务器层 code != 200]", "msg: ${response.message} , code: $code , url: $url")
        } else if (code == 200) { // 请求是正常的 ，处理 业务异常 上报
            var flag = false // Just for print log
            var body = clone.body
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    try {
                        val resp = body.string()
                        body = resp.toResponseBody(mediaType)
                        flag = true
                        val apiResponse = GsonUtils.fromJson(resp, ApiResponse::class.java)
                        if (apiResponse != null) {
                            val apiCode = apiResponse.code
                            if (apiCode != 200) { // 只处理异常情况
//                                BuglyReport.report("ApiExceptionInterceptor[业务层 code != 200]", "msg: ${apiResponse.msg} , code: $apiCode , url: $url")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
//                        LogUtils.d("拦截响应 --- 解析异常 ${e.message}")
                    }
                }
            }
            if (flag) return response.newBuilder().body(body).build()
        }
        return response
    }

}