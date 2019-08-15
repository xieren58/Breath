package com.zkp.breath.views.events;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created b Zwp on 2019/8/13.
 * 1.执行顺序dispatchTouchEvent - onInterceptTouchEvent - onTouchEvent。
 * 2.三个方法默认返回值情况下，down事件经过三个方法，当onTouchEvent()方法没有消费DOWN事件则剩余事件不会传入，反之
 * move和up事件经过dispatchTouchEvent和onTouchEvent方法。
 * 3.三个方法默认返回值且有子view情况下，down事件经过本类的dispatchTouchEvent和onInterceptTouchEvent，然后经过
 * 子view的dispatchTouchEvent和当onTouchEvent，最后才是奔雷的onTouchEvent。
 * 4.父view分发事件给子view，会从最后添加【addView()】的view开始分发，在ui上看就是盖在最上面的view，然后判断当前
 * 子view的canViewReceivePointerEvents（）和isTransformedTouchPointInView（），如果不满足则跳过该view，
 * 即不分发到该子view。
 * 5.父view的onTouchEvent()消费了down事件，那么move和up就不再分发给子view了。但是子view的onTouchEvent()消费
 * 了down事件，不会走父view的那么onTouchEvent()，move和up还是会经过父view的dispatchTouchEvent（）
 * 和onInterceptTouchEvent（）
 */
public class AViewGroup extends FrameLayout {

    public AViewGroup(Context context) {
        this(context, null);
    }

    public AViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i("AViewGroup", "onInterceptTouchEvent: ");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("EventLog", "AViewGroup_onInterceptTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("EventLog", "AViewGroup_onInterceptTouchEvent: ACTION_MOVE");
//                break;
                return true;
            case MotionEvent.ACTION_UP:
                Log.i("EventLog", "AViewGroup_onInterceptTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i("AViewGroup", "dispatchTouchEvent: ");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("EventLog", "AViewGroup_dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("EventLog", "AViewGroup_dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("EventLog", "AViewGroup_dispatchTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("EventLog", "AViewGroup_onTouchEvent: ACTION_DOWN");
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("EventLog", "AViewGroup_onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("EventLog", "AViewGroup_onTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
