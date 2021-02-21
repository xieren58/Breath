package com.zkp.breath.component.activity.weight.qmui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityQmuiDialogBinding


class QMUIDialogActivity : BaseActivity() {

    private lateinit var binding: ActivityQmuiDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQmuiDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rBtn.setOnClickListener(onClickListener)
    }

    private var onClickListener: ClickUtils.OnDebouncingClickListener? =
            object : ClickUtils.OnDebouncingClickListener() {
                override fun onDebouncingClick(v: View?) {
                    when (v) {
                        binding.rBtn -> {
                            builtInDialog()
                            return
                        }
                    }
                }
            }

    /**
     * 内置样式示例，不支持修改，基本不满足日常需求
     */
    private fun builtInDialog() {
        QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("读取中")
                .create()
                .show()
    }

    private fun customDialog() {
//        QMUITipDialog.CustomBuilder(this)
//                .setContent()
//                .create()
//                .show()
    }

}