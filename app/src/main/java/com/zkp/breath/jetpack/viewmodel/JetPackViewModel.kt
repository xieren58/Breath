package com.zkp.breath.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

/**
 * ViewModel解决的问题：
 * 1.数据持久化:当我们的Activity/Fragment因为某些因素被销毁重建时(屏幕旋转)，这里就涉及到数据保存的问题，显然重新请求
 * 或加载数据是不友好的。在 ViewModel 出现之前我们可以用 activity 的onSaveInstanceState()机制保存和恢复数据，
 * 但缺点很明显，onSaveInstanceState只适合保存少量的可以被序列化、反序列化的数据，假如我们需要保存是一个比较大的
 * bitmap list ，这种机制明显不合适，由于 ViewModel 的特殊设计，可以解决此痛点。
 *
 * 2.异步回调问题：通常我们 app 需要频繁异步请求数据，比如调接口请求服务器数据。当然这些请求的回调都是相当耗时的，
 * 之前我们在 Activity 或 fragment里接收这些回调。所以不得不考虑潜在的内存泄漏情况，比如 Activity 被销毁后接口请
 * 求才返回。处理这些问题，会给我们增添好多复杂的工作。但现在我们利用 ViewModel 处理数据回调，可以完美的解决此痛点
 */
class JetPackViewModel : ViewModel() {

    var data: MutableLiveData<String>? = null
    var viewModelField = "viewModelField"

    fun vm() {
        if (data == null) {
            thread {
                Thread.sleep(5000)

            }
        }
    }

}