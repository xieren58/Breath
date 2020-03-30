package com.zkp.httpprotocol;


import com.zkp.httpprotocol.converter.CommonConverterFactory;
import com.zkp.httpprotocol.converter.TgzyConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * API远程请求Service生成器
 * Created by zwp on 16/6/30.
 */
public class ServiceGenerator {

    private static OkHttpClient sOkHttpClient;

    static {
        Builder okHttpClientBuilder = new Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(new LoggerInterceptor());
        }

        sOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

    public static <S> S createOld(String endPoint, Class<S> serviceClass) {
        return create(sOkHttpClient, endPoint, serviceClass, CommonConverterFactory.create());
    }

    public static <S> S createOld(OkHttpClient client, String endPoint, Class<S> serviceClass) {
        return create(client, endPoint, serviceClass, CommonConverterFactory.create());
    }

    public static <S> S createOld(String endPoint, Class<S> serviceClass, Converter.Factory factory) {
        return create(sOkHttpClient, endPoint, serviceClass, factory);
    }

    public static <S> S createNew(OkHttpClient client, String endPoint, Class<S> serviceClass) {
        return create(client, endPoint, serviceClass, TgzyConverterFactory.create());
    }

    public static <S> S createNew(String endPoint, Class<S> serviceClass) {
        return create(sOkHttpClient, endPoint, serviceClass, TgzyConverterFactory.create());
    }

    public static <S> S create(OkHttpClient client, String endPoint, Class<S> serviceClass,
                               Converter.Factory factory) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(client)
                .baseUrl(endPoint)
                .addConverterFactory(factory);

        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
