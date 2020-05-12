package com.zkp.breath.component.activity

import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zkp.breath.R
import com.zkp.breath.adpter.CoordinatorAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityABinding
import com.zkp.breath.databinding.ActivityAruoterBinding
import com.zkp.breath.databinding.ActivityMaterialDialogBinding


class MaterialDialogsActivity : BaseActivity() {

    lateinit var binding: ActivityMaterialDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDialogBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val recyclerView = binding.rcv
        val arrayListOf = arrayListOf("1", "2", "3", "4", "5", "6", "7")
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView.setHasFixedSize(true)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val coordinatorAdapter = CoordinatorAdapter(arrayListOf)
        coordinatorAdapter.setOnItemClickListener(onItemClickListener)
        recyclerView.adapter = coordinatorAdapter
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        when (position) {
            0 -> {
                MaterialDialog(this).show {
                    title(text = "我是标题")
                    message(text = "我是内容")
                }
            }

            1 -> {
                MaterialDialog(this).show {
                    positiveButton()
                    negativeButton()
                }
            }
        }
    }

}
