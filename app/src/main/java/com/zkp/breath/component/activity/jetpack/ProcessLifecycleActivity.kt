package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityProcessBinding
import com.zkp.breath.jetpack.lifecycle.JetPackDefaultLifecycleObserverImp

/**
 * https://juejin.cn/post/6844904205614841864#heading-2
 *
 * 替换在Application中registerActivityLifecycleCallbacks，通过变量统计然后判断出是前台还是后台。其实内部原理
 * 还是registerActivityLifecycleCallbacks，然后通过变量统计进行前后台状态判断。
 *
 * 核心类：ProcessLifecycleOwner
 * 1. 该类提供了整个 app 进程的 lifecycle。
 * 2. 其中 Lifecycle.Event.ON_CREATE 只会分发一次，而 Lifecycle.Event.ON_DESTROY 则永远不会分发。
 * 3. 会分发 Lifecycle.Event.ON_START 和 Lifecycle.Event.ON_RESUME 事件 在第一个 activity 移动到这些事件时。
 * 4. Lifecycle.Event.ON_PAUSE 与 Lifecycle.Event.ON_STOP 会在最后一个 activity 移动到这些状态后 延迟 分发，
 *    该延迟足够长，以确保由于配置更改等操作重建 activity 后不会分发任何事件，对于监听应用在前后台切换且不需要毫秒级
 *    的精度的场景，这十分有用。
 *
 * 优点：
 * 1. 系统封装，减少模版代码
 * 2. 可以在任意activity，fragment，application等任意地方进行监听
 *
 * 缺点：
 * 1. 第一个activity移动到Lifecycle.Event.ON_START 和 Lifecycle.Event.ON_RESUME 事件一定会分发，因为第一个
 *    activity移动到Lifecycle.Event.ON_START 和 Lifecycle.Event.ON_RESUME的时候视为前台操作，所以会分发。
 *    但实际开发中可能需要开发者自己过滤首次回调。
 *
 * 2. Lifecycle.Event.ON_PAUSE 与 Lifecycle.Event.ON_STOP事件会延迟发送，目前无法修改延迟时间，这种情况只能
 *    对于不需要精切时间的场景才有用。如果面对播放器切后台暂停则不适用，因为会发生延迟暂停，造成不好的用户体验。
 */
class ProcessLifecycleActivity : BaseActivity() {

    private val observer by lazy { JetPackDefaultLifecycleObserverImp() }
    private lateinit var binding: ActivityProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessBinding.inflate(layoutInflater)
        addLifecycleObserver()
    }

    private fun addLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
    }

}