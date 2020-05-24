package com.zkp.breath.jetpack.viewmodel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class JetPackViewModel : ViewModel() {

    var data: MutableLiveData<String>? = null
    val mTasks: ListCompositeDisposable = ListCompositeDisposable()

    fun initData(): MutableLiveData<String>? {
        if (data == null) {
            data = MutableLiveData()

            Observable.create<String> {
                ToastUtils.showShort("请求数据")
                Thread.sleep(3000)
                it.onNext("我是新数据")
                it.onComplete()
            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<String> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: String?) {
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
        mTasks.clear()
        Log.i("JetPackViewModel", "onCleared()")
    }

}

