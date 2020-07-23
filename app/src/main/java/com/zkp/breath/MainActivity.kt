package com.zkp.breath

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listOf = mutableListOf(
            "kotlin", "java", "jetpack"
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
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val gridLayoutManager = GridLayoutManager(rcv.context, 2)
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = EntranceAdapter(listOf)
        rcv.adapter = gridAdapter
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                exitProcess(0)
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}