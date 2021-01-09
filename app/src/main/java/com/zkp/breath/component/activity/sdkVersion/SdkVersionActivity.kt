package com.zkp.breath.component.activity.sdkVersion

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_entrance.*

class SdkVersionActivity : BaseActivity(R.layout.activity_entrance) {

    private val listOf = mutableListOf(
            "ScopedStorage", "Toast", "HttpClearText", "Clipboard", "Location", "DeviceInfo"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.setBackgroundColor(ColorUtils.getColor(R.color.colorFF009688))
        initRcv()
    }

    private fun initRcv() {
        rcv.addItemDecoration(EntranceItemDecoration())
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val gridLayoutManager = GridLayoutManager(rcv.context, 2)
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = EntranceAdapter(listOf)
        gridAdapter.setOnItemClickListener(onItemChildClickListener)
        rcv.adapter = gridAdapter
    }

    private val onItemChildClickListener = OnItemClickListener { _, _, position ->
        when (val type = listOf[position]) {
            "ScopedStorage" -> {
                ActivityUtils.startActivity(ScopedStorageQ10Activity::class.java)
                return@OnItemClickListener
            }
            "Toast" -> {
                ActivityUtils.startActivity(ToastR11Activity::class.java)
                return@OnItemClickListener
            }
            "HttpClearText" -> {
                ActivityUtils.startActivity(HttpClearTextActivity::class.java)
                return@OnItemClickListener
            }
            "Clipboard" -> {
                ActivityUtils.startActivity(ClipboardQ10Activity::class.java)
                return@OnItemClickListener
            }
            "Location" -> {
                ActivityUtils.startActivity(LocationQ10Activity::class.java)
                return@OnItemClickListener
            }
            "DeviceInfo" -> {
                ActivityUtils.startActivity(DeviceInfoQ10Activity::class.java)
                return@OnItemClickListener
            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

}