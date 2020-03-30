package com.zkp.httpprotocol.bean;


import java.util.List;

/**
 * 精华版，只返回所需字段
 */
public class EssenceListHttpResponse<T> {

    private String status;
    private String token;
    private List<T> obj;

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

    public List<T> getObj() {
        return obj;
    }

    public void setObj(List<T> obj) {
        this.obj = obj;
    }
}
