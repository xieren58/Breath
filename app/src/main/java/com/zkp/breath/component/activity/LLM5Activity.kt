package com.zkp.breath.component.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityLlM5Binding


class LLM5Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityLlM5Binding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLlM5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())

        varargSetClickListener(binding.tvClipboard)
        binding.et1.requestFocus()
    }

    private fun m5() {
        var substring = ""
        try {
            val et1 = binding.et1.text.toString()
            val et2 = binding.et2.text.toString()
            val encryptMD5ToString = EncryptUtils.encryptMD5ToString(et1.plus(et2))
            val length = encryptMD5ToString.length
            val i = length / 2
            substring = if ((et1.toLong() % 2L) == 0L) {
                encryptMD5ToString.substring(i, i + 4)
            } else {
                encryptMD5ToString.substring(i, i - 4)
            }
        } catch (e: Exception) {
        }

        binding.tvClipboard.setText(substring)
        ClipboardUtils.copyText(substring)
        ToastUtils.showShort("md成功")
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvClipboard == v) {
            m5()
            return
        }
    }

}