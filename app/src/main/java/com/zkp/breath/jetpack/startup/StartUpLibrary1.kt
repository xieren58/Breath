package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * 手动初始化（也是延迟初始化）
 */
class StartUpLibrary1 : Initializer<StartUpLibrary1.Dependency> {

    companion object {
        private const val TAG = "StartUpLibrary1"
    }

    class Dependency

    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibrary1 ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}