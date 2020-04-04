package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zkp.breath.R
import com.zkp.breath.adpter.CoordinatorAdapter
import com.zkp.breath.bean.ArouterParamsBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterBinding


/**
 * https://www.jianshu.com/p/6021f3f61fa6
 */

@Route(path = "/activity/ARouterActivity")
class ARouterActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {

    lateinit var binding: ActivityAruoterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val recyclerView = binding.rcv
        val arrayListOf = arrayListOf("跳转test1", "跳转test2", "跳转test3", "5", "6", "7", "8", "9", "10")
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView.setHasFixedSize(true)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val coordinatorAdapter = CoordinatorAdapter(arrayListOf)
        coordinatorAdapter.setOnItemClickListener(this@ARouterActivity)
        recyclerView.adapter = coordinatorAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val data = adapter?.data
        val value: String = data?.get(position) as String
        if (TextUtils.isEmpty(value)) return
        when (value) {
            "跳转test1" -> {
                ARouter.getInstance()
                        .build(TEST1_AROUTER_ACTIVITY_PATH)
                        .navigation()
            }
            "跳转test2" -> {
                val arouterParamsBean = ArouterParamsBean()
                arouterParamsBean.age = 27
                arouterParamsBean.name = "zkp"

                val arrayListOf = arrayListOf(11, 22, 33, 44)

                ARouter.getInstance()
                        .build(TEST2_AROUTER_ACTIVITY_PATH)
                        .withString("key1", "value1")
                        .withInt("key2", 22)
                        .withSerializable("key3", arouterParamsBean)
                        .withIntegerArrayList("key4", arrayListOf)
                        .navigation()
            }
            "跳转test3" -> {
                ARouter.getInstance()
                        .build(TEST3_AROUTER_ACTIVITY_PATH)
                        .withTransition(R.anim.activity_in_anim, R.anim.activity_out_anim)
                        .navigation(this);
            }
        }
    }

}