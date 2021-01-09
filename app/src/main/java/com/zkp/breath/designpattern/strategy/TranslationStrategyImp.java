package com.zkp.breath.designpattern.strategy;

import android.util.Log;

/**
 * Created b Zwp on 2019/7/17.
 */
public class TranslationStrategyImp implements IAnimStrategy {

    @Override
    public void anim() {
        Log.i("AlphaStrategyImp", "anim: 位移动画");
    }
}
