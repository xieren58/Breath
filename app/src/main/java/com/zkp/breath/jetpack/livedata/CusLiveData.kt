package com.zkp.breath.jetpack.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * 扩展LiveData的优势：
 * 1. 理解onInactive()和onInactive()的触发机制，可以做一些实时的刷新操作。
 * 2. 针对一些公有操作（比如监听网络状态），也可以把类改为单例模式，达到资源共享的功能或者也可以说群发消息的功能。
 */
class CusLiveData : LiveData<String>() {

    // 当 LiveData 对象具有活跃（STARTED或者RESUMED）观察者时，会调用 onActive() 方法。（会多次触发）
    // 应用场景：Jane首页的广告刷新：只要回到首页（活跃状态）就要触发重刷广告数据的逻辑。
    override fun onActive() {
        super.onActive()
        Log.i("CusLiveData", "onActive()")
        if (!NetworkUtils.isRegisteredNetworkStatusChangedListener(listener)) {
            NetworkUtils.registerNetworkStatusChangedListener(listener)
        }
    }

    // 当 LiveData 对象没有任何活跃观察者时，会调用 onInactive() 方法。（会多次触发）
    override fun onInactive() {
        super.onInactive()
        Log.i("CusLiveData", "onInactive()")
        NetworkUtils.unregisterNetworkStatusChangedListener(listener)
    }

    val listener = object : NetworkUtils.OnNetworkStatusChangedListener {
        override fun onConnected(networkType: NetworkUtils.NetworkType?) {
            ToastUtils.showShort("")
            value = "网络已连接"
        }

        override fun onDisconnected() {
            value = "网络已断开"
        }
    }

}