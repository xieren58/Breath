package com.zkp.breath.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * 1.当程序执行的时候所有拦截器的init（）都会被执行，priority越小越先执行，注意priority不能相同否则会报错。
 * 2.注意在process()不执行callback.onContinue(postcard)则会对所有跳转都进行拦截，如果有多个拦截器，那么每个
 * 拦截器都需要调用，否则会在没调用的拦截器处被拦截。
 */
@Interceptor(priority = 1, name = "测试的拦截器ArouterInterceptorA")
public class ArouterInterceptorA implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group = postcard.getGroup();
        String path = postcard.getPath();
        Log.i("IInterceptor", "ArouterInterceptorA_process_group: " + group + ",path: " + path);
        // 如果不添加这行代码，则会拦截跳转。
        callback.onContinue(postcard);
//        callback.onInterrupt(new NullPointerException("xxx"));
    }

    @Override
    public void init(Context context) {
        Log.i("IInterceptor", "ArouterInterceptorA_init: ");
    }
}
