package com.zkp.breath.component.activity.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import me.jessyan.autosize.AutoSizeConfig

abstract class BaseActivity : AppCompatActivity() {

    val TAG = this::class.simpleName

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        Log.i(TAG, "attachBaseContext(newBase: Context?)")
    }

    /**
     * 在这个方法中会创建PhoneWindow，WindowManagerImpl,DecorView(把setContentView中的布局文件添加到其ContentView中)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //使用设备的完整尺寸(即隐藏标题栏和状态栏)
        AutoSizeConfig.getInstance().isUseDeviceSize = true
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate(savedInstanceState: Bundle?)")
//        hideTitleBarAndStateBar()
    }

    /**
     * 隐藏状态栏和标题栏
     */
    private fun hideTitleBarAndStateBar() {
        // 隐藏标题栏
        supportActionBar?.hide()
        // 隐藏状态栏
        BarUtils.setStatusBarVisibility(this, false)
    }

    /**
     * 通过ViewRootImpl将DecorView和PhoneWindow进行绑定,正真触发绘制流程。
     */
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    /**
     * DecordView和PhoneWindow绑定完成
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(TAG, "onAttachedToWindow()")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindow()")
    }

    //应用的启动图标在桌面上的位置有变化，可在此收到新的位置信息，
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent(intent: Intent?)")
    }

    // 当setContentView设置显示后会回调Activity的onContentChanged方法
    override fun onContentChanged() {
        super.onContentChanged()
        Log.i(TAG, "onContentChanged()")
    }

}