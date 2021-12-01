package com.zkp.httpprotocol;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 替换BaseUrl的拦截器
 * 原理：将每条请求拦截下来后基于自定的规则修改baseUrl后重新提交该次请求
 */
public abstract class AbsBaseUrlSwitchInterceptor implements Interceptor {

    public static boolean DEBUG = BuildConfig.DEBUG;

    @NonNull
    abstract String customBaseUrl();

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl oldUrl = request.url();
        Request.Builder builder = request.newBuilder();
        String customBaseUrl = customBaseUrl();
        if (DEBUG) {
            Log.d("自定义BaseUrl", "customBaseUrl: " + customBaseUrl);
        }
        HttpUrl baseURL = HttpUrl.parse(customBaseUrl);

        HttpUrl newHttpUrl = oldUrl.newBuilder()
                .scheme(baseURL.scheme())//http协议如：http或者https
                .host(baseURL.host())//主机地址
                .port(baseURL.port())//端口
                .build();

        //获取处理后的新newRequest
        Request newRequest = builder.url(newHttpUrl).build();
        return chain.proceed(newRequest);
    }

}
