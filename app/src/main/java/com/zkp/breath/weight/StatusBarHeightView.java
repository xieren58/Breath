package com.zkp.breath.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import me.jessyan.autosize.utils.AutoSizeUtils;
import me.jessyan.autosize.utils.ScreenUtils;


/**
 * 状态栏高度占位View
 */
public class StatusBarHeightView extends View {

    private int mStatusBarHeight;
    private Context context;

    public StatusBarHeightView(Context context) {
        this(context, null);
    }

    public StatusBarHeightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarHeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mStatusBarHeight = ScreenUtils.getStatusBarHeight();
        if (mStatusBarHeight == 0) {
            mStatusBarHeight = AutoSizeUtils.dp2px(context, 44f);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getWidth(), mStatusBarHeight);
    }
}
