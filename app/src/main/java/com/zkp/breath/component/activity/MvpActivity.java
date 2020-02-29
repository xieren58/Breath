package com.zkp.breath.component.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zkp.breath.R;
import com.zkp.breath.designpattern.mvp.IMvpPresenter;
import com.zkp.breath.designpattern.mvp.IMvpView;
import com.zkp.breath.designpattern.mvp.MvpPresenterImp;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 特点：通信都是双向的，v和p双向，p和m双向
 * 优点：彻底分割m和v，所有交互都需要经过p来传达；业务逻辑抽取到p层，使v层更加简洁；方便对p层进行单元测试；p
 * 层可以用于多个v层（Activity）。
 * 缺点：p层过于沉重；当功能复杂则v的接口过于庞大。
 */
public class MvpActivity extends AppCompatActivity implements IMvpView {

    @BindView(R.id.btn)
    Button btn;
    private Unbinder mBind;
    private IMvpPresenter iMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        iMvpPresenter = new MvpPresenterImp(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        iMvpPresenter.requestData();
    }

    @Override
    public void refreshUi(String s) {
        ToastUtils.showShort(s);
    }
}
