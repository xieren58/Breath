package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterTestBinding

class TestWebviewActivity : BaseActivity() {

    lateinit var binding: ActivityAruoterTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        ToastUtils.showShort("TestWebviewActivity")

        initView()
    }

    private fun initView() {
        val web = WebView(this)
        web.loadUrl("file:///android_asset/arouter.html")
        val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        binding.clt.addView(web, layoutParams)
    }

}