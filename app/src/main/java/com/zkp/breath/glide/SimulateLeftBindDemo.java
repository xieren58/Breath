package com.zkp.breath.glide;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

// Glide.with(Activity activity)的生命周期绑定原理，内部的代码都是一些伪代码，不要运行，会报错
// 总结可以这么认为：生成了一个无界面的Fg去响应Activity的生命周期，在Fg的对应生命周期的方法再去通知Glide当前是否加载资源或者停止加载资源
public class SimulateLeftBindDemo {

    private void f1(AppCompatActivity activity) {
        // 生成和activity生命周期关联的无界面Fg
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        LeftFragment current = (LeftFragment) supportFragmentManager.findFragmentByTag("ActivityFragmentLifecycle");

        //
        assert current != null;
        RequestManager requestManager = new RequestManager(current.activityFragmentLifecycle, Glide.get(activity));
    }
}
