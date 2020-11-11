package com.zkp.breath.component.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.*
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.service.ServiceA
import com.zkp.breath.databinding.ActivityABinding


class ServiceDemoActivity : BaseActivity() {

    val TAG = ServiceDemoActivity::class.simpleName
    lateinit var intentServiceA: Intent
    var isBindServiceConnectionImp = false
    var isBindRemoteServiceConnectionImp = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityABinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnActivityA1.setOnClickListener(onClickListener)
        binding.btnActivityA2.setOnClickListener(onClickListener)
        binding.btnServiceA1.setOnClickListener(onClickListener)
        binding.btnServiceA2.setOnClickListener(onClickListener)
        binding.btnServiceRemote.setOnClickListener(onClickListener)
    }

    val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            when (v?.id) {
                R.id.btn_activity_a1 -> {
                    startActivityForResult(Intent(this@ServiceDemoActivity, ActivityB::class.java), RequestCode.ActivityACode)
                }
                R.id.btn_activity_a2 -> {
                    startActivity(Intent(this@ServiceDemoActivity, ActivityB::class.java))
                }
                R.id.btn_service_a1 -> {
                    intentServiceA = Intent(this@ServiceDemoActivity, ServiceA::class.java)
                    startService(intentServiceA)
                }
                R.id.btn_service_a2 -> {
                    bindService(Intent(this@ServiceDemoActivity, ServiceA::class.java),
                            serviceConnectionImp, Context.BIND_AUTO_CREATE)
                    isBindServiceConnectionImp = true
                }
                R.id.btn_service_remote -> {
                    bindService(Intent(this@ServiceDemoActivity, LibraryManagerService::class.java), remoteServiceConnectionImp, Context.BIND_AUTO_CREATE)
                    isBindRemoteServiceConnectionImp = true
                }
                else -> ToastUtils.showShort("else")
            }
        }
    }

    private val serviceConnectionImp = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service is ServiceA.BinderImp) {
                Log.i(TAG, "name: ${name.toString()}")
                service.send(ServiceA.Code1, null)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    private val mHandler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                1 -> Log.i(TAG, "Book: ${msg.obj}");
            }
            return true
        }
    })

    private val listener: IOnNewBookArrivedListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(book: Book?) {
            // 由于 onNewBookArrived 方法在子线程被调用，所以通过Handler切换到UI线程，方便UI操作
            mHandler.obtainMessage(1, book).sendToTarget()
        }
    }

    var mILibraryManager: ILibraryManager? = null

    private val remoteServiceConnectionImp = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val libraryManager = ILibraryManager.Stub.asInterface(service)
            mILibraryManager = libraryManager
            libraryManager.register(listener)
            try { // 近期新书查询
                val books = libraryManager.newBookList
                Log.i(TAG, "books:$books")
                // 捐赠一本书
                libraryManager.donateBook("审判")
                val books2 = libraryManager.newBookList
                Log.i(TAG, "books:$books2")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented")
        }
    }

    override fun onDestroy() {
        if (::intentServiceA.isInitialized) stopService(intentServiceA)
        if (isBindServiceConnectionImp) unbindService(serviceConnectionImp)
        if (isBindRemoteServiceConnectionImp) {
            unbindService(remoteServiceConnectionImp)
            if (mILibraryManager != null && mILibraryManager!!.asBinder().isBinderAlive()) {
                try { // 取消注册
                    mILibraryManager!!.unregister(listener)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.ActivityACode && resultCode == ResponseCode.ActivityBCode) {
            super.onActivityResult(requestCode, resultCode, data)
            Log.i(TAG, data.toString())
        }
    }

}
