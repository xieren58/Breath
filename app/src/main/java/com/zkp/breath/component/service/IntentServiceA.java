package com.zkp.breath.component.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * 内部核心HandlerThread(继承了线程Thread和内部创建了该线程的Looper)和ServiceHandler(接受HandlerThread创建的
 * Looper用于处理消息的Handler)，外部携带的Intent用Message包裹然后发送给mServiceHandler，然后提供一个抽象方法
 * onHandleIntent让用户接管处理消息（内部又把msg强转回Intent）
 */
public class IntentServiceA extends IntentService {

    public IntentServiceA() {
        super("IntentServiceA");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
