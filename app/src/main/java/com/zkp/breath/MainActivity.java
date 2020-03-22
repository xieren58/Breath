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
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }
}
