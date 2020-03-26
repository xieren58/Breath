package com.zkp.breath.glide4.left;

import com.bumptech.glide.Glide;
import com.bumptech.glide.manager.LifecycleListener;

public class RequestManager implements LifecycleListener {

    private final ActivityFragmentLifecycle activityFragmentLifecycle;
    private Glide glide;

    public RequestManager(ActivityFragmentLifecycle activityFragmentLifecycle, Glide glide) {
        this.activityFragmentLifecycle = activityFragmentLifecycle;
        this.glide = glide;
        // 和fg的生命周期关联了起来
        activityFragmentLifecycle.addListener(this);
    }

    @Override
    public void onStart() {
        // 通知Glide可以开始
    }

    @Override
    public void onStop() {
        // 通知Glide应该要暂停
    }

    @Override
    public void onDestroy() {
        // 通知glide和当前activity解决关联
        // 做一些回收的操作
    }
}
