package com.zkp.breath.component.activity.sdkVersion

import android.os.Bundle
import android.view.View
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityNotificationBinding


/**
 * 通知栏
 */
class NotificationActivity : ClickBaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        varargSetClickListener(binding.tvNotification)
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvNotification == v) {
            return
        }
    }

}