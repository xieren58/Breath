package com.zkp.breath;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.zkp.breath.tim.TimActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlt)
    RelativeLayout mRlt;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);

        Intent intent = new Intent(this, TimActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }
}
