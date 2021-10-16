package com.zkp.breath.component.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ThreadUtils
import com.zkp.breath.MainActivity
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity

class ActivitySplash : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.startActivity(MainActivity::class.java)
        ActivityUtils.finishActivity(this@ActivitySplash)
    }

}