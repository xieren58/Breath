package com.zkp.breath.component.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zkp.breath.R;
import com.zkp.breath.component.activity.base.BaseActivity;
import com.zkp.breath.views.events.AViewGroup;
import com.zkp.breath.views.events.BViwe;
import com.zkp.breath.views.events.CView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created b Zwp on 2019/8/13.
 */
public class EventActivity extends BaseActivity {

    @BindView(R.id.b_v)
    BViwe mBV;
    @BindView(R.id.c_v)
    CView mCV;
    @BindView(R.id.a_vg)
    AViewGroup mAVg;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mBind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
