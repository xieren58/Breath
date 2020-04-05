package com.zkp.breath.arouter.simple;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * 监听一次跳转的导航回调
 *
 * @see Postcard#navigation(Context, NavigationCallback)
 */
public class SimpleNavigationCallback implements NavigationCallback {

    /**
     * 发现跳转的path是存在的
     */
    @Override
    public void onFound(Postcard postcard) {

    }

    /**
     * 跳转的path是不存在的（无效）
     */
    @Override
    public void onLost(Postcard postcard) {

    }

    /**
     * 完成跳转后回调（到达指定的目标）
     */
    @Override
    public void onArrival(Postcard postcard) {

    }

    /**
     * 执行下面的代码后回调，一般都是在拦截器执行，表示此次跳转事件被拦截
     *
     * @see com.alibaba.android.arouter.facade.template.IInterceptor#process(Postcard, InterceptorCallback)
     * @see com.alibaba.android.arouter.facade.callback.InterceptorCallback#onInterrupt(Throwable)
     */
    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
