package com.zkp.breath.component.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlin.concurrent.thread

/**
 * https://juejin.cn/post/6893791473121280013
 * https://mp.weixin.qq.com/s/V1xI2M8AibgB2whHSOTQGQ
 *
 *
 * 1. Handler的基本原理
 *   Handler持有Looper引用，Looper持有MessageQueue单向链表构成的优先级队列（取的都是头部，所以说是队列），
 *   MessageQueue存放Message。Handler通过Looper将Message存放到MessageQueue中，Looper通过MessageQueue
 *   取出Message交给Handler处理，整个过程其实就是生产-消费模式。
 *
 * 2. 子线程中怎么创建Handler
 *   子线程中使用 Handler 需要先执行两个操作：Looper.prepare （创建自线程的Looper，存放在ThreadLocal中）和
 *   Looper.loop （开启Looper）。如果直接创建Handler对象会报错，因为其构造函数会检查是否存在Looper，每个 Thread
 *   只能有一个 Looper，否则也会抛出异常。注意的是当处理完所有事情或者生命周期Destroy的时候要即时调用Looper.myLooper().quit()
 *   来终止消息循环，因为当执行Looper.loop后会处于死循环状态，所以子线程并不会结束。
 *
 * 3. 为什么主线程不需要手动调用 Looper.prepare 和 Looper.loop
 *    ActivityThread 的 main 方法中已经进行了调用，即app运行的时候就已经创建完毕。（里面有个名字叫H的Handler，
 *    专门用于处理四大组件和application的消息，比如创建Service，绑定Service的一些消息。）
 *
 * 4. 线程和 Handler Looper MessageQueue 的关系
 *   一个线程对应一个 Looper 对应一个 MessageQueue 对应多个 Handler，因为Message中的target记录的对应的Handler
 *   所以消息能找到对应的Handler进行下发。
 *
 * 5. Handler消息延迟是怎么处理的？
 *   延迟的时间等于"开机时长 + 指定延迟时长"，MessageQueue存放Message进入链表会根据指定的延时时长（没指定则位0）
 *   进行顺序排序。一旦有新的Message进入链表，就会唤醒MessageQueue进行取消息。
 *
 * 6. View.post 和 Handler.post 的区别
 *  > 如果在 performTraversals 前调用 View.post，则会将消息进行保存，之后在 dispatchAttachedToWindow 的时候
 *    通过 ViewRootImpl 中的 Handler 进行调用。
 *  > 如果在 performTraversals 以后调用 View.post，则直接通过 ViewRootImpl 中的 Handler 进行调用。
 *
 *    衍生问题：为什么 View.post 里可以拿到 View 的宽高信息呢？
 *        因为 View.post 的 Runnable 执行的时候，已经执行过 performTraversals 了，也就是 View 的 measure
 *        layout draw 方法都执行过了，自然可以获取到 View 的宽高信息了。
 *
 * 7. 非 UI 线程真的不能操作 View 吗
 *   在执行 UI 操作的时候，都会调用到 ViewRootImpl 里，以 requestLayout 为例，在 requestLayout 里会通过
 *   checkThread 进行线程的检查，也就是创建ViewRootImpl的线程必须和调用checkThread所在的线程一致，UI的更新
 *   并非只能在主线程才能进行，而创建ViewRootImpl一般都是在主线程。
 *
 *   衍生问题：系统为什么不建议在子线程中访问UI？
 *      因为 Android 的UI控件不是线程安全的，如果在多线程中并发访问可能会导致UI控件处于不可预期的状态。如果上锁的话
 *      会降低UI访问的效率，因为锁机制会阻塞某些线程的执行。所以最简单且高效的方法就是采用单线程模型来处理UI操作。
 *
 *
 */
class HandlerActivity : BaseActivity(R.layout.activity_handler) {

    // 有个主线程的handler，然后多个线程使用。
    //
    private val handler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            val obj = msg.obj
            Log.i("handleMessage", "obj: $obj")
            return true
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        threadShowDialog()

        val textView = TextView(this)
        textView.requestLayout()
    }

    var threadLooper: Looper? = null

    private fun threadShowDialog() {
        thread {
            //创建线程的Looper，MessageQueue
            Looper.prepare()
            threadLooper = Looper.myLooper()
            Handler().post {
                val builder = AlertDialog.Builder(this)
                val alertDialog = builder.create()
                alertDialog.show()
                alertDialog.hide()
            }
            //开始处理消息
            Looper.loop()
        }
    }

    override fun onDestroy() {
        threadLooper?.quit()
        super.onDestroy()
    }

}