package com.zkp.breath.jetpack.databinding;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.ToastUtils;
import com.zkp.breath.BR;

public class DataBindingViewModel extends BaseObservable {

    private String num = "0";
    private String url = "";

    // m层
    private final DataBindingModel mNumDataBindingModel;

    public DataBindingViewModel() {
        mNumDataBindingModel = new DataBindingModel();
    }

    /**
     * @Bindable注解可以提供给xml文件访问
     */
    @Bindable
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;     // 刷新ui数据
        notifyPropertyChanged(BR.num); // 通知观察者，回调
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;     // 刷新ui数据
        notifyPropertyChanged(BR.url); // 通知观察者，回调
    }

    public void onClickAdd(View v) {//点击事件处理
        ToastUtils.showShort("onClickAdd()");
        mNumDataBindingModel.add(new DataBindingModel.ModelCallback() {//相关逻辑处理，这里直接交给Model层
            @Override
            public void onSuccess(int num) {
                setNum(num + "");//成功，更新数据
            }

            @Override
            public void onFailed(String text) {
                setNum(text);//失败，更新数据
            }
        });
    }

}
