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
import kotlin.concurrent.thread

/**
 * https://mp.weixin.qq.com/s/ppJ-pRDifyPpQoPIyYbxww
 * https://mp.weixin.qq.com/s/RArUF06w0utNGaDTnUxGhw
 *
 * 1. 禁用断点 (并非删除断点)，可以右键点击断点并从弹框中取消选中 Enabled 选框。您也可以通过按住 Alt (在 Mac 上
 * 是 Option) 并点击（鼠标左右键都可以）断点，从而更快速地禁用断点。
 * 2. ctrl + F8  快捷键设置/取消断点
 * 3. 断点列表对断点分组
 */
class DebugActivity : BaseActivity() {

    private lateinit var binding: ActivityDebugBinding
    private lateinit var debugAdapter: DebugAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        thread {
            // 挂起线程断点： Suspend默认是选中all，一旦断点所有线程都会被挂起，那么界面的渲染就看不到。
            // 举例来说，您可能想要验证某个后台线程阻塞时，应用的其他功能是否能够正常工作，或者您希望了解在执行一个
            // 后台任务时，UI 能不能够持续进行渲染。那么这时候只要选中thread即可。
            Log.i("sdsds", "onCreate: ")
        }

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

                // 如果只是想快速验证断点是否触发并且不在乎其中的细节信息，可以使用 "Breakpoint hit" 信息
                // 来记录断点的触发事件，在Debug的Console中能看到打印信息，信息指向断点位置。
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
                // 异常断点：java exception breakpoint选中后开启断点即可，在发生异常的地方会自动挂起。
                throw Exception("异常断点")
            }

        }
    }

}