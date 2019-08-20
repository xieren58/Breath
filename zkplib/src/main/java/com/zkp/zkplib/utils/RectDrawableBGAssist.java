package com.zkp.zkplib.utils;

import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;

/**
 * Created b Zwp on 2019/8/20.
 */
public class RectDrawableBGAssist {

    public static void rectDrawable(int strokeWidth, @ColorInt int color) {
        roundRectDrawable(strokeWidth, color, 0);
    }

    public static void roundRectDrawable(int strokeWidth, @ColorInt int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(strokeWidth, color);
        drawable.setCornerRadius(radius);
    }
}
