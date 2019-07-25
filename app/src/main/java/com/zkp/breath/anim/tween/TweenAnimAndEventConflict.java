package com.zkp.breath.anim.tween;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.zkp.breath.R;

/**
 * Created b Zwp on 2019/7/24.
 */
public class TweenAnimAndEventConflict extends RelativeLayout {

    private static final String TAG = "ConflictTag";

    public TweenAnimAndEventConflict(Context context) {
        super(context);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        TouchEventView touchEventView = new TouchEventView(this.getContext());
        touchEventView.setBackgroundColor(Color.RED);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(800, 800);
        this.addView(touchEventView, layoutParams);
        {
            RelativeLayout touchListenerView = new RelativeLayout(getContext());
            touchListenerView.setOnTouchListener(mOnTouchListener);
            touchListenerView.setBackgroundColor(Color.RED);
            layoutParams = new RelativeLayout.LayoutParams(400, 400);
            touchEventView.addView(touchListenerView, layoutParams);

            postDelayed(() -> tweenAnim(touchListenerView), 1000);
        }
    }

    private void tweenAnim(View view) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 注意：当子view设置了属性动画，需要在动画结束的时候清除动画，否则子view有设置手势事件拦截，
                    // 那么在子view所在区域内的手势事件依旧会被子view拦截（即使子view的可见性为Gone）。
                // 原因：ViewGroup#canViewReceivePointerEvents。
                // 源码：在dispatchTouchEvent（）会调用下面的方法，返回true则子view能接收到手势事件，在源码中就有
                // child.getAnimation() != null这句代码，所以要在动画结束后清除动画对象，才不会让子view获取到手势事件
//                private static boolean canViewReceivePointerEvents(@NonNull View child) {
//                    return (child.mViewFlags & VISIBILITY_MASK) == VISIBLE
//                            || child.getAnimation() != null;
//                }
                view.setVisibility(View.GONE);
                view.clearAnimation();
                // 无效
//                view.getAnimation().cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animation);
    }

    @SuppressLint("ClickableViewAccessibility")
    OnTouchListener mOnTouchListener = (v, event) -> {
        // 无论任何手势事件都获取
        Log.i(TAG, "mOnTouchListener: ");
        return true;
    };

    private static class TouchEventView extends RelativeLayout {

        public TouchEventView(Context context) {
            super(context);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.i(TAG, "onTouchEvent: ");
            // 无论任何手势事件都获取
            return true;
        }
    }
}
