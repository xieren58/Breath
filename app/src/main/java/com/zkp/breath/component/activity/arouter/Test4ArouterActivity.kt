package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.arouter.ActivityRouterPath
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterTestBinding

/**
 * 动画跳转例子
 */
@Route(path = ActivityRouterPath.TEST4_AROUTER_ACTIVITY_PATH)
class Test4ArouterActivity : BaseActivity() {

    lateinit var binding: ActivityAruoterTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        ToastUtils.showShort("Test4ArouterActivity")
    }

}