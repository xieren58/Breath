package com.zkp.breath;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.zkp.breath.widget.FanProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout viewById = findViewById(R.id.rlt);

        FanProgressBar.Builder builder = new FanProgressBar.Builder();
        builder.setWidth(200);
        builder.setHeight(200);
        builder.setArcColor(Color.YELLOW);
        builder.setCirColor(Color.RED);
        final FanProgressBar fanProgressBar = new FanProgressBar(this, builder);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        viewById.addView(fanProgressBar, layoutParams);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                fanProgressBar.startAnim();
            }
        }, 2000);
    }
}
