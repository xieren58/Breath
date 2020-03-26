package com.zkp.breath.glide4.left;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 模拟生成的无界面的fg，该fg的生命周期和依附的activity的生命周期
 */
public class LeftFragment extends Fragment {

    // fg的生命周期会调用ActivityFragmentLifecycle对应的方法
    public ActivityFragmentLifecycle activityFragmentLifecycle = new ActivityFragmentLifecycle();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        activityFragmentLifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        activityFragmentLifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityFragmentLifecycle.onStop();
    }
}
