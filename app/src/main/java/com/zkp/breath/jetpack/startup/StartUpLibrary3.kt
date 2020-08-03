package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class StartUpLibrary3 : Initializer<StartUpLibrary3.Dependency> {

    companion object {
        private const val TAG = "StartUpLibrary3"
    }

    class Dependency

    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibrary3 ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(StartUpLibrary2::class.java)
    }
}