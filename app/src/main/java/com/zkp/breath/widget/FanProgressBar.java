package com.zkp.breath.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;

/**
 * Created b Zwp on 2019/7/17.
 */

@SuppressLint("ViewConstructor")
public class FanProgressBar extends View {

    private static final long DEFAULT_DURATION = 5000;

    private Builder mBuilder;
    //当前进度
    private float mProgress = 0;
    // 实心圆画笔
    private Paint mPaintCir;
    // 扇形画笔
    private Paint mPaintArc;
    // 扇形指定的矩形范围
    private RectF mOval;
    private int mW;
    private int mH;
    private long mAnimDuration;
    private ValueAnimator mValueAnimator;

    public FanProgressBar(Context context, Builder builder) {
        super(context, null);
        mBuilder = builder;
        initBuilder();
        initPaint();
        initRect();
    }

    private void initBuilder() {
        if (mBuilder == null) {
            throw new NullPointerException("FanProgressBar");
        }

        if (mBuilder.getWidth() <= 0 || mBuilder.getHeight() <= 0) {
            throw new IllegalArgumentException("FanProgressBar");
        }

        mAnimDuration = mBuilder.getAnimDuration();
        if (mAnimDuration <= 0) {
            mAnimDuration = DEFAULT_DURATION;
        }
    }

    private void initPaint() {
        mPaintCir = new Paint();
        mPaintCir.setAntiAlias(true);
        mPaintCir.setColor(mBuilder.getCirColor());
        // 默认是fill
        mPaintCir.setStyle(Paint.Style.FILL);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(mBuilder.getArcColor());
    }

    private void initRect() {
        mOval = new RectF(0, 0,
                mBuilder.getWidth(),
                mBuilder.getHeight());
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
            float animatedValue = (float) animation.getAnimatedValue();
            Log.i("打印信息", "onAnimationUpdate: " + animatedValue);
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
        // 背景色
        canvas.drawCircle(mW / 2f, mH / 2f, mW / 2f, mPaintCir);
        // 前景色
        canvas.drawArc(mOval, -90, mProgress, true, mPaintArc);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBuilder.getWidth(), mBuilder.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
    }

    public static class Builder {

        @Px
        int width;
        @Px
        int height;
        @ColorInt
        int cirColor;
        @ColorInt
        int arcColor;

        long animDuration;

        EventListener mEventListener;

        int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

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

