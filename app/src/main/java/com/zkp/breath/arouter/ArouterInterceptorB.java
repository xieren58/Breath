package com.zkp.breath.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

@Interceptor(priority = 2, name = "测试的拦截器ArouterInterceptorB")
public class ArouterInterceptorB implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group = postcard.getGroup();
        String path = postcard.getPath();
        Log.i("IInterceptor", "ArouterInterceptorB_process_group: " + group + ",path: " + path);
        // 如果不添加这行代码，则会拦截跳转。
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Log.i("IInterceptor", "ArouterInterceptorB_init: ");
    }
}
