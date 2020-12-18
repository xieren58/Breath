package com.zkp.breath.component.activity.base

import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.os.PersistableBundle
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

    val ACTIVITY_TAG: String? by lazy {
        this::class.simpleName
    }

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

    override fun onRestart() {
        super.onRestart()
        Log.i(ACTIVITY_TAG, "onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(ACTIVITY_TAG, "onStart()")
    }

    // onStart之后被调用
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(ACTIVITY_TAG, "onRestoreInstanceState()")
    }

    // onStart之后被调用
    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.i(ACTIVITY_TAG, "onRestoreInstanceState()")
    }

    /**
     * 通过ViewRootImpl将DecorView和PhoneWindow进行绑定,正真触发绘制流程。
     */
    override fun onResume() {
        super.onResume()
        Log.i(ACTIVITY_TAG, "onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i(ACTIVITY_TAG, "onPostResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_TAG, "onPause()")
    }

    /**
     * 在onStop()之前执行，但不保证一定在onPause之前或者之后（SDK不同实现），保存的数据会传到onRestoreInstanceState与onCreate方法。
     * 因为使用的是Bundle保存，所以只允许只适合保存少量的可以被序列化数据，最终数据写到本地文件存存储。
     * 推荐使用ViewModel替换，ViewModel可存大量数据，而且由于存放在内存所以读写速度快。
     *
     * 触发条件：
     * 1.点击home键回到主页或切换到其他程序
     * 2.按下电源键关闭屏幕
     * 3.启动新的Activity
     * 4.横竖屏切换时，肯定会执行，因为横竖屏切换的时候会先销毁Activity，然后再重新创建
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(ACTIVITY_TAG, "onSaveInstanceState()")
    }

    /**
     * 在onStop（）方法前调用，onSaveInstanceState()保存的数据会传到onRestoreInstanceState与onCreate方法
     * 两个参数的方法是5.0给我们提供的新的方法，使用前提在清单文件配置android:persistableMode="persistAcrossReboots"，
     * 然后我们的Activity就拥有了持久化的能力了， Activity拥有了持久化的能力，增加的这个PersistableBundle参数令这些方法
     * 拥有了系统关机后重启的数据恢复能力！而且不影响我们其他的序列化操作，可能内部的操作是另外弄了个文件保存吧~！
     * API版本需要>=21，就是要5.0以上的版本才有效。
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(ACTIVITY_TAG, "onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_TAG, "onDestroy()")
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

    // 当用户点击回退键的时候回调，由onKeyDown()触发
    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     *  1. 在A界面调用startActivityForResult(intent,requestCode);
     *  2. 在B界面调用setResult(resultCode,intent); finish();
     *  3. 在A界面重写onActivityResult(int requestCode, int resultCode, Intent data)方法，同时判断
     *  requestCode与resultCode，只有同时满足两个条件，才表明接收到数据，从而执行处理数据的代码，这种才是安全的。
     *  如果符合则通过data对象获取传递的参数。
     *
     *  注意：只有使用startActivityForResult返回才会触发onActivityResult（如果startActivity是不会触发的，
     *      猜想是内部有个标记判断，如果该标记为true则调用onActivityResult()方法），而setResult只是为了区分
     *      是哪个界面返回并且携带数据。
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}