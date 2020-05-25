package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRoomBinding

/**
 * WorkManager 旨在用于可延迟运行（即不需要立即运行）并且在应用退出或设备重启时必须能够可靠运行的任务，不适用于应用
 * 进程结束时能够安全终止的运行中后台工作，也不适用于需要立即执行的任务。
 */
class WorkManagerActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


    }


}