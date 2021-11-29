package com.zkp.breath.component.activity.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityH5WakeAppBinding

/**
 * 外部浏览器的html文件唤起app的demo
 *
 * https://www.jianshu.com/p/6b7832ccb05a
 */
class H5WakeAppActivity : ClickBaseActivity() {

    private lateinit var binding: ActivityH5WakeAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityH5WakeAppBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        varargSetClickListener(binding.tv)
    }

    override fun onDebouncingClick(v: View) {
        when (v) {
            binding.tv -> {
                val intent = Intent()
                intent.action = "android .intent.action.VIEW"
                /**
                 * 前部分content://com.android.htmlfileprovider是Provider的标准，后面是html文件的路径
                 * 目前只发现在sdcard目录下的文件可以访问到，assets或者内部路径都无法访问到。可以通过复制assets文件然后存放到sdcard下download文件夹中
                 */
                intent.data = Uri.parse("content://com.android.htmlfileprovider/sdcard/外部浏览器h5唤起app.html")
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
                startActivity(intent)

                finish()
            }
            else -> {

            }
        }
    }

}
