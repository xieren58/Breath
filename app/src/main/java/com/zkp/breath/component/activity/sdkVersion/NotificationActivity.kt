package com.zkp.breath.component.activity.sdkVersion

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import android.view.View
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityNotificationBinding


/**
 * 通知栏
 * https://www.jianshu.com/p/cb8426620e74
 * https://www.jianshu.com/p/e1e20e0ee18c
 * https://www.jianshu.com/p/99bc32cd8ad6
 *
 * 注意：小米新系统miui12测试得知，如果没有接入小米推送渠道，那么过滤规则是“系统推荐”，这时候推送的消息会被归为
 * 不重要，如果接入小米推送渠道，那么过滤规则“全部设为重要”。
 */
class NotificationActivity : ClickBaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        varargSetClickListener(binding.tvNotification)

        handler = Handler(Looper.getMainLooper(), object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                notification()
                handler.sendEmptyMessageDelayed(0, 3000L)
                return true
            }
        })
        AppUtils.registerAppStatusChangedListener(onAppStatusChangedListener)
    }

    private val onAppStatusChangedListener = object : Utils.OnAppStatusChangedListener {
        override fun onForeground(activity: Activity?) {
            handler.removeCallbacksAndMessages(null)
        }

        override fun onBackground(activity: Activity?) {
            handler.sendEmptyMessageDelayed(0, 3000L)
        }
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

        // 获取NotificationManager
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        builder = NotificationCompat.Builder(this)
        builder.setSmallIcon(R.mipmap.ic_launcher)//设置小图标（左边），否则通知不会显示
                .setLargeIcon(ImageUtils.getBitmap(R.drawable.ic_yq))// 设置大图标（右边）
                .setContentTitle("我是通知的标题")//设置通知标题
                .setContentText("我是一个通知：$position")//设置通知内容
                .setContentIntent(clickPI)// 设置pendingIntent,点击通知时就会用到
                .setAutoCancel(true)//设为true，点击后通知栏移除通知，setAutoCancel需要和setContentIntent一起使用，否则无效
                .setDeleteIntent(cacelPI)//设置pendingIntent,左滑右滑通知时就会用到。
//                .setTimeoutAfter(5000)//设置超时时间，超时之后自动取消（Android8.0有效）
//                .setSortKey(position.toString())//设置针对一个包内的通知进行排序的键值，键值是一个字符串，通知会按照键值的顺序排列。
//                /**
//                 * 设置通知时间，默认为系统发出通知的时间，通常不用设置。setWhen只是为通知设置时间戳，
//                 * 和是否显示时间没有任何关系，设置是否显示时间的方法是setShowWhen。
//                 */
//                .setWhen(System.currentTimeMillis())
//                /**
//                 * 设置是否显示当前时间,另外当已经设置了setUsesChronometer(true)，则当前时间就显示不了
//                 * （除非定制手机处理），默认情况下时间计时和时间戳的显示是在通知的同一区域。
//                 */
//                .setShowWhen(true)
//                .setUsesChronometer(true)//设置是否显示时间计时，电话通知就会使用到。如果没有设置也没有影响，通知默认是当前时间戳。
//                .setOngoing(true)//设置是否是正在进行中的通知，默认是false，如果设置成true，左右滑动的时候就不会被删除了
//                /**
//                 * 默认值是false，如果设置成true，那么一旦状态栏有ID为n的通知，再次调用
//                 * notificationManager.notify(n, notification)时，将不会有震动、闪灯、提示音以及悬挂通知的提醒。
//                 */
//                .setOnlyAlertOnce(true)
//                /**
//                 * 为通知设置设置一个进度条
//                 * max：最大值
//                 * progress：当前进度
//                 * indeterminate：进度是否确定，true的话进度条的进度显示就是progress，false的话进度条不会显示progress而是一个动画
//                 */
//                .setProgress(100, currentProgress, false)

        style()

        val extras = Bundle()
        extras.putString("key", "value")

        builder
//                .addExtras(extras)
//                .addAction()
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setNumber(2121)
//                .setLights(Color.RED, 2000, Color.BLUE)
                .setSubText("我是一个SubText")  // 现在只有官方模拟器有显示，国内厂商系统都没显示
                /**
                 * VISIBILITY_PUBLIC 任何情况都会显示通知
                 * VISIBILITY_PRIVATE 只有在没有锁屏时会显示通知
                 * VISIBILITY_SECRET 在安全锁和没有锁屏的情况下显示通知
                 *
                 * 有些手机对通知做了很大的处理
                 * 必须开启顶端通知的开关才能显示悬挂通知(就是挂在屏幕顶端的通知)；
                 * 必须开启锁屏通知的开关才能在锁屏的情况下显示通知。
                 */
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                /**
//                 * 设置通知栏颜色，目前测试大多测试机没有效果，目前因为定制机的影响，setColor作用不是很大了。
//                 * 建议还是不要设置颜色值为好。
//                 */
//                .setColor(Color.RED)


        channel(notificationManager)

        val build = builder.build()
        // id相同的会互相覆盖
        notificationManager.notify(position, build)

        position++
        currentProgress += 10
    }

    /**
     * Android 8.0以上新增了通知渠道这个概念，如果没有设置，则通知无法在Android8.0的机器上显示
     *
     * NotificationChannel是通知渠道的意思，channelID为通知渠道ID，channelName为通知渠道名称
     * ，NotificationManager.IMPORTANCE_HIGH为通知渠道的优先级。
     */
    private fun channel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "DemoChannelId"
            val channelName = "DemoChannelName" // 在“通知管理”-“通知类型”中就有对应的名字

            /**
             * 在平时用的比较多了还是IMPORTANCE_HIGH，因为这个属性同样被定制机影响，这个只要了解就行，
             * 做系统级APP也许会深入的使用，普通的APP只要使用IMPORTANCE_HIGH就可以了。
             *
             * NotificationManager.IMPORTANCE_HIGH：紧急。有声音提示，有悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_DEFAULT：高。有声音提示，但没有悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_LOW：中。没声音提示，没悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_MIN：低。没声音提示，没悬挂通知，有通知但是通知样式很简陋，状态栏中无显示
             * IMPORTANCE_NONE 关闭通知
             */
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.setDescription("我是DemoChannelName的描述")//设置渠道的描述信息

            //            channel.setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.calling),
            //                    Notification.AUDIO_ATTRIBUTES_DEFAULT)
            //            channel.enableLights(true)//设置通知出现时的闪灯（如果 android 设备支持的话）,有些手机呼吸灯只支持一种颜色。
            //            channel.setLightColor(Color.RED)

            channel.enableVibration(true)// 设置通知出现时的震动（如果 android 设备支持的话）
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            notificationManager.createNotificationChannel(channel)

            //创建通知时指定channelID
            builder.setChannelId(channelID)
        }
    }

    /**
     * 类似于二级内容，在放大下显示的内容。
     */
    private fun style() {
        // 大图类型
//        val bigPictureStyle = NotificationCompat.BigPictureStyle()
//        bigPictureStyle.setBigContentTitle("我是BigPictureStyle设置的Title")
//        bigPictureStyle.setSummaryText("我是BigPictureStyle设置的SummaryText")
//        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.ic_wh_1_1))
//        builder.setStyle(bigPictureStyle)

        // 大标题类型
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.setBigContentTitle("我是BigTextStyle设置的Title")
        bigTextStyle.setSummaryText("我是BigTextStyle设置的SummaryText") // 在模拟器和手机上都看到效果
        bigTextStyle.bigText("我是BigTextStyle设置的bigText")
        builder.setStyle(bigTextStyle)

//        val inboxStyle = NotificationCompat.InboxStyle()
//        inboxStyle.addLine("第一行")
//        inboxStyle.addLine("第二行")
//        inboxStyle.addLine("第三行")
//        inboxStyle.addLine("第四行")
//        inboxStyle.addLine("第五行")
//        inboxStyle.addLine("第六行")
//        inboxStyle.addLine("第七行")
//        inboxStyle.addLine("第八行")
//        inboxStyle.addLine("第九行")
//        inboxStyle.setBigContentTitle(title)
//        inboxStyle.setSummaryText("我是InboxStyle设置的SummaryText！")// 不知道有什么用
//        builder.setStyle(inboxStyle)
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

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        AppUtils.unregisterAppStatusChangedListener(onAppStatusChangedListener)
    }

}