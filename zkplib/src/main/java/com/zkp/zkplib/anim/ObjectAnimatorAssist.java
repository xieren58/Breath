package com.zkp.zkplib.anim;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.ALPHA;
import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.ROTATION;
import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.SCALE_X;
import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.SCALE_Y;
import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_X;
import static com.zkp.zkplib.anim.ObjectAnimatorAssist.ObjectAnimatorType.TRANSLATION_Y;

/**
 * Created b Zwp on 2018/5/8.
 *
 * @see ObjectAnimator#setPropertyName(String)
 * @see android.animation.AnimatorSet#playTogether(Animator...) 多个动画同时执行
 * @see android.animation.AnimatorSet#playSequentially(Animator...) 多个动画依次执行
 * @see android.animation.AnimatorSet#play(Animator)
 * @see android.animation.AnimatorSet.Builder#with(Animator)
 * @see android.animation.AnimatorSet.Builder#before(Animator)
 * @see android.animation.AnimatorSet.Builder#after(Animator)
 */

public class ObjectAnimatorAssist {

    @StringDef({ALPHA, SCALE_X, SCALE_Y, TRANSLATION_X, TRANSLATION_Y, ROTATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ObjectAnimatorType {

        String ALPHA = "alpha";

        String SCALE_X = "scaleX";

        String SCALE_Y = "scaleY";

        String TRANSLATION_X = "translationX";

        String TRANSLATION_Y = "translationY";

        String ROTATION = "rotation";
    }

    private Builder mBuilder;

    private ObjectAnimatorAssist(Builder builder) {
        mBuilder = builder;
    }

    public void startAnimator() {
        if (TextUtils.isEmpty(mBuilder.getAnimatorType())) {
            throw new NonAnimatorTypeException();
        }

        switch (mBuilder.getAnimatorType()) {

            case SCALE_X:
            case SCALE_Y:
            case ALPHA:
            case ROTATION:
            case TRANSLATION_X:
            case TRANSLATION_Y:
                animator();
                break;

            default:
                break;
        }
    }

    private void animator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mBuilder.getView(),
                mBuilder.getAnimatorType(),
                mBuilder.getStartValue(),
                mBuilder.getEndValue());
        animator.setDuration(mBuilder.getDuration());
        animator.setStartDelay(mBuilder.getStartDelay());
        animator.setRepeatCount(mBuilder.getRepeatCount());
        if (mBuilder.getInterpolator() != null) {
            animator.setInterpolator(mBuilder.getInterpolator());
        }
        if (mBuilder.getAnimatorListener() != null) {
            animator.addListener(mBuilder.getAnimatorListener());
        }
        if (mBuilder.getAnimatorUpdateListener() != null) {
            animator.addUpdateListener(mBuilder.getAnimatorUpdateListener());
        }
        animator.start();
    }

    public static class Builder {

        /** 动画更新监听器 */
        private AnimatorUpdateListener mAnimatorUpdateListener;
        /** 动画监听器 */
        private AnimatorListener animatorListener;
        /** 动画类型 {@link ObjectAnimatorType} */
        private @ObjectAnimatorType String animatorType;
        /** 动画起始值 */
        private float startValue;
        /** 执行次数 */
        private int repeatCount;
        /** 延迟多久后才开始执行动画 */
        private long startDelay;
        /** 动画结束值 */
        private float endValue;
        /** 动画时长 */
        private int duration;
        /** 执行动画的view */
        private View view;
        /**
         * 速度插值器 {@linkplain AccelerateDecelerateInterpolator 先加速再减速,默认} {@linkplain
         * LinearInterpolator 匀速} {@linkplain AccelerateInterpolator 持续加速} {@linkplain
         * DecelerateInterpolator 持续减速直到 0} {@linkplain AnticipateInterpolator 先回拉一下再进行正常动画轨迹}
         * {@linkplain OvershootInterpolator 先动画会超过目标值一些，然后再弹回来} {@linkplain BounceInterpolator
         * 在目标值处弹跳,有点像玻璃球掉在地板上的效果} {@linkplain AnticipateOvershootInterpolator 开始前回拉，最后超过一些然后回弹}
         */
        private Interpolator interpolator;

        float getStartValue() {
            return startValue;
        }

        public Builder setStartValue(float startValue) {
            this.startValue = startValue;
            return this;
        }

        Interpolator getInterpolator() {
            return interpolator;
        }

        public Builder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        int getRepeatCount() {
            return repeatCount;
        }

        /**
         * 重复执行动画的次数
         *
         * @param repeatCount ‘-1’ 代表无限循环执行
         */
        public Builder setRepeatCount(int repeatCount) {
            this.repeatCount = repeatCount;
            return this;
        }

        float getEndValue() {
            return endValue;
        }

        public Builder setEndValue(float endValue) {
            this.endValue = endValue;
            return this;
        }

        int getDuration() {
            return duration;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        View getView() {
            return view;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        @ObjectAnimatorType String getAnimatorType() {
            return animatorType;
        }

        public Builder setAnimatorType(@ObjectAnimatorType String animatorType) {
            this.animatorType = animatorType;
            return this;
        }

        long getStartDelay() {
            return startDelay;
        }

        public Builder setStartDelay(long startDelay) {
            this.startDelay = startDelay;
            return this;
        }

        AnimatorListener getAnimatorListener() {
            return animatorListener;
        }

        /**
         * 设置监听器，如果不需要使用到全部回调方法请使用空实现类 SimpleAnimatorListener
         *
         * @param animatorListener {@link SimpleAnimatorListener}
         */
        public Builder setAnimatorListener(AnimatorListener animatorListener) {
            this.animatorListener = animatorListener;
            return this;
        }

        AnimatorUpdateListener getAnimatorUpdateListener() {
            return mAnimatorUpdateListener;
        }

        public Builder setAnimatorUpdateListener(AnimatorUpdateListener animatorListener) {
            this.mAnimatorUpdateListener = animatorListener;
            return this;
        }

        public ObjectAnimatorAssist build() {
            return new ObjectAnimatorAssist(this);
        }
    }

    /**
     * 动画监听器，空实现，所需到的方法才进行重写
     *
     * @see AnimatorListenerAdapter 可以用这个，也是空实现
     */
    public static class SimpleAnimatorListener implements AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            // 当动画开始执行时，这个方法被调用。
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // 当动画结束时，这个方法被调用。
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // 当动画被通过 cancel() 方法取消时，这个方法被调用。
            // 需要说明一下的是，就算动画被取消，onAnimationEnd() 也会被调用。
            // 所以当动画被取消时，如果设置了 AnimatorListener，那么 onAnimationCancel() 和
            // onAnimationEnd() 都会被调用。onAnimationCancel() 会先于 onAnimationEnd() 被调用。
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // 当动画通过 setRepeatMode() / setRepeatCount() 或 repeat() 方法重复执行时，这个方法被调用。
            // 由于 ViewPropertyAnimator 不支持重复，所以这个方法对 ViewPropertyAnimator 相当于无效。
        }
    }

    private static class NonAnimatorTypeException extends NullPointerException {

        public NonAnimatorTypeException() {
            super("无指定动画类型异常");
        }
    }
}



