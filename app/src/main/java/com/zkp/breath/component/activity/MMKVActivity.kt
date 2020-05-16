package com.zkp.breath.component.activity

import android.annotation.SuppressLint
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Surface
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.component.activity.MaterialDialogsActivity
import com.zkp.breath.component.activity.arouter.ARouterActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.weight.RecycleViewActivity
import com.zkp.breath.databinding.ActivityMainBinding
import com.zkp.breath.databinding.ActivityMmkvBinding
import com.zkp.breath.mmkv.template.AppConfiguration

class MMKVActivity : BaseActivity() {

    private lateinit var binding: ActivityMmkvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMmkvBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }


}