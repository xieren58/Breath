package com.zkp.breath.component.activity.performance

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityLeakcanaryBinding
import com.zkp.breath.leakcanary2.LeakCanarySingleInstance

/**
 * LeakCanary2.0版本的例子
 */
class LeakCanaryActivity : AppCompatActivity() {

    val TAG = LeakCanaryActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLeakcanaryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        LeakCanarySingleInstance.Holder.newInstance(this)
    }

}
