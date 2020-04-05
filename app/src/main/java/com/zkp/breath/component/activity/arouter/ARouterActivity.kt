package com.zkp.breath.component.activity.arouter

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zkp.breath.R
import com.zkp.breath.adpter.CoordinatorAdapter
import com.zkp.breath.arouter.ActivityRouterPath
import com.zkp.breath.arouter.IProviderServicePath
import com.zkp.breath.arouter.entity.ArouterParamsBean
import com.zkp.breath.arouter.interfaces.HelloService
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterBinding


/**
 * https://www.jianshu.com/p/6021f3f61fa6
 */

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
        val arrayListOf = arrayListOf("跳转test1", "跳转test2", "跳转test3", "跳转test4", "跳转test5", "暴露服务接口")
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
                        .build(ActivityRouterPath.TEST1_AROUTER_ACTIVITY_PATH)
                        .navigation()
            }
            "跳转test2" -> {
                val arouterParamsBean = ArouterParamsBean()
                arouterParamsBean.age = 27
                arouterParamsBean.name = "zkp"

                val arrayListOf = arrayListOf(11, 22, 33, 44)

                ARouter.getInstance()
                        .build(ActivityRouterPath.TEST2_AROUTER_ACTIVITY_PATH)
                        .withString("key1", "value1")
                        .withInt("key2", 22)
                        .withSerializable("key3", arouterParamsBean)
                        .withIntegerArrayList("key4", arrayListOf)
                        .navigation()
            }
            "跳转test3" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    // Activity.overridePendingTransition
                    ARouter.getInstance()
                            .build(ActivityRouterPath.TEST3_AROUTER_ACTIVITY_PATH)
                            .withTransition(R.anim.activity_in_anim, R.anim.activity_out_anim)
                            .navigation(this);
                } else {
                    // ActivityOptionsCompat
                    val compat = ActivityOptionsCompat.makeCustomAnimation(
                            this, R.anim.activity_in_anim, R.anim.activity_out_anim)
                    ARouter.getInstance()
                            .build(ActivityRouterPath.TEST3_AROUTER_ACTIVITY_PATH)
                            .withOptionsCompat(compat)
                            .navigation()
                }
            }
            "跳转test4" -> {
                ARouter.getInstance()
                        .build(ActivityRouterPath.TEST4_AROUTER_ACTIVITY_PATH)
                        .navigation(this, object : NavigationCallback {
                            override fun onLost(postcard: Postcard?) {
                                // 找不到path指定的目标
                                Log.i("NavigationCallback", "onLost: ");
                            }

                            override fun onFound(postcard: Postcard?) {
                                // 当发现有可跳转的目标回调, 跳转的path是存在的
                                Log.i("NavigationCallback", "onFound: ");
                            }

                            override fun onInterrupt(postcard: Postcard?) {
                                // 当拦截器InterceptorCallback执行onInterrupt（）方法后回调
                                Log.i("NavigationCallback", "onInterrupt: ");
                            }

                            override fun onArrival(postcard: Postcard?) {
                                // 成功跳转后回调
                                Log.i("NavigationCallback", "onArrival: ");
                            }
                        })
            }
            "跳转test5" -> {
                // 实现跳转并获取返回结果
                val postcard: Postcard = ARouter.getInstance().build(ActivityRouterPath.TEST5_AROUTER_ACTIVITY_PATH)
                LogisticsCenter.completion(postcard)
                val destination = postcard.destination
                val intent = Intent(this, destination)
                startActivityForResult(intent, 22)
            }
            "暴露服务接口" -> {
                val helloService = ARouter.getInstance()
                        .build(IProviderServicePath.HELLO_PROVIDER_SERVICE_PATH)
                        .navigation() as HelloService
                val result = helloService.sayHello("my name is zkp")
                ToastUtils.showShort(result)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == 33) {
            ToastUtils.showShort("使用Arouter实现跳转并获取返回结果")
        }
    }

}