package com.zkp.zkplib.utils;

import android.view.View;

import com.zkp.zkplib.anim.ObjectAnimatorAssist;

/**
 * Created b Zwp on 2018/5/29.
 * Note:方法名字以By结尾则表明在当前位置进行叠加
 *
 * @see ObjectAnimatorAssist
 * @see ObjectAnimatorAssist.SimpleAnimatorListener 监听器相同，但是onAnimationRepeat()
 *                                                  方法在ViewPropertyAnimator中不起作用
 * @deprecated
 */

public class ViewPropertyAnimatorAssist {

    /**
     * @see ViewMomentAssiast#setAlpha(View, float)
     */
    public static void alpha(View view, float value) {
        view.animate().alpha(value);
    }

    public static void alphaBy(View view, float value) {
        view.animate().alphaBy(value);
    }

    /**
     * @see ViewMomentAssiast#setTranslationX(View, float) view.animate().translationZ需要minSdkVersion为21
     */
    public static void translationX(View view, float value) {
        view.animate().translationX(value);
    }

    public static void translationXBy(View view, float value) {
        view.animate().translationXBy(value);
    }

    /**
     * @see ViewMomentAssiast#setTranslationY(View, float)
     */
    public static void translationY(View view, float value) {
        view.animate().translationY(value);
    }

    public static void translationYBy(View view, float value) {
        view.animate().translationYBy(value);
    }

    /**
     * @see ViewMomentAssiast#setX(View, float) view.animate().z()需要minSdkVersion为21
     */
    public static void x(View view, float value) {
        view.animate().x(value);
    }

    public static void xBy(View view, float value) {
        view.animate().xBy(value);
    }

    /**
     * @see ViewMomentAssiast#setY(View, float)
     */
    public static void y(View view, float value) {
        view.animate().y(value);
    }

    public static void yBy(View view, float value) {
        view.animate().yBy(value);
    }

    /**
     * @see ViewMomentAssiast#setRotation(View, float)
     */
    public static void rotation(View view, float value) {
        view.animate().rotation(value);
    }

    public static void rotationBy(View view, float value) {
        view.animate().rotationBy(value);
    }

    /**
     * @see ViewMomentAssiast#setTranslationX(View, float)
     */
    public static void rotationX(View view, float value) {
        view.animate().rotationX(value);
    }

    public static void rotationXBy(View view, float value) {
        view.animate().rotationXBy(value);
    }

    /**
     * @see ViewMomentAssiast#setRotationY(View, float)
     */
    public static void rotationY(View view, float value) {
        view.animate().rotationY(value);
    }

    public static void rotationYBy(View view, float value) {
        view.animate().rotationYBy(value);
    }

    /**
     * @see ViewMomentAssiast#setScaleX(View, float)
     */
    public static void scaleX(View view, float value) {
        view.animate().scaleX(value);
    }

    public static void scaleXBy(View view, float value) {
        view.animate().scaleXBy(value);
    }

    /**
     * @see ViewMomentAssiast#setScaleY(View, float)
     */
    public static void scaleY(View view, float value) {
        view.animate().scaleY(value);
    }

    public static void scaleYBy(View view, float value) {
        view.animate().scaleYBy(value);
    }
}
