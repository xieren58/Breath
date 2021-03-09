package com.zkp.breath.component.activity.weight.qmui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.ClickUtils
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder
import com.qmuiteam.qmui.widget.dialog.QMUITipDialogView
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityQmuiBottomSheetBinding
import com.zkp.breath.databinding.ActivityQmuiDialogBinding


class QMUIBottomSheetActivity : ClickBaseActivity() {

    private lateinit var binding: ActivityQmuiBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQmuiBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        varargSetClickListener(binding.rBtn)
    }


    override fun onDebouncingClick(v: View) {
        if (v == binding.rBtn) {
        }
    }

    private fun simple() {
        QMUIBottomSheet.BottomGridSheetBuilder(this)
                .build()
                .show()
    }


}