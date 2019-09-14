package com.zkp.breath.component.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.os.IResultReceiver
import android.util.Log

class ServiceA : Service() {

    val TAG = javaClass.name

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand()")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind()")
        return BinderImp()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        super.onDestroy()
    }

    fun f1() {
        Log.i(TAG, "f1")
    }

    inner class BinderImp : IResultReceiver.Stub() {
        override fun send(p0: Int, p1: Bundle?) {
            if (p0 == SendCode.Code1) {
                Log.i(TAG, "send_code_1")
            }
        }
    }

    companion object SendCode {
        val Code1 = 1
    }

}
