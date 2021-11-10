package com.zkp.breath.coroutines

import android.util.Log
import com.zkp.breath.component.activity.jetpack.CoroutinesActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.NullPointerException
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * suspendCoroutine，suspendCancellableCoroutineTask。
 *
 * 对具有回调方法的改造，改造成具有返回值。
 * 使用协程开发，可以避免地狱式回调的尴尬。但是不可避免以前的旧代码还是存在回调的形式，这时候就可以使用这两个方法进行改造。
 */
fun suspendCoroutineDemo() {

    // 传统方式请求api，需要一个回调。缺点：地狱式回调，可读性差。
    runTask()

    // 使用suspendCoroutine函数进行结果回调，虽然内部也是回调，但是在上层调用者是无感知的。更加接近协程的用意，用同步（顺序）的方式写异步的代码。
    GlobalScope.launch(Dispatchers.Main) {

        try {
            val suspendCoroutineTask = suspendCoroutineTask()
            Log.i("suspendCoroutineDemo", "suspendCoroutineTask: $suspendCoroutineTask")
        } catch (e: Exception) {
            Log.i("suspendCoroutineDemo", "error: ${e.message}")
        }

        try {
            val suspendCoroutineTask = suspendCancellableCoroutineTask()
            Log.i("suspendCoroutineDemo", "suspendCancellableCoroutineTask: $suspendCoroutineTask")
        } catch (e: Exception) {
            Log.i("suspendCoroutineDemo", "error: ${e.message}")
        }
    }

}

private fun task(callback: CoroutinesActivity.Callback<String>) {
    thread {
        try {
            // 模拟耗时场景
            Thread.sleep(1000)
            // 模拟成功返回
            callback.onSuccess("result")
        } catch (e: Exception) {
            // 模拟失败返回
            callback.onError(e)
        }
    }
}

private fun runTask() {
    task(object : CoroutinesActivity.Callback<String> {
        override fun onSuccess(value: String) {
            Log.i("runTask", "onSuccess: $value")
        }

        override fun onError(t: Throwable) {
            Log.i("runTask", "onError: ${t.message}")
        }
    })
}

private suspend fun suspendCoroutineTask() = suspendCoroutine<String> {
    task(object : CoroutinesActivity.Callback<String> {
        override fun onSuccess(value: String) {
            Log.i("runTask", "onSuccess: $value")
            it.resume(value)
        }

        override fun onError(t: Throwable) {
            Log.i("runTask", "onError: ${t.message}")
            it.resumeWithException(t)
        }
    })
}

suspend fun suspendCoroutineExceptionTask() = suspendCoroutine<String> {
    task(object : CoroutinesActivity.Callback<String> {
        override fun onSuccess(value: String) {
            throw NullPointerException("测试异常")
        }

        override fun onError(t: Throwable) {
            it.resumeWithException(t)
        }
    })
}

private suspend fun suspendCancellableCoroutineTask() = suspendCancellableCoroutine<String> {
    task(object : CoroutinesActivity.Callback<String> {
        override fun onSuccess(value: String) {
            Log.i("runTask", "onSuccess: $value")
            it.resume(value)
        }

        override fun onError(t: Throwable) {
            Log.i("runTask", "onError: ${t.message}")
            it.resumeWithException(t)
        }
    })
}

