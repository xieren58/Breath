package com.zkp.breath.component.activity.base

import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.umeng.message.PushAgent
import me.jessyan.autosize.AutoSizeConfig

/**
 * ViewBinding的优势：
 * 1.Null 安全：由于视图绑定会创建对视图的直接引用，因此不存在因视图 ID 无效而引发 Null 指针异常的风险。
 * 此外，如果视图仅出现在布局的某些配置中，则绑定类中包含其引用的字段会使用 @Nullable 标记。
 * 2.类型安全：每个绑定类中的字段均具有与它们在 XML 文件中引用的视图相匹配的类型。这意味着不存在发生类转换异常的风险。
 */

// Api27及其以上AppCompatActivity支持主构函数传入LayoutId,默认为0表示此布局id无效，但还是推荐viewbinding进行（因为viewbinding可以获取子view对象，不需要再进行findViewById()）
abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0) : AppCompatActivity(contentLayoutId) {

    val ACTIVITY_TAG = this::class.simpleName

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        Log.i(ACTIVITY_TAG, "attachBaseContext(newBase: Context?)")
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
        Log.i(ACTIVITY_TAG, "onCreate(savedInstanceState: Bundle?)")
//        hideTitleBarAndStateBar()

        // 该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
        PushAgent.getInstance(this).onAppStart()
    }

    // 依赖硬件加速，可能特殊控件不行（高德地图），放在oncreate方法即可
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
        Log.i(ACTIVITY_TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_TAG, "onPause()")
    }

    /**
     * DecordView和PhoneWindow绑定完成
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(ACTIVITY_TAG, "onAttachedToWindow()")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(ACTIVITY_TAG, "onDetachedFromWindow()")
    }

    //应用的启动图标在桌面上的位置有变化，可在此收到新的位置信息，
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(ACTIVITY_TAG, "onNewIntent(intent: Intent?)")
    }

    // 当setContentView设置显示后会回调Activity的onContentChanged方法
    override fun onContentChanged() {
        super.onContentChanged()
        Log.i(ACTIVITY_TAG, "onContentChanged()")
    }

}