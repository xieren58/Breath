package com.zkp.breath.component.activity.performance

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityBlockcanaryBinding

/**
 * 界面卡顿检查工具ActivityBlockcanaryBinding的例子
 */
class BlockCanaryActivity : AppCompatActivity() {

    val TAG = BlockCanaryActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBlockcanaryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        try {
            Thread.sleep(6000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
