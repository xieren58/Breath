package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import kotlinx.android.synthetic.main.activity_jetpack.*

class JetPackActivity : BaseActivity(R.layout.activity_jetpack) {

    private val listOf = mutableListOf(
            "JetPackStartUp", "JetPackViewModel", "JetPackViewBinding", "JetPackLifecycle",
            "JetPackLiveData", "JetPackDataBinding", "JetPackPaging", "JetPackRoom"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}