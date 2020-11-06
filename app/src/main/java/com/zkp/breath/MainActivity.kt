package com.zkp.breath

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.*
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.debugs.DebugActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import com.zkp.breath.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listOf = mutableListOf(
            "TabLayout", "TextView", "Button", "Switch", "ImageViewAdjustViewBounds",
            "ImageViewScaleType", "MotionLayout", "Debug",
            "Rxjava3", "Glide4", "Fragment", "ViewEvent", "Service",
            "JetPackStartUp", "JetPackViewModel", "JetPackViewBinding",
            "JetPackLifecycle", "JetPackLiveData", "JetPackDataBinding", "JetPackPaging", "JetPackRoom"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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
            "TabLayout" -> {
                ActivityUtils.startActivity(TabLayoutActivity::class.java)
                return@OnItemClickListener
            }
            "TextView" -> {
                ActivityUtils.startActivity(TextViewActivity::class.java)
                return@OnItemClickListener
            }
            "Button" -> {
                ActivityUtils.startActivity(ButtonActivity::class.java)
                return@OnItemClickListener
            }
            "Switch" -> {
                ActivityUtils.startActivity(SwitchActivity::class.java)
                return@OnItemClickListener
            }
            "ImageViewAdjustViewBounds" -> {
                ActivityUtils.startActivity(ImageViewAdjustViewBoundsActivity::class.java)
                return@OnItemClickListener
            }
            "ImageViewScaleType" -> {
                ActivityUtils.startActivity(ImageViewScaleTypeActivity::class.java)
                return@OnItemClickListener
            }
            "MotionLayout" -> {
                ActivityUtils.startActivity(MotionLayoutActivity::class.java)
                return@OnItemClickListener
            }
            "Debug" -> {
                ActivityUtils.startActivity(DebugActivity::class.java)
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
            "Fragment" -> {
                ActivityUtils.startActivity(FragmentDemoActivity::class.java)
                return@OnItemClickListener
            }
            "ViewEvent" -> {
                ActivityUtils.startActivity(EventActivity::class.java)
                return@OnItemClickListener
            }
            "Service" -> {
                ActivityUtils.startActivity(ServiceDemoActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackStartUp" -> {
                ActivityUtils.startActivity(StartupActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackViewModel" -> {
                ActivityUtils.startActivity(ViewModelActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackViewBinding" -> {
                ActivityUtils.startActivity(ViewBindingActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackLifecycle" -> {
                ActivityUtils.startActivity(LifecycleActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackLiveData" -> {
                ActivityUtils.startActivity(LiveDataActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackDataBinding" -> {
                ActivityUtils.startActivity(DataBindingActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackPaging" -> {
                ActivityUtils.startActivity(PagingActivity::class.java)
                return@OnItemClickListener
            }
            "JetPackRoom" -> {
                ActivityUtils.startActivity(RoomActivity::class.java)
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