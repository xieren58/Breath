package com.zkp.breath

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.meituan.android.walle.WalleChannelReader
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.*
import com.zkp.breath.component.activity.arouter.ARouterActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.blankj.BlankjActivity
import com.zkp.breath.component.activity.debugs.DebugActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.sdkVersion.SdkVersionActivity
import com.zkp.breath.component.activity.third.GlideActivity
import com.zkp.breath.component.activity.third.MMKVActivity
import com.zkp.breath.component.activity.third.RxJava3Activity
import com.zkp.breath.component.activity.web.H5WakeAppActivity
import com.zkp.breath.component.activity.weight.*
import com.zkp.breath.databinding.ActivityEntranceBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityEntranceBinding

    private val listOf = mutableListOf(
        "计算入口m5", "Component", "JetPack", "Handler", "Weight", "SdkVersion", "ARoute", "BlankjActivity",
        "Coroutines", "Debug", "MMKV", "Rxjava3", "Glide4", "web"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntranceBinding.inflate(layoutInflater)
        binding.root.setBackgroundColor(ColorUtils.getColor(R.color.colorFF3F51B5))
        setContentView(binding.root)

        umEvent()
        initRcv()
        requestPermission()
    }

    private fun requestPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE)
            .rationale { activity, shouldRequest ->
                Log.i(ACTIVITY_TAG, "rationale")
                shouldRequest?.again(true)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    Log.i(ACTIVITY_TAG, "onGranted")
                }

                override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                    Log.i(ACTIVITY_TAG, "onDenied")
                    if (deniedForever.isNotEmpty()) {
                        // 防止title和message无显示
                        val builder: AlertDialog.Builder = AlertDialog.Builder(
                            this@MainActivity,
                            R.style.Theme_AppCompat_Light_Dialog_Alert
                        )
                        builder.setCancelable(false)
                        val alertDialog = builder.setTitle("提示！")
                            .setMessage("请前往设置->应用->权限中打开读写相关权限，否则功能无法正常运行！")
                            .setPositiveButton("确定") { dialog: DialogInterface?, which: Int ->
                                dialog?.dismiss()
                                AppUtils.launchAppDetailsSettings()
                            }
                            .show()
                    }
                }
            }).request()
    }

    private fun initRcv() {
        val rcv = binding.rcv
        rcv.addItemDecoration(EntranceItemDecoration())
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val gridLayoutManager = GridLayoutManager(rcv.context, 2)
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = EntranceAdapter(listOf)
        gridAdapter.setOnItemClickListener(onItemChildClickListener)
        rcv.adapter = gridAdapter
    }


    /**
     * 非内联系函数的lambda表达式不允许直接return，只能使用"@+标签"退出lambda表达式
     */
    private val onItemChildClickListener = OnItemClickListener { _, _, position ->
        when (val type = listOf[position]) {
            "计算入口m5" -> {
                val channel = WalleChannelReader.getChannel(this.applicationContext)
                ToastUtils.showShort("渠道: $channel")
                ActivityUtils.startActivity(LLM5Activity::class.java)
                return@OnItemClickListener
            }
            "MMKV" -> {
                ActivityUtils.startActivity(MMKVActivity::class.java)
                return@OnItemClickListener
            }
            "Handler" -> {
                ActivityUtils.startActivity(HandlerActivity::class.java)
                return@OnItemClickListener
            }
            "Coroutines" -> {
                ActivityUtils.startActivity(CoroutinesActivity::class.java)
                return@OnItemClickListener
            }
            "Debug" -> {
                ActivityUtils.startActivity(DebugActivity::class.java)
                return@OnItemClickListener
            }
            "Weight" -> {
                ActivityUtils.startActivity(WeightActivity::class.java)
                return@OnItemClickListener
            }
            "SdkVersion" -> {
                ActivityUtils.startActivity(SdkVersionActivity::class.java)
                return@OnItemClickListener
            }
            "ARoute" -> {
                ActivityUtils.startActivity(ARouterActivity::class.java)
                return@OnItemClickListener
            }
            "BlankjActivity" -> {
                ActivityUtils.startActivity(BlankjActivity::class.java)
                return@OnItemClickListener
            }
            "Rxjava3" -> {
                ActivityUtils.startActivity(RxJava3Activity::class.java)
                return@OnItemClickListener
            }
            "Glide4" -> {
                ActivityUtils.startActivity(GlideActivity::class.java)
                return@OnItemClickListener
            }
            "Component" -> {
                ActivityUtils.startActivity(ComponentActivity::class.java)
                return@OnItemClickListener
            }
            "JetPack" -> {
                ActivityUtils.startActivity(JetPackActivity::class.java)
                return@OnItemClickListener
            }
            "web" -> {
                ActivityUtils.startActivity(H5WakeAppActivity::class.java)
                return@OnItemClickListener
            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

    private fun umEvent() {
        // 模拟UM的事件统计
        val umEventMap: MutableMap<String, Any> = HashMap()
        umEventMap["name"] = "zkp"
        umEventMap["page_name"] = "main_activity"
        MobclickAgent.onEventObject(this, "um_main_event", umEventMap)
    }

    //  保存点击的时间
    private var exitTime: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showShort("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            ActivityUtils.finishAllActivities()
        }
    }

}