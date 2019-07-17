package com.zkp.zkplib.gson;

import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created b Zwp on 2017/12/4.
 */

public final class GsonAssist {

    public synchronized static <T> String generateJson(T jsonContent) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(jsonContent);
    }

    public synchronized static <T> boolean generateJsonToLocalSD(@NonNull String jsonPath,
                                                                 T jsonContent) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return generateJsonToLocalSDByCustomGson(gson, jsonPath, jsonContent);
    }

    public synchronized static <T> boolean generateJsonToLocalSDBySerializeNulls(
            @NonNull String jsonPath, T jsonContent) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        return generateJsonToLocalSDByCustomGson(gson, jsonPath, jsonContent);
    }

    public synchronized static <T> boolean generateJsonToLocalSDByCustomGson(@NonNull Gson gson,
                                                                             @NonNull String jsonPath,
                                                                             T jsonContent) {
        String json = gson.toJson(jsonContent);
        return FileIOUtils.writeFileFromString(jsonPath, json);
    }

    public synchronized static <T> T jsonToBean(String jsonContent, Class<T> cls) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonContent, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToListBean(String jsonContent, Class<T> cls) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonContent, new TypeToken<List<T>>() {}.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, T> jsonToMapBean(String jsonContent) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonContent, new TypeToken<Map<String, T>>() {}.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
