package com.zkp.breath.jetpack.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zkp.breath.BaseApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * AndroidViewModel用于获取上下文
 */
class JetPackAndroidViewModel(application: Application) : AndroidViewModel(application) {

    var data: MutableLiveData<String>? = null
    private val mTasks: ListCompositeDisposable = ListCompositeDisposable()

    fun initData(): MutableLiveData<String>? {
        if (data == null) {
            Observable.create<String> {
                try {
                    Thread.sleep(3000)
                    it.onNext("我是初始化数据")
                    it.onComplete()
                } catch (e: Exception) {
                }
            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<String> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: String?) {
                            val application = getApplication<BaseApplication>()
                            val baseContext = application.baseContext
                            Log.i("JetPackAndroidViewModel", "演示获取上下文:$baseContext")
                            data?.value = t
                        }

                        override fun onError(e: Throwable?) {
                        }
                    })
        }
        return data
    }

    override fun onCleared() {
        super.onCleared()
        try {
            mTasks.clear()
        } catch (e: Exception) {
        }
    }
}