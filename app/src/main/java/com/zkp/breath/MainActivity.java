package com.zkp.breath;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.zkp.zkplib.widget.RoundRectProgress;
import com.zkp.zkplib.widget.RoundRectProgress.Builder;
import com.zkp.zkplib.widget.RoundRectProgress.Direction;

import androidx.appcompat.app.AppCompatActivity;
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

        RoundRectProgress.Builder builder = new Builder();
        builder.setStrokeWidth(20);
        builder.setRadiusXy(0);
        builder.setRectFlag(true);
        builder.setDirection(Direction.TOP_CENTER_CW);
        builder.setBgColor(Color.BLACK);
        builder.setFgColor(Color.YELLOW);
        RoundRectProgress roundRectProgress = new RoundRectProgress(getBaseContext(), builder);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(800, 500);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRlt.addView(roundRectProgress, layoutParams);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            Log.i("animatedValue ", "onCreate: " + animatedValue);
            roundRectProgress.setProgress(animatedValue);
        });
        valueAnimator.setStartDelay(2000);
        valueAnimator.start();

    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }
}
