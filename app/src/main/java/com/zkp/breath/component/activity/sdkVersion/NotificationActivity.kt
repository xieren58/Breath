package com.zkp.breath.component.activity.sdkVersion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityNotificationBinding


/**
 * 通知栏
 * https://www.jianshu.com/p/cb8426620e74
 * https://www.jianshu.com/p/e1e20e0ee18c
 */
class NotificationActivity : ClickBaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        varargSetClickListener(binding.tvNotification)
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvNotification == v) {
            notification()
            return
        }
    }

    var position = 0
    var currentProgress = 0
    lateinit var builder: NotificationCompat.Builder

    fun notification() {
        // 点击事件
        val clickIntent = Intent(this, ClipboardQ10Activity::class.java)
        val clickPI = PendingIntent.getActivity(this, 1, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        // 通知消失时触发的回调
        val cacelIntent = Intent(this, NotificationBroadcastReceiver::class.java)
        cacelIntent.setAction("com.xxx.xxx.cancel")
        val cacelPI = PendingIntent.getBroadcast(this, 2, cacelIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        builder = NotificationCompat.Builder(this, "DemoChannelId")
        builder.setSmallIcon(R.mipmap.ic_launcher)//设置小图标，否则通知不会显示
                .setContentTitle("我是通知的标题")//设置通知标题
                .setContentText("我是一个通知：" + position)//设置通知内容
                .setContentIntent(clickPI)// 设置pendingIntent,点击通知时就会用到
                .setAutoCancel(true)//设为true，点击后通知栏移除通知，setAutoCancel需要和setContentIntent一起使用，否则无效
                .setDeleteIntent(cacelPI)//设置pendingIntent,左滑右滑通知时就会用到。
                .setOngoing(true)//设置是否是正在进行中的通知，默认是false，如果设置成true，左右滑动的时候就不会被删除了
                /**
                 * 默认值是false，如果设置成true，那么一旦状态栏有ID为n的通知，再次调用
                 * notificationManager.notify(n, notification)时，将不会有震动、闪灯、提示音以及悬挂通知的提醒。
                 */
                .setOnlyAlertOnce(true)
                /**
                 * 为通知设置设置一个进度条
                 * max：最大值
                 * progress：当前进度
                 * indeterminate：进度是否确定，true的话进度条的进度显示就是progress，false的话进度条不会显示progress而是一个动画
                 */
                .setProgress(100, currentProgress, false)

        builder.setLargeIcon(ImageUtils.getBitmap(R.mipmap.ic_launcher))
                .setNumber(2121)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Android 8.0以上
            val channelID = "DemoChannelId"
            val channelName = "DemoChannelName" // 在“通知管理”-“通知类型”中就有对应的名字
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            //channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel)
            //创建通知时指定channelID
            builder.setChannelId(channelID)
            //builder.setTimeoutAfter(5000);//设置超时时间，超时之后自动取消（Android8.0有效）
        }

        val build = builder.build()
        notificationManager.notify(1, build)

        position++
        currentProgress += 10
    }

    class NotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (action == "com.xxx.xxx.cancel") {
                //处理滑动清除和点击删除事件
                ToastUtils.showShort("cancel")
            }
        }
    }

}