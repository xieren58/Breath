package com.zkp.breath.component.activity.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.blankj.utilcode.util.ThreadUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 * 什么是协程（启动一个协程可以使用 launch 或者 async 函数，协程其实就是这两个函数中闭包的代码块）：
 * 协程就是 Kotlin 提供的线程框架，但并不是说协程就是为线程而生的，协程最常用的功能是并发，而并发的典型场景就是多线程，
 * 能够在同一个代码块进行多次线程切换而不会导致多级嵌套，只形成上下级关系。用同步的方式写异步的代码
 *
 * 为什么使用协程：
 * java的线程池Executor是对线程复用和生命周期（创建，销毁）的管理，android的AsyncTask或者Handler是用于解决线
 * 程间通信（子线程切到主线程），但是一旦遇到多个api调用且需要合并结果的时候，我们一般会嵌套串行调用，明明可以并发
 * 执行由于使用了串行导致效率降低了一倍，而且也会陷入“地狱式回调”也就是嵌套太多层回调导致可读性降低。
 *
 * 挂起本质：
 * 代码执行到 suspend 函数的时候会从当前线程挂起协程，就是这个协程从正在执行它的线程上脱离（launch函数指定的线程中脱离），
 * 挂起后的协程会在suspend函数指定的线程中继续执行，在 suspend 函数执行完成之后，协程会自动帮我们把线程再切回来（切回launch函数指定的线程）。
 * suspend的意义在于提醒使用者要在协程中调用，真正实现挂起的是withContext（）这个kotlin提供的方法。
 *
 * kotlin提供的suspend函数，注意都需要在协程中调用：
 * 1.withContext（）
 * 2.delay()   等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作，该操作发生在launch函数指定的线程。
 *
 * 非阻塞式挂起:
 * 阻塞是发生在单线程中，挂起是切到另外的线程执行了，所以挂起一定是非阻塞的。
 *
 *
 * 协程作用域（理解为生命周期）：
 * 1.runBlocking：顶层函数，它和 coroutineScope 不一样，它会阻塞当前线程来等待，所以这个方法在业务中并不适用 。
 * 2.GlobalScope：全局协程作用域，可以在整个应用的声明周期中操作，且不能取消，所以仍不适用于业务开发。（会造成空指针或者内存泄漏）
 * 3.自定义作用域：自定义协程的作用域，不会造成内存泄漏。
 *
 * 调度器（将协程限制在特定的线程执行）：
 * Dispatchers.Main：指定执行的线程是主线程。
 * Dispatchers.IO：指定执行的线程是 IO 线程。
 * Dispatchers.Default：默认的调度器，适合执行 CPU 密集性的任务。
 * Dispatchers.Unconfined：非限制的调度器，指定的线程可能会随着挂起的函数发生变化。
 *
 * launch：启动一个新的协程，它返回的是一个 Job对象，我们可以调用 Job#cancel() 取消这个协程。除了 launch，
 * 还有一个方法跟它很像，就是 async，它的作用是创建一个协程，之后返回一个 Deferred<T>对象，我们可以调用
 * Deferred#await()去获取返回的值，有点类似于 Java 中的 Future（会阻塞当前线程，最外层的协程一定不能在主线程）。
 *
 * https://www.sohu.com/a/236536167_684445
 * https://www.jianshu.com/p/76d2f47b900d
 * https://mp.weixin.qq.com/s/lBS1PpWeIXLFjkGfOZilyw
 */
class CoroutinesActivity : BaseActivity() {
    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        init()
        customScopeDemo()
    }

    // 1. 创建一个 MainScope
    val scope = MainScope()
    private fun customScopeDemo() {     // 自定义作用域demo
        // 2. 启动协程
        scope.launch(Dispatchers.IO) {
            Log.i(ACTIVITY_TAG, "customScopeDemo: 1")
            // async 能够并发执行任务，执行任务的时间也因此缩短了一半。async 还可以对它的 start 入参设置成懒加载
            val one = async {
                getResult(20)
                Log.i(ACTIVITY_TAG, "customScopeDemo: 2_" + ThreadUtils.isMainThread())
            }
            Log.i(ACTIVITY_TAG, "customScopeDemo: 3")
            val two = async {
                getResult(40)
                Log.i(ACTIVITY_TAG, "customScopeDemo: 4_" + ThreadUtils.isMainThread())
            }
            Log.i(ACTIVITY_TAG, "customScopeDemo: 5")
            Log.i(ACTIVITY_TAG, "customScopeDemo: " + (one.await() + two.await()).toString())
            Log.i(ACTIVITY_TAG, "customScopeDemo: 6")
        }
    }

    private suspend fun getResult(num: Int): Int {
        delay(5000)
        return num * num
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        // kotlin提供的函数简化了对Thread的使用
        thread {
            Log.i(ACTIVITY_TAG, "thread: ${Thread.currentThread().name}")
        }

        // 如果没有ui操作就没必要使用Dispatchers.Main，否则会报错。
        // 消除了嵌套关系，内部的withContext形成了上下级关系

        // 挂起函数后并不会往下继续执行，只有等挂起函数执行完毕才能接着往下执行，但这个挂起不是暂停，而是脱离的意思，
        // 脱离到其他线程执行完毕再切换原有线程继续往下执行。
        GlobalScope.launch(Dispatchers.Main) {
            // 1
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(ACTIVITY_TAG, "launch_IO1: ${Thread.currentThread().name}")
            }
            // 2
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(ACTIVITY_TAG, "launch_IO2: ${Thread.currentThread().name}")
            }

            // 3
            extractWithContext()

            // 4
            extractDelay()

            // 5
            binding.tvCoroutines.text = "协程牛逼！"
            Log.i(ACTIVITY_TAG, "launch_Main: ${Thread.currentThread().name}")
        }

        // 0
        Log.i(ACTIVITY_TAG, "main_out: ${Thread.currentThread().name}")

    }

    // 可以把withContext放进单独的一个函数内部，但函数需要添加suspend关键字（因为withContext 是一个 suspend 函数，
    // 它需要在协程或者是另一个 suspend 函数中调用）
    private suspend fun extractWithContext() = withContext(Dispatchers.IO) {
        Log.i(ACTIVITY_TAG, "extractWithContext_IO3: ${Thread.currentThread().name}")
    }

    private suspend fun extractDelay() {
        // 等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作
        delay(1000)
        Log.i(ACTIVITY_TAG, "extractDelay: ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 3. 销毁的时候释放
        scope.cancel()
    }

}