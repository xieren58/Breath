package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class StartUpLibraryC : Initializer<StartUpLibraryC.Dependency> {

    companion object {
        private const val TAG = "StartUpLibraryC"
    }

    class Dependency

    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibraryC ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // 依赖StartUpLibraryB
        return mutableListOf(StartUpLibraryB::class.java)
    }
}