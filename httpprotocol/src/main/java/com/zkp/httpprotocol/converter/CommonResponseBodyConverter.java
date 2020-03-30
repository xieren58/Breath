package com.zkp.httpprotocol.converter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zkp.httpprotocol.HttpConstant;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class CommonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;

    CommonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mAdapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        return essenceCovert(responseBody);
    }

    private T commConvert(@NonNull ResponseBody responseBody) throws IOException {
        try {
            String response = responseBody.string().trim();
            return mAdapter.fromJson(response);
        } finally {
            responseBody.close();
        }
    }

    private T essenceCovert(@NonNull ResponseBody responseBody) {
        try {
            String response = responseBody.string().trim();

            // empty_response
            if (TextUtils.isEmpty(response)) {
                return mAdapter.fromJson(generateRespJsonStr(HttpConstant.CustomCode.RESPONSE_EMPTY, null, null));
            }

            JSONObject jsonObj = new JSONObject(response);
            String token = jsonObj.optString(HttpConstant.PROTOCOL.TOKEN);

            boolean success = jsonObj.optBoolean(HttpConstant.PROTOCOL.SUCCESS);
            // non_success
            if (!success) {
                return mAdapter.fromJson(generateRespJsonStr(HttpConstant.CustomCode.NON_SUCCESS, token, null));
            }

            String status = jsonObj.optString(HttpConstant.PROTOCOL.STATUS);
            // Responses but not ok
            if (!status.equals(HttpConstant.CommonCode.REQ_OK)) {
                return mAdapter.fromJson(generateRespJsonStr(status, token, null));
            }

            // no obj field
            if (!jsonObj.has(HttpConstant.PROTOCOL.OBJ)) {
                return mAdapter.fromJson(generateRespJsonStr(HttpConstant.CustomCode.NO_OBJ_FIELD, token, null));
            }

            //  obj field empty
            String objJson = jsonObj.optString(HttpConstant.PROTOCOL.OBJ);
            if (TextUtils.isEmpty(objJson)) {
                return mAdapter.fromJson(generateRespJsonStr(HttpConstant.CustomCode.OBJ_JSON_EMPTY, token, null));
            }

            // all success
            return mAdapter.fromJson(generateRespJsonStr(status, token, objJson));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            responseBody.close();
        }
    }

    private String generateRespJsonStr(String status, String msg, String obj) {
        return "{" +
                "\"status\":" + status + ", " +
                "\"msg\":" + "\"" + msg + "\"" + ", " +
                "\"obj\":" + obj +
                "}";
    }

}
