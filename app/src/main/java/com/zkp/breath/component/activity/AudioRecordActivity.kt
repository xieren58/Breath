package com.zkp.breath.component.activity

import android.os.*
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAudioRecordBinding


class AudioRecordActivity : BaseActivity() {

    private lateinit var binding: ActivityAudioRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioRecordBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

    }


}
