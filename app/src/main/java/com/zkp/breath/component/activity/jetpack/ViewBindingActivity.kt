package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityViewbindingBinding

/**
 * https://mp.weixin.qq.com/s/BSdzKSOiAWG08epvXN5q2w
 *
 * ViewBinding优势：
 * 1.防止类型转换异常，修改了控件类型马上进行错误提示。
 * 2.防止空异常。（一般的获取操作一定是非空）
 */
class ViewBindingActivity : BaseActivity() {

    private lateinit var binding: ActivityViewbindingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewbindingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        // 调用include内部控件的方式：binding + include布局id + 控件id
        binding.appbar.tvTitle.text = "ViewBindingActivity"
    }

}