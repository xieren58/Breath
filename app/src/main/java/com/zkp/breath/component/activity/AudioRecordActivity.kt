package com.zkp.breath.component.activity

import android.content.*
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.*
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.service.ServiceA
import com.zkp.breath.databinding.ActivityABinding
import com.zkp.breath.databinding.ActivityAudioRecordBinding
import com.zkp.breath.databinding.ActivityMainBinding


class AudioRecordActivity : BaseActivity() {

    private lateinit var binding: ActivityAudioRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioRecordBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

    }


}
