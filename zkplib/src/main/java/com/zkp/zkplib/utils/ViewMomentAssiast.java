package com.zkp.zkplib.utils;

import android.view.View;

/**
 * Created b Zwp on 2018/5/29.
 *
 * @author zwp 动画其实就是相当于在一段时间将指定的value值等份分割， 然后不断调用下面的方法刷新ui进行改变。
 * @see View#postDelayed(Runnable, long)
 */

public class ViewMomentAssiast {

    // 设置透明度。常用，UI只给一张常规状态图的情况下可重写onTouchEvent()在点击和抬起状态下进行设置alpha数值
    public static void setAlpha(View view, float value) {
        view.setAlpha(value);
    }

    // 偏移X。setTranslationZ()需要minSdkVersion为21
    public static void setTranslationX(View view, float value) {
        view.setTranslationX(value);
    }

    // 偏移Y
    public static void setTranslationY(View view, float value) {
        view.setTranslationY(value);
    }

    // 设置X轴绝对位置。setZ()需要minSdkVersion为21
    public static void setX(View view, float value) {
        view.setX(value);
    }

    // 设置Y轴绝对位置
    public static void setY(View view, float value) {
        view.setY(value);
    }

    public static void setRotation(View view, float value) {
        view.setRotation(value);
    }

    public static void setRotationX(View view, float value) {
        view.setRotationX(value);
    }

    public static void setRotationY(View view, float value) {
        view.setRotationY(value);
    }

    public static void setScaleX(View view, float value) {
        view.setScaleX(value);
    }

    public static void setScaleY(View view, float value) {
        view.setScaleY(value);
    }
}
