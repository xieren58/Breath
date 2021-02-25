package com.zkp.breath.component.activity.weight.qmui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.ClickUtils
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder
import com.qmuiteam.qmui.widget.dialog.QMUITipDialogView
import com.zkp.breath.R
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
//                            builtInDialog()
                            customDialog()
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

    /**
     * 自定义示例
     *
     * 问题1：自定义的view其实就是作为子view被添加到QMUITipDialogView中，而QMUITipDialogView有默认
     * 样式，而 QMUITipDialog.CustomBuilder没有相关方法提供取消QMUITipDialogView默认样式的设置，所以只能
     * 自己获取到QMUITipDialogView然后进行修改。
     *
     * 问题2：QMUITipDialog的布局默认有大小限制（坑）
     */
    private fun customDialog() {
        val create = QMUITipDialog.CustomBuilder(this)
                .setContent(R.layout.dialog_qmui_custom)
                .create()

        val decorView = create.window?.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)
        if (decorView is QMUITipDialogView) {
            decorView.background = null
            decorView.setWidthLimit(com.blankj.utilcode.util.ScreenUtils.getAppScreenWidth())
            decorView.setHeightLimit(com.blankj.utilcode.util.ScreenUtils.getAppScreenHeight())
        }

        create.setCancelable(true)  // 是否点击返回键取消弹框
        create.setCanceledOnTouchOutside(true)  // 是否点击非内容区取消弹框
        create.show()
    }
}