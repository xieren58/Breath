package com.zkp.breath.component.activity.jetpack

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
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import com.zkp.breath.jetpack.navigation.NavigationActivity
import kotlinx.android.synthetic.main.activity_entrance.*

class JetPackActivity : BaseActivity(R.layout.activity_entrance) {

    private val listOf = mutableListOf(
            "StartUp", "ViewModel", "ViewBinding", "Lifecycle",
            "LiveData", "DataBinding", "Paging", "Room", "DataStore", "ResultsApi", "Navigation"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.setBackgroundColor(ColorUtils.getColor(R.color.colorFF8BC34A))
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
            "StartUp" -> {
                ActivityUtils.startActivity(StartupActivity::class.java)
                return@OnItemClickListener
            }
            "ViewModel" -> {
                ActivityUtils.startActivity(ViewModelActivity::class.java)
                return@OnItemClickListener
            }
            "ViewBinding" -> {
                ActivityUtils.startActivity(ViewBindingActivity::class.java)
                return@OnItemClickListener
            }
            "Lifecycle" -> {
                ActivityUtils.startActivity(LifecycleActivity::class.java)
                return@OnItemClickListener
            }
            "LiveData" -> {
                ActivityUtils.startActivity(LiveDataActivity::class.java)
                return@OnItemClickListener
            }
            "DataBinding" -> {
                ActivityUtils.startActivity(DataBindingActivity::class.java)
                return@OnItemClickListener
            }
            "Paging" -> {
                ActivityUtils.startActivity(PagingActivity::class.java)
                return@OnItemClickListener
            }
            "Room" -> {
                ActivityUtils.startActivity(RoomActivity::class.java)
                return@OnItemClickListener
            }
            "DataStore" -> {
                ActivityUtils.startActivity(DataStoreActivity::class.java)
                return@OnItemClickListener
            }
            "ResultsApi" -> {
                ActivityUtils.startActivity(ResultsApiActivity::class.java)
                return@OnItemClickListener
            }
            "Navigation" -> {
                ActivityUtils.startActivity(NavigationActivity::class.java)
                return@OnItemClickListener
            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

}