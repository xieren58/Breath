package com.zkp.breath.jetpack.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class JetPackLiveDataViewModel : ViewModel() {
    private val mTasks: ListCompositeDisposable = ListCompositeDisposable()

    var data: MutableLiveData<String>? = null
    val cusLiveData = CusLiveData()
    val repository by lazy { Repository() }
    val repositoryLiveData by lazy { repository.getRepositoryTransformationData() }

    /**
     * MediatorLiveData 允许您将一个或多个数据源添加到单个可观察的 LiveData 中，当任何一个数据源变化时，
     * result 会更新。（即是观察者，也是被观察者）
     */
    var mediatorLiveData: MediatorLiveData<String> = MediatorLiveData()
    private var liveData1 = MutableLiveData<String>()
    private var liveData2 = MutableLiveData<String>()
    private var liveData3 = MutableLiveData<String>()


    init {
        // 即是观察者，也是被观察者。
        // 合并多个 LiveData 源，只要任何 LiveData 源对象发生更改，就会触发 MediatorLiveData 注入的回调。
        // mediatorLiveData监听其它的liveData源，然后可以通过调用自己的setValue（）告诉观察者。
        mediatorLiveData.addSource(liveData1) {
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(liveData2) {
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(liveData3) {
            mediatorLiveData.value = it
        }
    }


    fun initData(): MutableLiveData<String>? {
        if (data == null) {
            data = MutableLiveData()

            Observable.create<String> {
                ToastUtils.showShort("请求初始化数据")
                try {
                    Thread.sleep(5000)
                } catch (e: Exception) {
                }
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

                            liveData1.value = "1"
//                            liveData2.value = "2"
//                            liveData3.value = "3"
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
            try {
                Thread.sleep(3000)
            } catch (e: Exception) {
            }
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

    override fun onCleared() {
        super.onCleared()
        try {
            mTasks.clear()
        } catch (e: Exception) {
        }
    }

}