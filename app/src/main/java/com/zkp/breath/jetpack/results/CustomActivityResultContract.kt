package com.zkp.breath.jetpack.results

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.zkp.breath.component.activity.jetpack.ResultsApiActivity

/**
 * 自定义result协议的例子
 *
 * 泛型1：input type，ActivityResultLauncher#launch -> ActivityResultContract#createIntent参数2
 * 泛型2：output type，ActivityResultContract#parseResult返回类型 -> ActivityResultCallback#onActivityResult
 */
class CustomActivityResultContract : ActivityResultContract<String, String>() {
    /**
     * 创建可用于Activity#startActivityForResult的意图。
     */
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, ResultsApiActivity.ResultsApiSecondActivity::class.java).apply {
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