package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class StartUpLibraryB : Initializer<StartUpLibraryB.Dependency> {

    companion object {
        private const val TAG = "StartUpLibraryB"
    }

    class Dependency

    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibraryB ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // 依赖StartUpLibraryA
        return mutableListOf(StartUpLibraryA::class.java)
    }
}