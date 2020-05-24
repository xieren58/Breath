package com.zkp.breath.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * https://www.jianshu.com/p/2c9bcbf092bc
 *
 * 生命周期感知组件，一般用来响应Activity、Fragment等组件的生命周期变化，并将变化通知到已注册的观察者。
 * ComponentActivity实现了LifecycleOwner接口，然后通过返回Lifecycle对象进行添观察者LifecycleObserver。
 * 在FragmentActivity可以看到调用LifecycleRegistry的handleLifecycleEvent（）方法进行事件分发。
 * 我们在其他activity只要获取Lifecycle然后添加自己的LifecycleObserver就好。
 *
 * 优势总结：响应生命周期，避免内存泄露，规范回收逻辑防止activity回收操作太多。
 * 使用场景：通常在Activity或者Fragment的ondestory我们会进行释放资源的操作，如果存在大量释放操作会导致页面的代码量上升
 * 而降低可读性，比如在我们常见mvp模式的p层，p层的操作已经要感知v层的生命周期从而避免内存泄露。
 *
 * Event：@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)。
 * State：INITIALIZED、DESTROYED、CREATED、STARTED、RESUMED。
 *
 */
class JetPackLifecycle : DefaultLifecycleObserver {

    val TAG = JetPackLifecycle::class.simpleName


    override fun onCreate(owner: LifecycleOwner) {
        Log.i(TAG, "onCreate()")
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.i(TAG, "onStart()")
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.i(TAG, "onResume()")
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.i(TAG, "onPause()")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.i(TAG, "onDestroy()")
    }

}