package com.zkp.breath.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯
 * https://www.cnblogs.com/dasusu/p/6593991.html
 */
public class MarqueTextView extends TextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }


    @Override
    public boolean isFocused() {
        //就是把这里返回true即可
        return true;
    }
}