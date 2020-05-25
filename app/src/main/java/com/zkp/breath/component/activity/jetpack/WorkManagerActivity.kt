package com.zkp.breath.component.activity.jetpack

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.work.*
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

        val uploadWorkRequest = OneTimeWorkRequestBuilder<CustomWorker>().build()
//        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }

    class CustomWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
        override fun doWork(): Result {
            for (i in 0..1000) {
                Thread.sleep(100)
                Log.i("CustomWorker", i.toString())
            }
            return Result.success()
        }
    }


}