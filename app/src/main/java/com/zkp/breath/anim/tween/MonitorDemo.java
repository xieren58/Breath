package com.zkp.breath.anim.tween;

import android.util.Log;

/**
 * Created b Zwp on 2019/8/1.
 */
public class MonitorDemo {

    // ===========  monitor为类的实例对象  start===============
    // ===========  monitor为类的实例对象  start===============
    public synchronized void xxx() {
        Log.i("Thread_Log", "xxx: ");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void yyy() {
        Log.i("Thread_Log", "yyy: ");
    }

    public void zzz() {
        synchronized (this) {
            Log.i("Thread_Log", "zzz: ");
        }
    }

    public void nnn() {
        synchronized (MonitorDemo.class) {
            Log.i("Thread_Log", "zzz: ");
        }
    }

    public void mmm() {
        synchronized (MonitorDemo.class) {
            Log.i("Thread_Log", "zzz: ");
        }
    }

    // ===========  monitor为类的实例对象  end===============
    // ===========  monitor为类的实例对象  end===============

    // ===========  monitor为类对象  start===============
    // ===========  monitor为类对象  start===============
    public static synchronized void aaa() {
        Log.i("Thread_Log", "aaa: ");
    }

    public static synchronized void bbb() {
        Log.i("Thread_Log", "bbb: ");
    }

    // ===========  monitor为类对象  end===============
    // ===========  monitor为类对象  end===============
}
