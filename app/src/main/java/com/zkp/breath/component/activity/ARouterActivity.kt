package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterBinding


/**
 * https://www.jianshu.com/p/6021f3f61fa6
 */

@Route(path = "/activity/ARouterActivity")
class ARouterActivity : BaseActivity() {

    lateinit var binding: ActivityAruoterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        ToastUtils.showShort("路由器activity")
    }

}