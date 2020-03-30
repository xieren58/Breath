package com.zkp.httpprotocol.converter;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class TgzyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;

    TgzyResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mAdapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        return commConvert(responseBody);
    }

    private T commConvert(@NonNull ResponseBody responseBody) throws IOException {
        try {
            String response = responseBody.string().trim();
            return mAdapter.fromJson(response);
        } finally {
            responseBody.close();
        }
    }

}
