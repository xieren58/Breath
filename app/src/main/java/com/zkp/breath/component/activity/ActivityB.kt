package com.zkp.breath.component.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R

class ActivityB : AppCompatActivity(), View.OnClickListener {

    val TAG = localClassName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.i(TAG, "onCreate()")

        val btn = findViewById<Button>(R.id.btn_activity_b)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_activity_b -> {
                setResult(ResponseCode.ActivityBCode)
                finish()
            }
            else -> ToastUtils.showShort("其他")
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        // 方法在onStart之后，onSaveInstanceState()保存的数据会传到onRestoreInstanceState与onCreate方法
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        // 方法在onStart之后，onSaveInstanceState()保存的数据会传到onRestoreInstanceState与onCreate方法
        // 两个参数的方法是5.0给我们提供的新的方法，使用前提在清单文件配置android:persistableMode="persistAcrossReboots"，
        // 然后我们的Activity就拥有了持久化的能力了， Activity拥有了持久化的能力，增加的这个PersistableBundle参数令这些方法
        // 拥有了系统关机后重启的数据恢复能力！而且不影响我们其他的序列化操作，可能内部的操作是另外弄了个文件保存吧~！API版本需要>=21，就是要5.0以上的版本才有效。
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

    override fun onSaveInstanceState(outState: Bundle) {
        // 在onStop（）方法前调用，但和onPause没有时序关系
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState()")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        // 在onStop（）方法前调用，但和onPause没有时序关系
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(TAG, "onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

}
