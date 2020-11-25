package com.zkp.breath.component.activity.kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 * 什么是协程（协程不是线程，可以认为是一个线程框架）：
 * 1.用同步（顺序）的方式写异步的代码：协程最常用的功能是并发，能够在同一个代码块进行多次线程切换而不会导致多级嵌套
 *  （死亡回调），只形成上下级关系。
 * 2.非阻塞挂起：线程由系统调度（用户不可控），线程切换（内核态和用户态的切换）或线程阻塞（竞争cpu时间片，如果在同步锁
 *   环境下已经有线程获取到cpu执行权，那么后来的线程即便获取到cpu的执行权也不能做任何操作，浪费时间又浪费cpu操作）
 *   的开销都比较大。而协程依赖于线程，协程挂起不会阻塞协程所在的线程，由用户控制（一直在用户态，不用在核态和用户态的
 *   切换，也不用像线程一样竞争cpu执行权），一个线程可以创建任意个协程。
 *
 * 挂起本质：
 * 1. 代码执行到 suspend 函数的时候会从当前线程挂起协程，就是这个协程从正在执行它的线程上脱离（launch函数指定的线程中脱离），
 * 挂起后的协程会在suspend函数指定的线程中继续执行，在 suspend 函数执行完成之后，协程会自动帮我们把线程再切回来（
 * 切回launch函数指定的线程），suspend函数要在协程中或者另一个suspend函数中调用。
 *
 * 2. suspend 函数它就像是回调的语法糖一样，实际上通过一个叫 Continuation 的接口的实例来返回结果，而这一步操作
 *  是由编译器自动帮我们完成。如下：
 *  suspend fun requestToken(): String { ... }  👇
 *  Object requestToken(Continuation<String> cont) { ... }   // 实际上在JVM中
 *
 * 3. 然而，协程内部实现不是使用普通回调的形式，而是使用状态机CPS(Continuation Passing Style)来处理不同的挂起点。
 *  每一个挂起点对应的 Continuation 都会转化为一种状态，协程恢复只是跳转到下一种状态中。挂起函数将执行过程分为多个
 *  Continuation 片段，并且利用状态机的方式保证各个片段是顺序执行的。
 *
 *
 * kotlin提供的suspend函数，注意都需要在协程中调用：
 * 1.withContext（）
 * 2.delay()等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作，该操作发生在launch函数指定的线程。
 *
 * 非阻塞式挂起:
 * 阻塞是发生在单线程中，挂起已经是一种切到另外的线程执行了，所以挂起一定是非阻塞的。一个协程在进入阻塞后不会阻塞当前线程，
 * 当前线程会去执行其他协程任务
 *
 *
 * 启动一个协程：
 * 1.runBlocking：顶层函数，它和 coroutineScope 不一样，它会阻塞当前线程来等待，所以这个方法在实际业务中并不适用。
 * 2.launch：启动一个新的协程，它返回的是一个 Job对象，我们可以调用 Job#cancel() 取消这个协程。
 * 3.async：启动一个新的协程，之后返回一个 Deferred<T>对象（Job的子类），Deferred#await()可以获取到返回值，
 *   await是一个挂起函数。
 *
 * 协程作用域（理解为生命周期）：
 * 1.GlobalScope：全局协程作用域，可以在整个应用的声明周期中操作，且不能取消，所以仍不适用于业务开发。（会造成空指针或者内存泄漏）
 * 2.自定义作用域：自定义协程的作用域，不会造成内存泄漏。
 *
 * 调度器（将协程限制在特定的线程执行）：
 * Dispatchers.Main：指定执行的线程是主线程。
 * Dispatchers.IO：指定执行的线程是 IO 线程。
 * Dispatchers.Default：默认的调度器，适合执行 CPU 密集性的任务。
 * Dispatchers.Unconfined：非限制的调度器，指定的线程可能会随着挂起的函数发生变化。
 *
 * CoroutineStart(启动模式)，只需要掌握下面两个即可:
 * 1. DEFAULT	立即执行协程体
 * 2. LAZY	只有在需要的情况下运行
 *
 * https://www.sohu.com/a/236536167_684445
 * https://www.jianshu.com/p/76d2f47b900d
 * https://mp.weixin.qq.com/s/lBS1PpWeIXLFjkGfOZilyw
 * https://www.jianshu.com/p/2979732fb6fb
 */
class CoroutinesActivity : BaseActivity() {

    private lateinit var binding: ActivityCoroutinesBinding
    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
//        init()
//        asyncDemo()
//        runBlockingDemo()

        coroutineStartStrategyDemo()
    }

    private fun asyncDemo() {
        mainScope.launch {
            Log.i(ACTIVITY_TAG, "asyncDemo: 1: " + Thread.currentThread().name)
            // async 能够并发执行任务，执行任务的时间也因此缩短了一半。async 还可以对它的 start 入参设置成懒加载
            val one = async {
                getResult(20)
                Log.i(ACTIVITY_TAG, "asyncDemo: 2_" + Thread.currentThread().name)
            }
            Log.i(ACTIVITY_TAG, "asyncDemo: 3")
            val two = async {
                getResult(40)
                Log.i(ACTIVITY_TAG, "asyncDemo: 4_" + Thread.currentThread().name)
            }
            Log.i(ACTIVITY_TAG, "asyncDemo: 5")
            // await会挂起执行该方法所处的协程（这里指MainScope），等待await所属的协程完成(这里指one和two，即Deferred)。
            Log.i(ACTIVITY_TAG, "asyncDemo: " + (one.await() + two.await()).toString())
            Log.i(ACTIVITY_TAG, "asyncDemo: 6")
        }
        Log.i(ACTIVITY_TAG, "asyncDemo: 7")
    }

    private suspend fun getResult(num: Int): Int {
        delay(5000)
        return num * num
    }

    /**
     * runBlocking会阻塞当前线程，所以一定会等待协程内部（内部可以有多个协程）执行完毕才会执行外部的代码
     */
    private fun runBlockingDemo() {
        runBlocking {
            launch { // 这里默认是Dispatchers.Default，在mian线程运行，当不是直接运行，相当于post任务到队列等待执行
                Log.i("test", "1:${Thread.currentThread().name}")
                delay(100)
                Log.i("test", "2: ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) {// Dispatchers.Unconfined 是直接运行的
                Log.i("test", "3: ${Thread.currentThread().name}")
                delay(100)
                Log.i("test", "4: ${Thread.currentThread().name}")
            }
        }

        Log.i("runBlockingDemo", "threadName_0: ${Thread.currentThread().name}")
        runBlocking(Dispatchers.IO) {

            launch(Dispatchers.Unconfined) {
                Log.i("runBlockingDemo", "threadName_1: ${Thread.currentThread().name}")
            }

            launch(Dispatchers.Unconfined) {
                Log.i("runBlockingDemo", "threadName_1_1: ${Thread.currentThread().name}")
            }

            Log.i("runBlockingDemo", "threadName_2: ${Thread.currentThread().name}")
        }

        Log.i("runBlockingDemo", "threadName_3: ${Thread.currentThread().name}")
    }

    private fun init() {
        // kotlin提供的函数简化了对Thread的使用
        thread {
            Log.i(ACTIVITY_TAG, "thread: ${Thread.currentThread().name}")
        }

        // 挂起函数后并不会往下继续执行，只有等挂起函数执行完毕才能接着往下执行，但这个挂起不是暂停，而是脱离的意思，
        // 脱离到其他线程执行完毕再切换原有线程继续往下执行。
        GlobalScope.launch(Dispatchers.Main) {
            // 1
            withContext(Dispatchers.IO) {
                Log.i("GlobalScope_Demo", "launch_IO1: ${Thread.currentThread().name}")
            }
            // 2
            withContext(Dispatchers.IO) {
                Log.i("GlobalScope_Demo", "launch_IO2: ${Thread.currentThread().name}")
            }

            // 3
            extractWithContext()

            // 4
            extractDelay()

            // 5
            Log.i("GlobalScope_Demo", "launch_Main: ${Thread.currentThread().name}")
        }

        // 0
        Log.i("GlobalScope_Demo", "main_out: ${Thread.currentThread().name}")

    }

    // 可以把withContext放进单独的一个函数内部，但函数需要添加suspend关键字（因为withContext 是一个 suspend 函数，
// 它需要在协程或者是另一个 suspend 函数中调用）
    private suspend fun extractWithContext() = withContext(Dispatchers.IO) {
        Log.i("GlobalScope_Demo", "extractWithContext_IO3: ${Thread.currentThread().name}")
    }

    private suspend fun extractDelay() {
        // 等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作
        delay(1000)
        Log.i("GlobalScope_Demo", "extractDelay: ${Thread.currentThread().name}")
    }

    /**
     *协程的启动模式demo
     */
    private fun coroutineStartStrategyDemo() {
        defaultStrategyDemo()
//        lazyStrategyDemo()
    }

    /**
     * DEFAULT 是饿汉式启动， launch 调用后会立即进入待调度状态，一旦调度器 OK 就可以开始执行。默认调度器是一个
     * 线程池，而线程的调度顺序取决于竞争cpu的时间片。
     *
     * 可能出现的输出顺序：132，123
     */
    private fun defaultStrategyDemo() {
        Log.i("defaultStrategyDemo", "1")
        GlobalScope.launch() {    // 默认调度器
            val job = coroutineContext[Job]
            Log.i("defaultStrategyDemo", "2")
        }
        Log.i("defaultStrategyDemo", "3")
    }

    /**
     * LAZY 是懒汉式启动， launch 后并不会有任何调度行为，协程体也自然不会进入执行状态，直到我们需要它执行的时候。
     * 调用 Job.start，主动触发协程的调度执行
     * 调用 Job.join，隐式的触发协程的调度执行
     */
    private fun lazyStrategyDemo() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.i("defaultStrategyDemo", "1")
            val launch = GlobalScope.launch(start = CoroutineStart.LAZY) {    // 默认调度器
                Log.i("defaultStrategyDemo", "2")
            }
            Log.i("defaultStrategyDemo", "3")
//        launch.start()  // 只是进入可调度状态，但协程所处的线程不一定能马上抢到cpu执行权。 可能出现的输出顺序：1342，1324
            launch.join()   // 因为要等待协程执行完毕，因此输出的结果一定是：1324
            Log.i("defaultStrategyDemo", "4")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}