package com.zkp.breath.review;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;

/**
 * https://mp.weixin.qq.com/s/kpl8X9ZjOO_DewitoT7j-w
 * <p>
 * 当前消息队列空闲的时候做些事情从而提升效率，不过最好不要做耗时操作，注意返回值true则会重复触发，false则表示只触发一次
 * <p>
 * 场景
 * 1.Activity启动优化：onCreate，onStart，onResume中耗时较短但非必要的代码可以放到IdleHandler中执行，减少启动时间
 * 2.一些第三方库中有使用，比如LeakCanary，Glide中有使用到。
 */
public class IdleHandlerDemo {

    public static void main(String[] args) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("IdleHandler1 queueIdle");
                return false;
            }
        });

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("IdleHandler2 queueIdle");
                return false;
            }
        });

        Handler handler = new Handler();
        // 添加第一个Message
        Message message1 = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("message1");
            }
        });
        message1.sendToTarget();

        // 添加第二个Message
        Message message2 = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("message2");
            }
        });
        message2.sendToTarget();
    }
}
