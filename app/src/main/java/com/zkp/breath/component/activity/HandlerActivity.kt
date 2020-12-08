package com.zkp.breath.component.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity

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
    }


}