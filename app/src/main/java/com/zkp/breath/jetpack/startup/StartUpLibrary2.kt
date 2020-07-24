package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class StartUpLibrary2 : Initializer<StartUpLibrary2.Dependency> {

    companion object {
        private const val TAG = "StartUpLibrary2"
    }

    class Dependency

    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibrary2 ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(StartUpLibrary1::class.java)
    }
}