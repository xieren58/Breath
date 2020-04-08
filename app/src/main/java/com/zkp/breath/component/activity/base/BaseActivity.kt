package com.zkp.breath.component.activity.base

import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
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
        // activity使用arouter需要inject
        ARouter.getInstance().inject(this)
        Log.i(TAG, "onCreate(savedInstanceState: Bundle?)")
//        hideTitleBarAndStateBar()
    }

    // 依赖硬件加速，可能特殊控件不行（高德地图）
    fun gray() {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }

    // 视频，webview会出问题
//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        if ("FrameLayout" == name) {
//            val count: Int = attrs.getAttributeCount()
//            for (i in 0 until count) {
//                val attributeName: String = attrs.getAttributeName(i)
//                val attributeValue: String = attrs.getAttributeValue(i)
//                if (attributeName == "id") {
//                    val id = attributeValue.substring(1).toInt()
//                    val idVal = resources.getResourceName(id)
//                    if ("android:id/content" == idVal) {
//                        return GrayFrameLayout(context, attrs)
//                    }
//                }
//            }
//        }
//        return super.onCreateView(name, context, attrs)
//    }

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