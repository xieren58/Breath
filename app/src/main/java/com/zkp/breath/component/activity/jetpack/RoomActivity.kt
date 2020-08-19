package com.zkp.breath.component.activity.jetpack

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRoomBinding
import com.zkp.breath.jetpack.room.User
import com.zkp.breath.jetpack.room.UserRoomDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Room 包含 3 个主要组件：
 * 1. RoomDatabase（数据库），包含了DAO，并且提供创建和连接数据库的方法。
 * 2. DAO（Data Access Object） 包含用于访问数据库的方法
 * 3. Entity 表示数据库中的表。
 *
 * 注意：
 * 1、编译时会检查SQL语句是否正确
 * 2、不要在主线程中进行数据库操作
 * 3、RoomDatabase最好使用单例模式
 *
 */
class RoomActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

    }

    class RoomViewModel(app: Application) : AndroidViewModel(app) {

        private val mTasks: ListCompositeDisposable = ListCompositeDisposable()
        val userDao = UserRoomDatabase.get(app).userDao()
        var data: MutableLiveData<List<User>>? = null

        fun add(): MutableLiveData<List<User>>? {

            Observable.create<MutableList<User>> {

                val mutableListOf = mutableListOf<User>()
                for (i in 1..20) {
                    val address = User.Address()
                    address.city = "city$i"
                    address.street = "street$i"
                    address.state = "state$i"
                    address.postCode = i

                    val user = User()
                    user.uid = i
                    user.firstName = "FN$i"
                    user.lastName = "LN$i"
                    user.ignore = i
                    user.address = address

                    mutableListOf.add(user)
                }
                userDao.insert(mutableListOf)

                it.onNext(mutableListOf)
                it.onComplete()

            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<MutableList<User>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: MutableList<User>?) {
                            data?.value = t
                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
            return data
        }

        fun query(): MutableLiveData<List<User>>? {

            Observable.create<MutableList<User>> {
                val queryUsers = userDao.queryUsers()
                it.onNext(queryUsers)
                it.onComplete()

            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<MutableList<User>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: MutableList<User>?) {
                            data?.value = t
                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
            return data
        }

        fun update(): MutableLiveData<List<User>>? {

            Observable.create<MutableList<User>> {
                val queryUsers = userDao.queryUsers()
                it.onNext(queryUsers)
                it.onComplete()

            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<MutableList<User>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: MutableList<User>?) {
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


}