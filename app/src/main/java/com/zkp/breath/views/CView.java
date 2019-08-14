package com.zkp.breath.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created b Zwp on 2019/8/13.
 */
public class CView extends View {

    public CView(Context context) {
        this(context, null);
    }

    public CView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("EventLog", "CViwe_dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("EventLog", "CViwe_onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
