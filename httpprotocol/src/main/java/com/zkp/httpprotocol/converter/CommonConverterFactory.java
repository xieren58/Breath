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
public class CommonConverterFactory extends Converter.Factory {

    public static CommonConverterFactory create() {
        return create(new Gson());
    }

    public static CommonConverterFactory create(Gson gson) {
        return new CommonConverterFactory(gson);
    }

    private final Gson mGson;

    private CommonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("Gson can't be null");
        }
        mGson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new CommonResponseBodyConverter<>(mGson, adapter);
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
