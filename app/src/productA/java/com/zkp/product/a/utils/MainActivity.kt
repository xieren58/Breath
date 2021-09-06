package com.zkp.product.a.utils

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.R
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.*
import com.zkp.breath.component.activity.arouter.ARouterActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.blankj.BlankjActivity
import com.zkp.breath.component.activity.debugs.DebugActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.kotlin.CoroutinesActivity
import com.zkp.breath.component.activity.sdkVersion.SdkVersionActivity
import com.zkp.breath.component.activity.third.GlideActivity
import com.zkp.breath.component.activity.third.MMKVActivity
import com.zkp.breath.component.activity.third.RxJava3Activity
import com.zkp.breath.component.activity.weight.*
import com.zkp.breath.databinding.ActivityEntranceBinding
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityEntranceBinding

    private val listOf = mutableListOf(
        "MMKV", "Handler", "Weight", "SdkVersion", "ARoute", "BlankjActivity",
        "Coroutines", "Debug",
        "Rxjava3", "Glide4", "Component", "JetPack"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntranceBinding.inflate(layoutInflater)
        binding.root.setBackgroundColor(ColorUtils.getColor(R.color.colorFF3F51B5))
        setContentView(binding.root)

        umEvent()
        initRcv()
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
            "MMKV" -> {
                ActivityUtils.startActivity(MMKVActivity::class.java)
                return@OnItemClickListener
            }
            "Handler" -> {
                ToastUtils.showShort("3456789")
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
            exitProcess(0)
        }
    }

}