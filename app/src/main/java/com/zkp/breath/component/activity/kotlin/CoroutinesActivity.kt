package com.zkp.breath.component.activity.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * 什么是协程：
 * 协程就是 Kotlin 提供的线程框架，但并不是说协程就是为线程而生的，协程最常用的功能是并发，而并发的典型场景就是多线程，
 * 能够在同一个代码块进行多次线程切换而不会导致多级嵌套，只形成上下级关系。用同步的方式写异步的代码
 *
 * 为什么使用协程：
 * java的线程池Executor是对线程复用和生命周期（创建，销毁）的管理，android的AsyncTask或者Handler是用于解决线
 * 程间通信（子线程切到主线程），但是一旦遇到多个api调用且需要合并结果的时候，我们一般会嵌套串行调用，明明可以并发
 * 执行由于使用了串行导致效率降低了一倍，而且也会陷入“地狱式回调”也就是嵌套太多层回调导致可读性降低。
 *
 * suspend:代码执行到 suspend 函数的时候会『挂起』，并且这个『挂起』是非阻塞式的，它不会阻塞你当前的线程。
 *
 */
class CoroutinesActivity : BaseActivity() {
    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        // kotlin提供的函数简化了对Thread的使用
        thread {
            Log.i(TAG, "thread: ${Thread.currentThread().name}");
        }

        // 如果没有ui操作就没必要使用Dispatchers.Main，否则会报错。
        // 消除了嵌套关系，内部的withContext形成了上下级关系
        GlobalScope.launch(Dispatchers.Main) {
            // 1
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(TAG, "launch_IO1: ${Thread.currentThread().name}")
            }
            // 2
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(TAG, "launch_IO2: ${Thread.currentThread().name}")
            }

            // 3
            extractWithContext()

            // 4
            binding.tvCoroutines.text = "tttt"
            Log.i(TAG, "launch_Main: ${Thread.currentThread().name}")
        }

    }

    // 可以把withContext放进单独的一个函数内部，但函数需要添加suspend关键字（因为withContext 是一个 suspend 函数，它需要在协程或者是另一个 suspend 函数中调用）
    suspend fun extractWithContext() = withContext(Dispatchers.IO) {
        Log.i(TAG, "extractWithContext_IO3: ${Thread.currentThread().name}")
    }

}