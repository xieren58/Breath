package com.zkp.breath.designpattern.strategy;

/**
 * Created b Zwp on 2019/7/17. 入口类/控制类</p>
 * 使用Context是为了和Client解耦，具体操作不依赖于Client，只提供方法给Client调用
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
