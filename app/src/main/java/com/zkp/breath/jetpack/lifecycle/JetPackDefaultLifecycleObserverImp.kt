package com.zkp.breath.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


class JetPackDefaultLifecycleObserverImp : DefaultLifecycleObserver {

    val TAG = JetPackDefaultLifecycleObserverImp::class.simpleName

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