package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.GridAdapter
import com.zkp.breath.adpter.LoadMoreAdapter
import com.zkp.breath.adpter.decoration.GridItemDecoration
import com.zkp.breath.adpter.diff.DemoDiffCallBack
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRecycleViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * https://zhuanlan.zhihu.com/p/26079803
 *
 * 提高局部刷新效率的方法：
 * 1.notifyItemChanged的参数二payload，这里的局部刷新指的是某个item的某些子view。
 * payload的作用是刷新某个item里面的某些子view，真正做到局部刷新的概念，该参数是一个Object类型的List,如果自身继承
 * RecycleView的Adpter，那么再重写带有payloads参数的onBindViewHolder()的方法，然后在内部判断payloads是否为空，
 * 如果为空那么就在内部调用两个参数的onBindViewHolder()，否则就执行自身的刷新逻辑：
 *
 *   // 示例代码如下
 *   public void onBindViewHolder(@NonNull VH holder, int position,@NonNull List<Object> payloads) {
 *      if(payloads.isEmpty()){
 *          onBindViewHolder(holder, position);
 *      }else{
 *          // 刷新逻辑...
 *      }
 *   }
 *
 *   2. diffutils 一般用在重置数据源，存在个别数据没有发生改变使用diffutils会跳过那些没发生改变数据的item的刷新
 *   ，而只会去刷新发生数据改变的item，从而达到局部刷新的作用（这里的局部刷新是值某些item），当数据太大的时候建议放在线程中执行。
 *
 *
 *  注意：目前BaseRecyclerViewAdapterHelper框架提供的上拉加载更多和下拉刷新都没有回弹效果，可以使用SmartRefreshLayout
 *  框架实现。
 *
 *
 */
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
        /**
         * 取消动画
         * 3.0.2版本，开启了loadmore功能，也开启DiffUtils功能，使用setDiffNewData方法会自动滑倒底部，需要加上
         * 下面的方法才能避免，已经提交github的issues。（#3177）
         */
        rcv.itemAnimator = null
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val staggeredGridLayoutManager = LinearLayoutManager(this)
        rcv.layoutManager = staggeredGridLayoutManager
        val gridAdapter = LoadMoreAdapter(arrayList)
        gridAdapter.setOnItemClickListener(onItemClickListener)

        // 动画
        gridAdapter.animationEnable = true
        gridAdapter.isAnimationFirstOnly = false
        gridAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft)

        rcv.adapter = gridAdapter
        rcv.addItemDecoration(GridItemDecoration())

        // 设置空视图(recycleview设置adpter后再调用)
        gridAdapter.isUseEmpty = true
        gridAdapter.setEmptyView(R.layout.view_empty)

        // 启用diff工具，配合setDiffNewData（）方法使用
        gridAdapter.setDiffCallback(DemoDiffCallBack())

        // 获取模块
        val loadMoreModule = gridAdapter.loadMoreModule
        // 打开或关闭加载更多功能（默认为true）
        loadMoreModule.isEnableLoadMore = true
        // 所有数据加载完成后，是否允许点击（默认为false）
        loadMoreModule.enableLoadMoreEndClick = true
        // 是否自定加载下一页（默认为true）
        loadMoreModule.isAutoLoadMore = true
        // 预加载的位置（默认为1）。  一般用于分页加载，一般设置请求数据量的一半即可。
        loadMoreModule.preLoadNumber = 5

        loadMoreModule.setOnLoadMoreListener {

            GlobalScope.launch(Dispatchers.Main) {

                ToastUtils.showShort("进入load_more回调")

                // 模拟load数据的过程
                withContext(Dispatchers.IO) {
                    Thread.sleep(3000)
                    Log.i(ACTIVITY_TAG, "launch_IO1: ${Thread.currentThread().name}")
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

        val loadMoreAdapter = adapter as? LoadMoreAdapter

        if (position in 1..2) {
            if (position == 1) {
                // 模拟使用diffutils匹配相同数据不会进行刷新
                val arrayList = ArrayList<String>()
                for (i in 0..10) {
                    arrayList.add(i.toString())
                }
                loadMoreAdapter?.setDiffNewData(arrayList)
            } else {
                // 模拟使用diffutils匹配不相等数据进行刷新的逻辑
                val arrayList = ArrayList<String>()
                for (i in 0..10) {
                    arrayList.add(i.toString().plus("x"))
                }
                loadMoreAdapter?.setDiffNewData(arrayList)

            }
        } else if (position in 5..10) {
            // 模拟某个item的某些子view刷新的逻辑
            adapter.notifyItemChanged(position, 1)
        } else {
            val data = adapter.data
            ToastUtils.showShort(data[position] as String)
        }
    }

}