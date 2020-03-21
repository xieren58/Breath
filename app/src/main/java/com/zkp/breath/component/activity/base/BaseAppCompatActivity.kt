package com.zkp.breath.component.activity.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils

abstract class BaseAppCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 隐藏标题栏
        supportActionBar?.hide()
        // 隐藏状态栏
        BarUtils.setStatusBarVisibility(this, false)
    }

    //应用的启动图标在桌面上的位置有变化，可在此收到新的位置信息，
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

}