package com.zkp.breath.component.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class ServiceA : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return BinderImp()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class BinderImp : Binder() {

    }
}
