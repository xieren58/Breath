package com.zkp.breath.jetpack.databinding;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.ToastUtils;
import com.zkp.breath.BR;

/**
 * notifyPropertyChanged()： 只更新对应 BR 的 flag（ui中引用这些变量的会发生刷新），该 BR 的生成通过注解
 * Bindable 生成，并且回调监视器告知是哪个BR的flag发生了改变。
 * notifyChange() 刷新所有的值域（ui中引用这些变量的会发生刷新），并且回调监听器。
 */
public class DataBindingViewModel extends BaseObservable {

    /**
     * 如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
     */
    @Bindable
    public String name;
    /**
     * 如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
     */
    private String num = "0";

    // m层
    private final DataBindingModel mNumDataBindingModel;

    public DataBindingViewModel() {
        mNumDataBindingModel = new DataBindingModel();
    }

    @Bindable
    public String getNum() {
        return num;
    }

    /**
     * 下面的操作缺一不可，刷新数据后通知改变，ui同步刷新
     */
    public void setNum(String num) {
        this.num = num;
        this.name = "我是notifyChange";
        if (Long.parseLong(num) % 2 == 0) {
            notifyPropertyChanged(BR.num);
        } else {
            notifyChange();  //更新所有字段
        }
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
