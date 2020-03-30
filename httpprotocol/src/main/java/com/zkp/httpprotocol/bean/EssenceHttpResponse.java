package com.zkp.httpprotocol.bean;


/**
 * 精华版，只返回所需字段
 */
public class EssenceHttpResponse<T> {

    private String status;
    private String token;
    private T obj;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
