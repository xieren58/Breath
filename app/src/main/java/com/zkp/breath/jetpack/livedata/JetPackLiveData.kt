package com.zkp.breath.jetpack.livedata

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

class JetPackLiveData : ViewModel(), DefaultLifecycleObserver {

    var data: MutableLiveData<String>? = null
    val mTasks: ListCompositeDisposable = ListCompositeDisposable()
    val cusLiveData = CusLiveData()

    fun initData(): MutableLiveData<String>? {
        if (data == null) {
            data = MutableLiveData()

            Observable.create<String> {
                ToastUtils.showShort("请求初始化数据")
                Thread.sleep(5000)
                it.onNext("初始化数据")
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


    fun updateData(): MutableLiveData<String>? {
        Observable.create<String> {
            ToastUtils.showShort("请求更新数据")
            Thread.sleep(3000)
            it.onNext("更新数据")
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
        return data
    }


    override fun onDestroy(owner: LifecycleOwner) {
        mTasks.clear()
    }

}