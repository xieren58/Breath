package com.zkp.httpprotocol;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CookieInterceptor implements Interceptor {
    public static boolean DEBUG = BuildConfig.DEBUG;

    private static final String CONTENT_DIVIDER = "════════════════════════════════════════ Content ═══════════════════════════════════════";
    private final String cookieVlaue;

    private void logReq(String msg) {
        if (DEBUG) {
            Log.d("CookieInterceptor-Req", msg);
        }
    }

    private void logResp(String msg) {
        if (DEBUG) {
            Log.d("CookieInterceptor-Resp", msg);
        }
    }

    public CookieInterceptor(String cookieVlaue) {
        this.cookieVlaue = cookieVlaue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (!TextUtils.isEmpty(cookieVlaue)) {
            builder.addHeader("Cookie", cookieVlaue);
        }
        Request buildRequest = builder.build();
        logRequest(buildRequest);
        Response response = chain.proceed(buildRequest);
        return logResponse(response);
    }

    private void logRequest(Request request) {
        try {
            StringBuilder reqLog = new StringBuilder();
            reqLog.append("method = ").append(request.method())
                    .append("\n")
                    .append("url = ").append(request.url().toString());

            Headers headers = request.headers();
            if (headers != null && headers.size() > 0) {
                reqLog.append("\n").append("headers = ").append(headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    reqLog.append("\n").append("contentType = ").append(mediaType.toString());
                    if (isText(mediaType)) {
                        String body = bodyToString(request);
                        reqLog.append("\n")
                                .append(CONTENT_DIVIDER)
                                .append("\n")
                                .append(parseFakeJson(body));
                    }
                }
            }

            logReq(reqLog.toString());
        } catch (Exception ignored) {
        }
    }

    private Response logResponse(Response response) {
        try {
            StringBuilder respLog = new StringBuilder();

            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            respLog.append("url = ").append(clone.request().url().toString())
                    .append("\n")
                    .append("code = ").append(clone.code())
                    .append("\n")
                    .append("protocol = ").append(clone.protocol().toString())
                    .append("\n")
                    .append("message = ").append(clone.message());

            ResponseBody body = clone.body();
            boolean flag = false; // Just for print log
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    respLog.append("\n").append("contentType = ").append(mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        body = ResponseBody.create(mediaType, resp);
                        respLog.append("\n")
                                .append(CONTENT_DIVIDER)
                                .append("\n")
                                .append(parseFakeJson(resp));
                        flag = true;
                    }
                }
            }

            logResp(respLog.toString());
            if (flag) {
                return response.newBuilder().body(body).build();
            }
        } catch (Exception ignored) {
            ;
        }

        return response;
    }

    private String parseFakeJson(String fakeStr) {
        String result = parseJson(fakeStr);
        return result.equals("Log error!") ? fakeStr : result;
    }

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;

    private String parseJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content!";
        }
        try {
            if (json.startsWith("{")) {
                return new JSONObject(json).toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                return new JSONArray(json).toString(JSON_INDENT);
            }
        } catch (JSONException e) {
            String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            return message + "\n" + json;
        }
        return "Log error!";
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }

        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
            ) {
                return true;
            }
        }

        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "Something error when show requestBody.";
        }
    }

}
