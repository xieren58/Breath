package com.zkp.breath.component.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zkp.breath.R

class ActivityB : AppCompatActivity() {

    val TAG = localClassName
    val requestCode = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.i(TAG, "onCreate()")
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

    override fun onSaveInstanceState(outState: Bundle?) {
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
        super.onActivityResult(requestCode, resultCode, data)
    }

}
