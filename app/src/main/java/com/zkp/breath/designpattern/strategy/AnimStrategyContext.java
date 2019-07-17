package com.zkp.breath.designpattern.strategy;

/**
 * Created b Zwp on 2019/7/17. 入口类/控制类</p>
 */
public class AnimStrategyContext {

    private final IAnimStrategy mIAnimStrategy;

    public AnimStrategyContext(IAnimStrategy iAnimStrategy) {
        mIAnimStrategy = iAnimStrategy;
    }

    public void anim() {
        if (mIAnimStrategy != null) {
            mIAnimStrategy.anim();
        }
    }
}
