package com.zkp.breath.component.activity.debugs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.adpter.DebugAdapter
import com.zkp.breath.adpter.decoration.RoomItemDecoration
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityDebugBinding


class DebugActivity : BaseActivity() {

    private lateinit var binding: ActivityDebugBinding
    private lateinit var debugAdapter: DebugAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.tvDebug.setOnClickListener(onClickListener)
        binding.tvExceptionDebug.setOnClickListener(onClickListener)

        val mutableListOf = mutableListOf<String>()
        for (i in 0..50) {
            // 条件断点
            mutableListOf.add("第${i}个item")
        }
        binding.rcv.layoutManager = LinearLayoutManager(binding.rcv.context)
        debugAdapter = DebugAdapter(mutableListOf)
        binding.rcv.adapter = debugAdapter
        binding.rcv.addItemDecoration(RoomItemDecoration())

        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 依赖断点：依赖其余的断点触发后才会触发，触发后还需要依赖的断点才能再次触发。
                Log.i("onScrolled", "dx:${dx},dy:${dy}")
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // 日志断点：取消选中Suspend，选Evaluate and log选项，并在其中添加上Log语句，点击‘Done’按钮。
                // 和调用Log日志工具类起到一样的作用，只是可以避免在类中到处打Log，影响类的可读性，日志断点严格来说并不是断点调试，
                // 它不会在你打断点的地方停下来，它只是让你在需要的地方输出日志而已。
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            if (v == null) {
                return
            }

            if (v == binding.tvDebug) {
                ToastUtils.showShort("普通断点")
                return
            }

            if (v == binding.tvExceptionDebug) {
                ToastUtils.showShort("异常断点")
            }

        }
    }

}