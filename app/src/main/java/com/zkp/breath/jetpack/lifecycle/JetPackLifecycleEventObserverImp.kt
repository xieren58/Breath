package com.zkp.breath.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class JetPackLifecycleEventObserverImp : LifecycleEventObserver {

    val TAG = JetPackLifecycleEventObserverImp::class.simpleName

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                // 获取当前状态
                val lifecycle = source.lifecycle
                val currentState = lifecycle.currentState
                Log.i(TAG, "onCreate():${currentState}")
            }
            Lifecycle.Event.ON_START -> {
                Log.i(TAG, "onStart()")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.i(TAG, "onResume()")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.i(TAG, "onPause()")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.i(TAG, "onStop()")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.i(TAG, "onDestroy()")
            }
            Lifecycle.Event.ON_ANY -> {
                Log.i(TAG, "onAny")
            }
        }
    }

}