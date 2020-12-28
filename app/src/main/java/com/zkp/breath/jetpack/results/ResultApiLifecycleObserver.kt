package com.zkp.breath.jetpack.results

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


/**
 * 在非Activity的环境下配合LifecycleObserver实现监听，因为LifecycleOwner 会在 Lifecycle 被销毁时自动移除已
 * 注册的启动器，可以使用ActivityResultLauncher.unregister() 手动移除。
 *
 * 核心：获取从Activity获得的ActivityResultRegistry，然后register自己的监听。
 */
class ResultApiLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {

    val TAG = ResultApiLifecycleObserver::class.simpleName

    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        Log.i(TAG, "onCreate()")
        launcher = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
            Log.i(TAG, "uri: $uri")
        }
    }

    fun selectImage() {
        launcher.launch("image/*")
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