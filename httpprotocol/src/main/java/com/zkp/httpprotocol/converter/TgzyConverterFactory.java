package com.zkp.httpprotocol.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Date  : 2016/12/5
 * Author: MarkChan
 * Desc  :
 */
public class TgzyConverterFactory extends Converter.Factory {

    public static TgzyConverterFactory create() {
        return create(new Gson());
    }

    public static TgzyConverterFactory create(Gson gson) {
        return new TgzyConverterFactory(gson);
    }

    private final Gson mGson;

    private TgzyConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("Gson can't be null");
        }
        mGson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new TgzyResponseBodyConverter<>(mGson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new CommonRequestBodyConverter<>(mGson, adapter);
    }
}
