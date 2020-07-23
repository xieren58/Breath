package com.zkp.breath.component.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.zkp.breath.MainActivity
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity

class ActivitySplash : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    private fun requestPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE)
                .rationale { activity, shouldRequest ->
                    Log.i(TAG, "rationale")
                    shouldRequest?.again(true)
                }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(granted: MutableList<String>) {
                        Log.i(TAG, "onGranted")
                        ActivityUtils.startActivity(MainActivity::class.java)
                        ActivityUtils.finishActivity(this@ActivitySplash)
                    }

                    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                        Log.i(TAG, "onDenied")
                        if (deniedForever.isNotEmpty()) {
                            // 防止title和message无显示
                            val builder: AlertDialog.Builder = AlertDialog.Builder(
                                    this@ActivitySplash,
                                    R.style.Theme_AppCompat_Light_Dialog_Alert)
                            builder.setCancelable(false)
                            val alertDialog = builder.setTitle("提示！")
                                    .setMessage("请前往设置->应用" + "->权限中打开读写相关权限，否则功能无法正常运行！")
                                    .setPositiveButton("确定") { dialog: DialogInterface?, which: Int ->
                                        this@ActivitySplash.finish()
                                        AppUtils.launchAppDetailsSettings()
                                    }.show()
                        } else {
                            ActivityUtils.finishActivity(ActivitySplash::class.java)
                        }
                    }
                }).request()
    }
}