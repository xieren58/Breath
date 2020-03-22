package com.zkp.breath;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.zkp.breath.component.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rlt)
    RelativeLayout mRlt;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);

//        ActivityUtils.startActivity(ActivityCoordinator.class);

        // DisplayMetrics
        // px = dp * density(dpi / 160)
        // 1080* 1920 ，设计稿是360dp
        // 1080 = 360 * 3
        // density(3) = 1080  / 360  （其实就是不按照传统的计算方案，直接按照我们的宽为基准和设计稿的dp算出density）
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }
}
