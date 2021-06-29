package com.zkp.breath.component.activity.sdkVersion

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
 *
 * https://www.jianshu.com/p/cb8426620e74
 * https://www.jianshu.com/p/e1e20e0ee18c
 * https://www.jianshu.com/p/99bc32cd8ad6
 *
 * 0. 小米新系统miui12测试得知，如果没有接入小米推送渠道，那么过滤规则是“系统推荐”，这时候推送的消息会被归为不重要，
 *    如果接入小米推送渠道，那么过滤规则“全部设为重要”。
 * 1. 使用NotificationCompat可以兼容所有版本，不要使用Notification。
 * 2. NotificationCompat.Builder和NotificationChannel 存在部分相同的api（demo中只在NotificationChannel中演示使用）。
 * 3. 通知设置中的“振动权限”,"发声权限",“锁屏通知权限”,“悬浮通知权限”等全局权限一旦用户改动，app卸载后重装还是和原来设置的一样
 *    并不会发生重置，猜测应该是国内厂商进行的数据缓存。
 * 4. 使用NotificationChannel再去修改一些属性不能生效，因为创建会以channelId为key进行缓存，所以想要修改生效只能清除
 *    缓存或者卸载app重装
 * 5. 在一些手机上，默认是没有开启“振动权限”,"发声权限"等全局权限的，要么引导用户设置（体验不好，用户也不明白怎么搞），
 *    这时候可以手动调用播放音效和振动来解决。 (见 NotificationAssist 工具类)
 *
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
        val clickPI =
            PendingIntent.getActivity(this, 1, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        // 通知消失时触发的回调
        val cacelIntent = Intent(this, NotificationBroadcastReceiver::class.java)
        cacelIntent.setAction("com.xxx.xxx.cancel")
        val cacelPI =
            PendingIntent.getBroadcast(this, 2, cacelIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        // 获取NotificationManager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        builder = NotificationCompat.Builder(this)
        builder.setSmallIcon(R.mipmap.ic_launcher)//设置小图标（左边），否则通知不会显示
            .setLargeIcon(ImageUtils.getBitmap(R.drawable.ic_yq))// 设置大图标（右边）
            .setContentTitle("我是通知的标题")//设置通知标题
            .setContentText("我是一个通知：$position")//设置通知内容
            .setNumber(2121) //桌面图片右上角显示的数字，有的显示圆点角标，需要注意的是该方法受到定制手机的影响。需要开启“通知权限-桌面图标角标权限”
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

//        setGroup()
//        style()
//        setVisibility()


        builder
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSubText("我是一个SubText")  // 现在只有官方模拟器有显示，国内厂商系统都没显示（在DecoratedCustomViewStyle下会显示）
            .setColor(Color.RED)  // 设置通知栏颜色，目前测试大多测试机没有效果，目前因为定制机的影响，setColor作用不是很大了。建议还是不要设置颜色值为好。

        channel(notificationManager)

        val build = builder.build()
//        builder.setPublicVersion(build)   //锁屏时显示通知
        notificationManager.notify(position, build) // id相同的会互相覆盖

        position++
        currentProgress += 10

//        playNotificationVibrate(this)
//        playNotificationRing(this)
    }

    /**
     * 设置可见性
     *
     * VISIBILITY_PUBLIC 任何情况都会显示通知
     * VISIBILITY_PRIVATE 只有在没有锁屏时会显示通知
     * VISIBILITY_SECRET 在安全锁和没有锁屏的情况下显示通知
     *
     * 有些手机对通知做了很大的处理
     * 必须开启顶端通知的开关才能显示悬挂通知(就是挂在屏幕顶端的通知)；
     * 必须开启锁屏通知的开关才能在锁屏的情况下显示通知。
     */
    private fun setVisibility() {
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }

    /**
     * 一般都不设置即可，如果当设置了setGroup（）了，那么一定要指定setGroupSummary（）
     * 1. 如果setGroupSummary和setGroup都不设置，Android7.0以上的时候会自动分组，默认是同一个组
     * 2. 如果每个通知都设置了setGroupSummary(true)，这样每个通知都作为主通知了，毫无意义。
     * 3. 如果只设置setGroup，但是不设置setGroupSummary，那么当通知比较多条的时候不会折叠。
     */
    private fun setGroup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {//Android4.4W以上
            if (position >= 0 && position <= 7) {
                builder.setGroup("notification_test");//捆绑通知
                if (position == 0) {
                    builder.setGroupSummary(true)//设置是否为一组通知的第一个显示
                }
            } else if (position > 7 && position <= 15) {
                builder.setGroup("notification_ceshi");//捆绑通知
                if (position == 8) {
                    builder.setGroupSummary(true)//设置是否为一组通知的第一个显示
                }
            }
        }
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
             * 参数三：重要程度。在平时用的比较多了还是IMPORTANCE_HIGH，和版本相关，28版本有，28以上则没有
             *
             * NotificationManager.IMPORTANCE_HIGH：紧急。有声音提示，有悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_DEFAULT：高。有声音提示，但没有悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_LOW：中。没声音提示，没悬挂通知，有通知，状态栏中显示
             * NotificationManager.IMPORTANCE_MIN：低。没声音提示，没悬挂通知，有通知但是通知样式很简陋，状态栏中无显示
             * IMPORTANCE_NONE 关闭通知
             */
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.setDescription("我是DemoChannelName的描述")//设置渠道的描述信息

//            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET)

            /**
             * 设置渠道通知铃声，需要注意的是先开启“发声权限”的全局权限
             */
//            channel.setSound(
//                Uri.parse("android.resource://" + packageName + "/" + R.raw.calling),
//                Notification.AUDIO_ATTRIBUTES_DEFAULT
//            )

            /**
             * 设置通知出现时的闪灯（如果 android 设备支持的话）,有些手机呼吸灯只支持一种颜色。需要注意的是先开启“允许呼吸灯闪烁”的全局权限
             * 小米6的渠道设置里面有“闪烁指示灯”设置，小米10则没有
             */
//            channel.enableLights(true)
//            channel.setLightColor(Color.WHITE)

            /**
             * 设置通知出现时的震动（如果 android 设备支持的话），需要注意的是先开启“允许振动”的全局权限
             * 但是无论手机怎么设置，都没有效果，只能使用手动振动。
             * @see NotificationAssist.kt#playNotificationVibrate()
             */
//            channel.enableVibration(true)
//            channel.vibrationPattern = longArrayOf(300, 3000, 3000, 300, 3000)

            notificationManager.createNotificationChannel(channel)

            //创建通知时指定channelID
            builder.setChannelId(channelID)
        }
    }

    /**
     * 类似于二级内容，在放大下显示的内容。
     */
    @SuppressLint("RemoteViewLayout")
    private fun style() {
        // 自定义view类型,注意布局的顶层只能是LinearLayout、FrameLayout、RelativeLayout三种基本布局其中之一
//        val remoteViews = RemoteViews(packageName, R.layout.view_custom_notification)
//        val decoratedCustomViewStyle = NotificationCompat.DecoratedCustomViewStyle()
//        builder.setStyle(decoratedCustomViewStyle)
//        builder.setCustomContentView(remoteViews)//设置悬挂通知和一般通知的布局
//        builder.setCustomContentView(remoteViews)//设置通知的布局
//        builder.setCustomBigContentView(remoteViews)//设置悬挂通知的布局

        // 大图类型
//        val bigPictureStyle = NotificationCompat.BigPictureStyle()
//        bigPictureStyle.setBigContentTitle("我是BigPictureStyle设置的Title")
//        bigPictureStyle.setSummaryText("我是BigPictureStyle设置的SummaryText")
//        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.ic_wh_1_1))
//        builder.setStyle(bigPictureStyle)

        // 大标题类型
//        val bigTextStyle = NotificationCompat.BigTextStyle()
//        bigTextStyle.setBigContentTitle("我是BigTextStyle设置的Title")
//        bigTextStyle.setSummaryText("我是BigTextStyle设置的SummaryText") // 在模拟器和手机上都看不到效果
//        bigTextStyle.bigText("我是BigTextStyle设置的bigText")
//        builder.setStyle(bigTextStyle)

        // 收件箱类型（和ListView一样）
//        val inboxStyle = NotificationCompat.InboxStyle()
//        inboxStyle.addLine("第一行")
//        inboxStyle.addLine("第二行")
//        inboxStyle.addLine("第三行")
//        inboxStyle.addLine("第四行")
//        inboxStyle.addLine("第五行")
//        inboxStyle.addLine("第六行")
//        inboxStyle.setBigContentTitle("我是BigTextStyle设置的Title")
//        inboxStyle.setSummaryText("我是InboxStyle设置的SummaryText") // 模拟器和手机都看不到效果
//        builder.setStyle(inboxStyle)

        // 消息类型（没怎么用过）
//        val messagingStyle = NotificationCompat.MessagingStyle("用户显示名")
//        messagingStyle.addMessage("发送的内容", System.currentTimeMillis(), "发送者")
//        messagingStyle.setConversationTitle("消息标题")
//        builder.setStyle(messagingStyle)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        AppUtils.unregisterAppStatusChangedListener(onAppStatusChangedListener)
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