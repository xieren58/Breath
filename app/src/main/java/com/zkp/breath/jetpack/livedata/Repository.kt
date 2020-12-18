package com.zkp.breath.jetpack.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class Repository {

    val liveData = MutableLiveData<Int>()

    fun generateData() {
        liveData.value = 1
    }

    fun getRepositoryTransformationData(): LiveData<String> {
        return Transformations.map(liveData) {
            "我是Transformations.map_".plus(it)
        }
    }

}