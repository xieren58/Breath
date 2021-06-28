package com.zkp.breath.component.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zkp.breath.component.activity.base.BaseActivity

/**
 * 当应该处于后台，借用一个空白的activity返回前台。
 *
 *  场景：一般处于后台，点击通知栏消息返回前台。
 */
class BackForegroundActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            try {
                val intent = Intent(context, BackForegroundActivity::class.java)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.action = Intent.ACTION_MAIN
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 不需要设置contentView，同时马上finish不影响正常页面的跳转或者使用
        finish()
    }

}