package com.zkp.breath.glide;

import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;

public class ActivityFragmentLifecycle implements Lifecycle {

    // ActivityFragmentLifecycle对应的方法会调用传入listener的对应方法
    private LifecycleListener listener;

    @Override
    public void addListener(LifecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(LifecycleListener listener) {
        listener = null;
    }

    void onStart() {
        listener.onStart();
    }

    void onStop() {
        listener.onStop();
    }

    void onDestroy() {
        listener.onDestroy();
    }
}
