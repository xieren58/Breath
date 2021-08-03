package com.zkp.breath.jetpack.livedata

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class Repository {

    val liveData = MutableLiveData<Int>()

    fun generateData() {
        liveData.value = 1
    }

    fun getMapData(): LiveData<String> {
        // 一对一的静态转换（map）
        // 第一个参数为 LiveData 源，第二个参数是一个转换函数。
        return Transformations.map(liveData) {
            "我是Transformations.map_".plus(it)
        }
    }

    val liveData1 = MutableLiveData<Int>(2)
    val liveData2 = MutableLiveData<Int>(3)
    val liveData3 = MutableLiveData<String>()

    fun getSwitchMapData(boolean: Boolean): LiveData<String> {
        return Transformations.switchMap(if (boolean) liveData1 else liveData2,
            object : Function<Int, LiveData<String>> {
                override fun apply(input: Int?): LiveData<String> {
                    liveData3.value = "我是Transformations.switchMap_".plus(input)
                    return liveData3
                }
            })
    }


}