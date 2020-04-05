package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.zkp.breath.component.activity.base.BaseActivity

/**
 * 注意web中的uri要按照android这边声明的uri格式，否则不起作用。
 * 清单文件注册声明能接收浏览器的uri。
 * 由此activity作为中转解析后再使用ARouter跳转
 */
class SchemeFilterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: String? = intent.data?.path
        // 直接通过ARouter处理外部Uri
        ARouter.getInstance().build(uri).navigation(this, object : NavCallback() {
            override fun onArrival(postcard: Postcard) {
                finish()
            }

            override fun onLost(postcard: Postcard?) {
                finish()
            }
        })
    }

}