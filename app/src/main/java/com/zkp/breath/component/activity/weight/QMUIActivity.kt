package com.zkp.breath.component.activity.weight

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
import com.zkp.breath.component.activity.EventActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import kotlinx.android.synthetic.main.activity_entrance.*

class QMUIActivity : BaseActivity(R.layout.activity_entrance) {

    private val listOf = mutableListOf(
            "QMUIButton", "QMUIDialog"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.setBackgroundColor(ColorUtils.getColor(R.color.color808844FF))
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
            "QMUIButton" -> {
                ActivityUtils.startActivity(QMUIButtonActivity::class.java)
                return@OnItemClickListener
            }
            "QMUIDialog" -> {

            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

}