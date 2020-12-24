package com.zkp.breath.component.activity.jetpack

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.hi.dhl.binding.viewbind
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityResultsApiBinding
import com.zkp.breath.databinding.ActivityResultsApiSecondBinding

/**
 *
 * https://juejin.cn/post/6887743061309587463
 *
 * onActivityResult的缺点：
 * 1. 各种处理结果都耦合在该回调里，并且还得定义一堆额外的常量REQUEST_CODE,用与判断是哪个请求的回调结果。
 * 2.
 *
 * registerForActivityResult:
 * 1.其实就是把一个startActivityForResult和onActivityResult放在了一个协议中，可读性增强，不再需要定义
 *   requestCode。
 * 2.ActivityResultContracts定义了常用的result协议。
 *
 */
class ResultsApiActivity : ClickBaseActivity() {

    private val binding by viewbind<ActivityResultsApiBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvAction.setOnClickListener(this)
    }

    override fun onDebouncingClick(v: View) {
        if (v == binding.tvAction) {
            action()
        }
    }

    private fun action() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("Custom_ActivityResultContract")
                .addItem("StartActivityForResult")
                .addItem("requestPermissionContract_Contract")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "Custom_ActivityResultContract" -> {
                            customContract()
                        }
                        "startActivityForResult_Contract" -> {
                            startActivityForResultContract()
                        }
                        "requestPermissionContract_Contract" -> {
                            requestPermissionContract()
                        }
                    }
                }
                .build()
                .show()
    }

    private fun jump() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale { activity, shouldRequest ->
                    Log.i("ResultsApiActivity_tag", "rationale")
                    shouldRequest.again(true)
                }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(granted: MutableList<String>) {
                        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                            ToastUtils.showShort("哈哈哈")

                        }.launch(null)
                    }

                    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {

                    }
                }).request()
    }

//    class CusRequestPermission : ActivityResultContracts.RequestPermission() {
//    }

    private fun requestPermissionContract() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            ToastUtils.showShort("申请权限: $result")
        }.launch(Manifest.permission.CAMERA)
    }


    private fun startActivityForResultContract() {
        val intent = Intent(this, ResultsApiSecondActivity::class.java).apply {
            putExtra("name", "Hello,技术最TOP")
        }

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK && data != null) {
                ToastUtils.showShort("回传数据：$data")
            }
        }.launch(intent)
    }

    private fun customContract() {
        registerForActivityResult(CustomActivityResultContract()) { result ->
            ToastUtils.showShort("回传数据：$result")
        }.launch("Hello,技术最TOP")
    }

    /**
     * 自定义result协议
     */
    class CustomActivityResultContract : ActivityResultContract<String, String>() {
        /**
         * 创建可用于Activity#startActivityForResult的意图。
         */
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, ResultsApiSecondActivity::class.java).apply {
                putExtra("name", input)
            }
        }

        /**
         * 解析Activity#onActivityResult回传的结果
         */
        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            val data = intent?.getStringExtra("result")
            return if (resultCode == Activity.RESULT_OK && data != null) data else null
        }
    }

    class ResultsApiSecondActivity : BaseActivity() {
        private val binding by viewbind<ActivityResultsApiSecondBinding>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val name = intent.getStringExtra("name")
            Log.i("接收Tag", "接收到的数据为：$name")

            val intent = Intent().apply {
                putExtra("result", "Hello，依然范特西稀，我是回传的数据！")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

//    private class TakeDrawable(val context: Context) : ActivityResultContract<Void, Drawable>() {
//        override fun createIntent(context: Context, input: Void?): Intent {
//            return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Drawable? {
//            if (null == intent || resultCode != Activity.RESULT_OK)
//                return null
//            if (intent == null || resultCode != Activity.RESULT_OK) return null;
//            val bitmap = intent.getParcelableExtra<Bitmap>("data")
//            return BitmapDrawable(context.resources, bitmap);
//        }
//    }

}