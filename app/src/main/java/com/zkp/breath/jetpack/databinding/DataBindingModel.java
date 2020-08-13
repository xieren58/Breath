package com.zkp.breath.jetpack.databinding;

public class DataBindingModel {

    private int num = 0;

    public void add(ModelCallback callback) {
        callback.onSuccess(++num);//通知Presenter结果
    }

    public interface ModelCallback {//数据回调接口

        void onSuccess(int num);

        void onFailed(String text);
    }
}
