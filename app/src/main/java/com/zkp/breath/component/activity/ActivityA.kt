package com.zkp.breath.component.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ServiceUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.service.ServiceA


class ActivityA : AppCompatActivity(), View.OnClickListener {

    val TAG = ActivityA::class.simpleName
    lateinit var intentServiceA: Intent
    var isBindServiceConnectionImp = false
    var isBindRemoteServiceConnectionImp = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.zkp.breath.R.layout.activity_a)
        Log.i(TAG, "onCreate()")

        val btn1 = findViewById<Button>(com.zkp.breath.R.id.btn_activity_a1)
        val btn2 = findViewById<Button>(com.zkp.breath.R.id.btn_activity_a2)
        val btn3 = findViewById<Button>(com.zkp.breath.R.id.btn_service_a1)
        val btn4 = findViewById<Button>(com.zkp.breath.R.id.btn_service_a2)
        val btn5 = findViewById<Button>(com.zkp.breath.R.id.btn_service_remote)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.zkp.breath.R.id.btn_activity_a1 -> {
                startActivityForResult(Intent(this, ActivityB::class.java), RequestCode.ActivityACode)
            }
            com.zkp.breath.R.id.btn_activity_a2 -> {
                startActivity(Intent(this, ActivityB::class.java))
            }
            com.zkp.breath.R.id.btn_service_a1 -> {
                intentServiceA = Intent(this, ServiceA::class.java)
                startService(intentServiceA)
            }
            com.zkp.breath.R.id.btn_service_a2 -> {
                bindService(Intent(this, ServiceA::class.java),
                        serviceConnectionImp, Context.BIND_AUTO_CREATE)
                isBindServiceConnectionImp = true
            }
            com.zkp.breath.R.id.btn_service_remote -> {
                bindService(Intent(this, ServiceA::class.java), remoteServiceConnectionImp, Context.BIND_AUTO_CREATE)
                isBindRemoteServiceConnectionImp = true
            }
            else -> ToastUtils.showShort("else")
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


    private val remoteServiceConnectionImp = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

//            try {
//                val libraryManager = BookController.Stub.asInterface(service)
//                val bookList = libraryManager.bookList
//                for (b in bookList) {
//                    Log.i(TAG, "$b")
//                }
//
//                val temp = Book("新增")
//                libraryManager.addBookInOut(temp)
//            } catch (e: RemoteException) {
//                e.printStackTrace()
//            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    /**
     * 方法在onStart之后，onSaveInstanceState()保存的数据会传到onRestoreInstanceState与onCreate方法
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.i(TAG, "onRestoreInstanceState()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    /**
     * 在onStop()后执行
     * 触发条件：
     * 1.点击home键回到主页或切换到其他程序
     *2.按下电源键关闭屏幕
     *3.启动新的Activity
     *4.横竖屏切换时，肯定会执行，因为横竖屏切换的时候会先销毁Act，然后再重新创建 重要原则：当系统"未经你许可
     *"时销毁了你的activity，则onSaveInstanceState会被系统调用， 这是系统的责任，
     *因为它必须要提供一个机会让你保存你的数据（你可以保存也可以不保存）。
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState1()")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(TAG, "onSaveInstanceState2()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        if (::intentServiceA.isInitialized) stopService(intentServiceA)
        if (isBindServiceConnectionImp) unbindService(serviceConnectionImp)
        if (isBindRemoteServiceConnectionImp) unbindService(remoteServiceConnectionImp)
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    /**
     *  1. 在A界面调用startActivityForResult(intent,requestCode);
     *  2. 在B界面调用setResult(resultCode,intent); finish();
     *  3. 在A界面重写onActivityResult(int requestCode, int resultCode, Intent data)方法，
     *      同时判断requestCode与resultCode，只有同时满足两个条件，才表明接收到数据，从而执行处理数据的代码，
     *      这种才是安全的。如果符合则通过data对象获取传递的参数。
     *  注意：只有使用startActivityForResult返回才会触发onActivityResult（如果startActivity是不会触发的，
     *      猜想是内部有个标记判断，如果该标记为true则调用onActivityResult()方法），而setResult只是为了区分是哪个界面返回并且携带数据。
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.ActivityACode && resultCode == ResponseCode.ActivityBCode) {
            super.onActivityResult(requestCode, resultCode, data)
            Log.i(TAG, data.toString())
        }
    }

}
