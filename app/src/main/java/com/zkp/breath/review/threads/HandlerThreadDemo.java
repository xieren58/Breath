package com.zkp.breath.review.threads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * 特点：Thread+Looper，实现接收消息在工作线程进行处理。（其实就像自己封装的Thread+Handler）
 */
public class HandlerThreadDemo {

    public static void main(String[] args) {
        // 步骤1：创建HandlerThread实例对象
        HandlerThread handlerThread = new HandlerThread("handler_thread");

        // 步骤2：创建工作线程Handler & 复写handleMessage（）
        // 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
        // 注：消息处理操作（HandlerMessage（））所处的线程为HandlerThread线程，即是工作线程
        Handler handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                // 消息处理
                return true;
            }
        });

        // 步骤3：启动线程
        handlerThread.start();

        // 步骤4：使用工作线程Handler向工作线程的消息队列发送消息
        // 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
        // a. 定义要发送的消息
        Message msg = Message.obtain();
        msg.what = 2; //消息的标识
        msg.obj = "B"; // 消息的存放
        // b. 通过Handler发送消息到其绑定的消息队列
        handler.sendMessage(msg);

        // 步骤5：结束线程，即停止线程的消息循环
        handler.removeCallbacksAndMessages(null);
        handlerThread.quit();
    }
}
