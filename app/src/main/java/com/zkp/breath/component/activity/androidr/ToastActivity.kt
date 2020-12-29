package com.zkp.breath.component.activity.androidr

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityToastR30Binding

/**
 * https://juejin.cn/post/6844904144424140808#heading-5
 *
 * 测试须知：模拟器或者真机必须是R以上，build的sdk版本也必须修改为R以上。
 *
 * 1. 禁止后台自定义 Toast
 * 2. text toast 不允许自定义，默认的 toast 是 text toast，在 targetSdkVersion 为 R 或更高时，调用 setGravity
 *    和 setMargin 方法将不进行任何操作。官方文档中所述的 Android R 仅影响 「text toast」 ，而自定义的 toast 不受影响。
 * 3. setView() 被标记弃用，当该方法从源码中移除后，自定 Toast 的方式将被彻底消灭，当然，官方提供了相应的替代品，
 *    使用 Snackbar。
 * 4. 新增 Toast.Callback 回调，以通知 Toast 显示和隐藏。
 */
class ToastActivity : BaseActivity(R.layout.activity_toast_r30) {

    private lateinit var binding: ActivityToastR30Binding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        getContentView()?.let { binding = ActivityToastR30Binding.bind(it) }
//        disableBackstageCusToast()
//        toastCallBack()
    }

    /**
     * 自定义 Toast 「不能」 在 app 处于后台时显示，取而代之会显示 「"Background custom toast blocked
     * for package [packageName] See g.co/dev/toast."」 的文本 toast
     */
    @SuppressLint("InflateParams")
    private fun disableBackstageCusToast() {
        handler.postDelayed({
            val toastUtils = ToastUtils()
            toastUtils.show(layoutInflater.inflate(R.layout.activity_toast_custom_r30, null))
        }, 1500L)
    }

    /**
     * 添加了新的回调（Toast.Callback），以通知 Toast 显示和隐藏。
     */
    private fun toastCallBack() {
//        val toast = Toast.makeText(this, "哈哈哈哈", Toast.LENGTH_SHORT)
//        toast.addCallback(object : Toast.Callback() {
//            override fun onToastShown() {
//                super.onToastShown()
//                Log.i("toastCallBack", "onToastShown")
//            }
//
//            override fun onToastHidden() {
//                super.onToastHidden()
//                Log.i("toastCallBack", "onToastShown")
//            }
//        })
//        toast.show()
    }

}