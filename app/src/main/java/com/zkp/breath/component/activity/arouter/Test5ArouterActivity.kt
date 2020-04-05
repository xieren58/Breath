package com.zkp.breath.component.activity.arouter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.arouter.ActivityRouterPath
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterTestBinding
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 实现跳转并获取返回结果的例子
 */
@Route(path = ActivityRouterPath.TEST5_AROUTER_ACTIVITY_PATH)
class Test5ArouterActivity : BaseActivity() {

    lateinit var binding: ActivityAruoterTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        ToastUtils.showShort("Test5ArouterActivity")

        initView()
    }

    private fun initView() {
        val textView = TextView(this)
        textView.setText("setResult()")
        textView.setTextSize(AutoSizeUtils.sp2px(this, 16f).toFloat())
        textView.setTextColor(Color.WHITE)
        val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        binding.clt.addView(textView, layoutParams)

        textView.setOnClickListener({
            setResult(33, intent)
            finish()
        })
    }

}