package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLiferecycleBinding
import com.zkp.breath.jetpack.lifecycle.JetPackDefaultLifecycleObserverImp
import com.zkp.breath.jetpack.lifecycle.JetPackLifecycleEventObserverImp


/**
 * https://www.jianshu.com/p/2c9bcbf092bc
 * https://juejin.cn/post/6844904111108800519#heading-4
 *
 * 生命周期感知组件，一般用来响应Activity、Fragment等组件的生命周期变化，并将变化通知到已注册的观察者。
 * ComponentActivity实现了LifecycleOwner接口，然后通过返回Lifecycle对象进行添观察者LifecycleObserver。
 * 在FragmentActivity可以看到调用LifecycleRegistry的handleLifecycleEvent（）方法进行事件分发。
 * 我们在其他activity只要获取Lifecycle然后添加自己的LifecycleObserver就好。
 * 其实实现原理就是一种观察者模式，也就是回调。
 *
 * 优势总结：响应生命周期，避免内存泄露，规范回收逻辑防止activity回收操作太多。
 * 使用场景：通常在Activity或者Fragment的ondestory我们会进行释放资源的操作，如果存在大量释放操作会导致页面的代码量上升
 * 而降低可读性
 *
 * Event：@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)。
 * State：INITIALIZED、DESTROYED、CREATED、STARTED、RESUMED。
 *
 */
class LifecycleActivity : BaseActivity() {

    private lateinit var binding: ActivityLiferecycleBinding
    private lateinit var jetPackDefaultLifecycleObserverImp: JetPackDefaultLifecycleObserverImp
    private lateinit var jetPackLifecycleEventObserverImp: JetPackLifecycleEventObserverImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        jetPackDefaultLifecycleObserverImp = JetPackDefaultLifecycleObserverImp()
        jetPackLifecycleEventObserverImp = JetPackLifecycleEventObserverImp()

        // 添加生命周期观察者
        lifecycle.addObserver(jetPackDefaultLifecycleObserverImp)
        lifecycle.addObserver(jetPackLifecycleEventObserverImp)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除生命周期观察者
        lifecycle.removeObserver(jetPackDefaultLifecycleObserverImp)
        lifecycle.removeObserver(jetPackLifecycleEventObserverImp)
    }


}