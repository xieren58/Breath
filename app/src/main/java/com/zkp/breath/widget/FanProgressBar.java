package com.zkp.breath.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.core.view.ViewCompat;

/**
 * Created b Zwp on 2019/7/17.
 */

@SuppressLint("ViewConstructor")
public class FanProgressBar extends View {

    /** 默认时长 */
    private static final long DEFAULT_DURATION = 5000;

    /** 控件宽高 */
    private int mW;
    private int mH;
    /** 当前扇形扫过的进度（度数） */
    private float mProgress;
    private long mAnimDuration = DEFAULT_DURATION;

    private RectF mOval;
    private Paint mPaintCir;
    private Paint mPaintArc;

    private Builder mBuilder;
    private ValueAnimator mValueAnimator;

    public FanProgressBar(Context context, Builder builder) {
        super(context, null);
        mBuilder = builder;
        initPaint();
        initBuilder();
    }

    private void initPaint() {
        mPaintCir = new Paint();
        mPaintCir.setAntiAlias(true);
        mPaintCir.setColor(Color.RED);
        // 默认是fill
        mPaintCir.setStyle(Paint.Style.FILL);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(Color.YELLOW);
    }

    private void initBuilder() {
        if (mBuilder != null) {
            mAnimDuration = mBuilder.getAnimDuration();
            if (mAnimDuration <= 0) {
                mAnimDuration = DEFAULT_DURATION;
            }

            int arcColor = mBuilder.getArcColor();
            if (arcColor != -1) {
                mPaintArc.setColor(arcColor);
            }

            int cirColor = mBuilder.getCirColor();
            if (cirColor != -1) {
                mPaintCir.setColor(cirColor);
            }
        }
    }

    public void startAnim() {
        mProgress = 0;

        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
        mValueAnimator.addListener(mAnimatorListenerAdapter);
        mValueAnimator.setDuration(mAnimDuration);
        mValueAnimator.start();
    }

    AnimatorUpdateListener mAnimatorUpdateListener = new AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            // [0,1]
            float animatedValue = (float) animation.getAnimatedValue();
            Log.i("FanProgressBar", "onAnimationUpdate: " + animatedValue);
            // 一个圆360°
            mProgress = animatedValue * 360;
            ViewCompat.postInvalidateOnAnimation(FanProgressBar.this);
        }
    };

    AnimatorListenerAdapter mAnimatorListenerAdapter = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {

            EventListener eventListener = mBuilder.getEventListener();
            if (eventListener != null) {
                eventListener.onComplete();
            }
        }
    };

    public void stopAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("FanProgressBar", "onDraw: ");
        // 背景色
        // 减去1f是因为让前景比背景大，盖住背景。在不-1f的情况下会出现背景边，暂时不知道是什么问题导致的？？！！
        canvas.drawCircle(mW / 2f, mH / 2f, mW / 2f - 1f, mPaintCir);
        // 前景色
        canvas.drawArc(mOval, -90, mProgress, true, mPaintArc);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("FanProgressBar", "onSizeChanged: ");
        mW = w;
        mH = h;

        mOval = new RectF(0, 0, mW, mH);
    }

    public static class Builder {

        int cirColor = -1;
        int arcColor = -1;
        long animDuration;

        EventListener mEventListener;

        int getCirColor() {
            return cirColor;
        }

        public void setCirColor(int cirColor) {
            this.cirColor = cirColor;
        }

        int getArcColor() {
            return arcColor;
        }

        public void setArcColor(int arcColor) {
            this.arcColor = arcColor;
        }

        long getAnimDuration() {
            return animDuration;
        }

        public void setAnimDuration(long animDuration) {
            this.animDuration = animDuration;
        }

        EventListener getEventListener() {
            return mEventListener;
        }

        public void setEventListener(EventListener eventListener) {
            mEventListener = eventListener;
        }
    }

    public interface EventListener {

        /**
         * 动画执行完毕
         */
        void onComplete();
    }
}

