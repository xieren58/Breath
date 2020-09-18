package com.zkp.breath.component.activity.jetpack

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.adpter.RoomAdapter
import com.zkp.breath.adpter.decoration.RoomItemDecoration
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
 * https://juejin.im/post/6844903763296124942
 */
class RoomActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        roomViewModel.data.observe(this@RoomActivity, Observer {
            refreshData(it)
        })
        initView()
        initData()
    }

    private fun initData() {
        roomViewModel.query()
    }

    private fun initView() {
        binding.tvAdd.setOnClickListener(onClickListener)
        binding.tvDelete.setOnClickListener(onClickListener)
        binding.tvUpdate.setOnClickListener(onClickListener)
        binding.tvQuery.setOnClickListener(onClickListener)

        binding.rcv.layoutManager = LinearLayoutManager(binding.rcv.context)
        roomAdapter = RoomAdapter(null)
        binding.rcv.adapter = roomAdapter
        binding.rcv.addItemDecoration(RoomItemDecoration())
    }

    private fun refreshData(list: MutableList<User>?) {
        roomAdapter.setNewInstance(list)
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {

            if (v == binding.tvAdd) {
                roomViewModel.add()
            }

            if (v == binding.tvDelete) {
                roomViewModel.delete()
            }

            if (v == binding.tvUpdate) {
                roomViewModel.update()
            }

            if (v == binding.tvQuery) {
                roomViewModel.query()
                return
            }
        }
    }


    class RoomViewModel(app: Application) : AndroidViewModel(app) {

        private val mTasks: ListCompositeDisposable = ListCompositeDisposable()
        val userDao = UserRoomDatabase.get(app).userDao()
        var data: MutableLiveData<MutableList<User>?> = MutableLiveData()

        fun add() {

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
                            ToastUtils.showShort("添加成功")
                            query()
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: MutableList<User>?) {

                        }

                        override fun onError(e: Throwable?) {
                            ToastUtils.showShort(e.toString())
                        }
                    })
        }

        fun query() {
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
                            if (CollectionUtils.isEmpty(t)) {
                                add()
                            } else {
                                data.value = t
                            }
                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
        }

        fun update() {
            Observable.create<User> { emitter ->
                val value = data.value
                if (CollectionUtils.isEmpty(value)) {
                    emitter.onComplete()
                }
                val get = value?.get(0)
                get?.lastName = "我是修改过的名字"
                get?.let {
                    userDao.update(get)
                    emitter.onNext(get)
                }
                emitter.onComplete()
            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<User> {
                        override fun onComplete() {

                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: User) {
                            query()
                            ToastUtils.showShort("修改成功")
                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
        }

        fun delete() {
            Observable.create<User> { emitter ->
                val value = data.value
                if (CollectionUtils.isEmpty(value)) {
                    emitter.onComplete()
                }
                val toTypedArray = value?.toTypedArray()
                toTypedArray?.let {
                    userDao.delete(*toTypedArray)
                    emitter.onNext(User())
                }
                emitter.onComplete()
            }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.rxjava3.core.Observer<User> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            mTasks.add(d)
                        }

                        override fun onNext(t: User?) {
                            data.value = null
                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
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