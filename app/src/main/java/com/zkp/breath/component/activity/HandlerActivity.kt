package com.zkp.breath.component.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_handler.*

/**
 * 1. Handler的基本原理
 *   Handler持有Looper引用，Looper持有MessageQueue单向链表，MessageQueue存放Message。Handler通过Looper将
 *   Message存放到MessageQueue中，Looper通过MessageQueue取出Message交给Handler处理。
 *
 * 2. 子线程中怎么创建Handler
 *   子线程中使用 Handler 需要先执行两个操作：Looper.prepare （创建自线程的Looper，存放在ThreadLocal中）和
 *   Looper.loop （开启Looper）。如果直接创建Handler对象会报错，因为其构造函数会检查是否存在Looper，每个 Thread
 *   只能有一个 Looper，否则也会抛出异常。
 *
 * 3. 为什么主线程不需要手动调用 Looper.prepare 和 Looper.loop
 *    ActivityThread.main 中已经进行了调用，即app运行的时候就已经创建完毕。
 *
 * 4. 线程和 Handler Looper MessageQueue 的关系
 *   一个线程对应一个 Looper 对应一个 MessageQueue 对应多个 Handler
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
 *   checkThread 进行线程的检查。
 *
 *
 */
class HandlerActivity : BaseActivity(R.layout.activity_handler) {

    // 有个主线程的handler，然后多个线程使用。
    //
    private val handler = Handler(mainLooper, object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            val obj = msg.obj
            Log.i("handleMessage", "obj: $obj")
            return true
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.requestLayout()
    }


}