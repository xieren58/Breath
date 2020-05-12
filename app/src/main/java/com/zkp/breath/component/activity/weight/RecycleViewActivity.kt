package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.adpter.GridAdapter
import com.zkp.breath.adpter.LoadMoreAdapter
import com.zkp.breath.adpter.decoration.GridItemDecoration
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRecycleViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecycleViewActivity : BaseActivity() {

    private lateinit var binding: ActivityRecycleViewBinding
    private var reqCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleViewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

//        adpter1()
        adpter2()
    }

    fun adpter1() {
        val arrayList = ArrayList<String>()
        for (i in 0..100) {
            arrayList.add(i.toString())
        }

        val rcv = binding.rcv
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        rcv.layoutManager = staggeredGridLayoutManager
        val gridAdapter = GridAdapter(arrayList)
        rcv.adapter = gridAdapter
        rcv.addItemDecoration(GridItemDecoration())
    }


    fun adpter2() {
        val arrayList = ArrayList<String>()
        for (i in 0..10) {
            arrayList.add(i.toString())
        }

        val rcv = binding.rcv
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val staggeredGridLayoutManager = LinearLayoutManager(this)
        rcv.layoutManager = staggeredGridLayoutManager
        val gridAdapter = LoadMoreAdapter(arrayList)
        gridAdapter.setOnItemClickListener(onItemClickListener)
        rcv.adapter = gridAdapter
        rcv.addItemDecoration(GridItemDecoration())

        // 获取模块
        val loadMoreModule = gridAdapter.loadMoreModule
        // 打开或关闭加载更多功能（默认为true）
        loadMoreModule.isEnableLoadMore = true
        // 所有数据加载完成后，是否允许点击（默认为false）
        loadMoreModule.enableLoadMoreEndClick = true
        // 是否自定加载下一页（默认为true）
//        loadMoreModule.isAutoLoadMore = false
        // 预加载的位置（默认为1）。  一般用于分页加载，一般设置请求数据量的一半即可。
//        loadMoreModule.preLoadNumber = 5

        loadMoreModule.setOnLoadMoreListener {

            GlobalScope.launch(Dispatchers.Main) {

                ToastUtils.showShort("进入load_more回调")

                // 模拟load数据的过程
                withContext(Dispatchers.IO) {
                    Thread.sleep(3000)
                    Log.i(TAG, "launch_IO1: ${Thread.currentThread().name}")
                }

                when (reqCount) {
                    // 当前这次数据加载错误，调用此方法
                    0 -> {
                        loadMoreModule.loadMoreFail()
                        reqCount++
                    }
                    1 -> {
                        val newData = ArrayList<String>()
                        for (i in 0..10) {
                            newData.add("我是新load的数据".plus(i.toString()))
                        }
                        // 添加新load的模拟数据
                        gridAdapter.addData(newData)
                        // 当前这次数据加载完毕，调用此方法
                        loadMoreModule.loadMoreComplete()

                        reqCount++
                    }

                    2 -> {
                        // 所有数据加载完成，调用此方法
                        // 需要重置"加载完成"状态时，请调用 setNewData()
                        loadMoreModule.loadMoreEnd()
                    }

                    // 上面的预加载功能打开才放开这段代码进行测试，上面的when的其余判断条件也要注释
//                    else -> {
//                        val newData = ArrayList<String>()
//                        for (i in 0..10) {
//                            newData.add("我是新load的数据".plus(i.toString()))
//                        }
//                        // 添加新load的模拟数据
//                        gridAdapter.addData(newData)
//                        // 当前这次数据加载完毕，调用此方法
//                        loadMoreModule.loadMoreComplete()
//
//                        reqCount++
//                    }
                }
            }

        }
    }

    private val onItemClickListener = OnItemClickListener { adapter, view, position ->

        // 加载过程中不允许点击
        if (adapter.loadMoreModule.isLoading) {
            return@OnItemClickListener
        }

        val data = adapter.data
        ToastUtils.showShort(data[position] as String)
    }

}