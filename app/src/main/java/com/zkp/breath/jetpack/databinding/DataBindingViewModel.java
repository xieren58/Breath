package com.zkp.breath.jetpack.databinding;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.ToastUtils;
import com.zkp.breath.BR;

public class DataBindingViewModel extends BaseObservable {

    private String num = "0";

    private final DataBindingModel mNumDataBindingModel;

    public DataBindingViewModel() {
        mNumDataBindingModel = new DataBindingModel();
    }

    @Bindable
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
        notifyPropertyChanged(BR.num);//更新UI
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
