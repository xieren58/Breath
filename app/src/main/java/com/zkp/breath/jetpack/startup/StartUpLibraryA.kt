package com.zkp.breath.jetpack.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * 自定初始化
 */
class StartUpLibraryA : Initializer<StartUpLibraryA.Dependency> {

    companion object {
        private const val TAG = "StartUpLibraryA"
    }

    class Dependency

    // 这里进行组件初始化工作，返回值会把当作value和本类为key被存放在一个map中，在这里可以视为成员变量以供后期获取使用。
    override fun create(context: Context): Dependency {
        Log.i(TAG, "init StartUpLibraryA ")
        return Dependency()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // 不依赖任何库
        return mutableListOf()
    }
}