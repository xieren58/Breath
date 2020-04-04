package com.zkp.breath.component.activity.mvx;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zkp.breath.R;
import com.zkp.breath.designpattern.mvc.MvcCallback;
import com.zkp.breath.designpattern.mvc.MvcModelBaseInterface;
import com.zkp.breath.designpattern.mvc.MvcModelBaseInterfaceImp;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * https://www.jianshu.com/p/f17f5d981de7
 *
 * 特点:所有的通信都是单项的，v通知c，c通知m，m通知v。
 * 缺点：Activity责任不明确，十分臃肿（vc层都是Activity）；m层直接对v进行操作。这两者都是耦合性的体现
 * 优点：分割了m层和v层
 */
public class MvcActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;
    private Unbinder mBind;
    private MvcModelBaseInterface mvcModelBaseInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);

        // 创建model
        mvcModelBaseInterface = new MvcModelBaseInterfaceImp();
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        // 这里btn触发交互属于view层，而调用model进行处理属于controller层
        // 调用model进行业务处理，处理完毕model层对view层进行视图修改
        mvcModelBaseInterface.onCommonFunction(new MvcCallback() {
            @Override
            public void onCallBack(String s) {
                // 模拟ui修改
                ToastUtils.showShort(s);
            }
        });

    }
}
