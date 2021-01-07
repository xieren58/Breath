package com.zkp.breath.component.activity.sdkVersion

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnWindowFocusChangeListener
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.Utils
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityClipboardBinding


class ClipboardQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityClipboardBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClipboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())

        varargSetClickListener(binding.tvClipboard)
        ClipboardUtils.addChangedListener(clipBoardListener)
    }

    /**
     * android10及其以上，解决无法直接访问剪切板的解决方案。
     *
     * 监听焦点变化，当获取到焦点时访问剪切板。
     */
    private fun focusClipBoard() {
        // 监听焦点变化
        window.decorView.viewTreeObserver.addOnWindowFocusChangeListener(object : OnWindowFocusChangeListener {
            override fun onWindowFocusChanged(hasFocus: Boolean) {
                // 获取到焦点
                if (hasFocus) {
                    ClipboardUtils.copyText("获取焦点访问剪切板")
                    val text = ClipboardUtils.getText()
                    Log.i("剪切板内容", "text: $text")
                    window.decorView.viewTreeObserver.removeOnWindowFocusChangeListener(this)
                }
            }
        })
    }

    /**
     * Android Q及其以上，只有当应用处于可交互情况（默认输入法本身就可交互）才能访问剪切板和监听剪切板变化，
     * 即便在onResume也无法直接访问剪切板，这么做的好处是避免了一些应用后台疯狂监听响应剪切板的内容，疯狂弹窗。
     *
     * 测试结果：
     * 1. 当Android版本在10以下，可以直接访问剪切板
     * 2. 当Android版本在10以上，即便在onCreate和onResume都无法直接访问，在响应点击等交互情况下才能访问，如果；
     *    当应用切换到后台无法访问剪切板，剪切板监听不回调。
     *
     * 解决：只能针对前台且Android版本10及其以上
     * 1. 可以在onResume和onCreate进行延迟访问，使应用获取焦点。
     * 2. 在onCreate中，通过addOnWindowFocusChangeListener去监听当且界面的焦点变化，不过需要记得的是，
     *    读取完剪切板需要remove监听。
     */
    private fun clipboard() {
        handler.postDelayed({
            ClipboardUtils.copyText("测试剪贴板")
            val text = ClipboardUtils.getText()
            Log.i("获取剪贴板内容", "text: $text")
        }, 3000L)
    }

    /**
     * 回调多次，最好保存最后一次访问得到的剪切板内容，每次都比较一下是否有变化。
     */
    private val clipBoardListener = object : ClipboardManager.OnPrimaryClipChangedListener {
        override fun onPrimaryClipChanged() {
            val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val primaryClip = cm.primaryClip
            val description = primaryClip?.description
            val label = description?.label
            val mimeTypeCount = description?.mimeTypeCount
            mimeTypeCount?.let {
                for (index in 0 until it) {
                    val mimeType = description.getMimeType(index)
                    Log.i("监听剪切板", "mimeType: $mimeType")
                }
            }

            val itemCount = primaryClip?.itemCount
            itemCount?.let {
                for (index in 0 until it) {
                    val item = primaryClip.getItemAt(index)
                    val text = item.text
                    Log.i("监听剪切板", "text: $text")
                }
            }
        }
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvClipboard == v) {
            clipboard()
            return
        }
    }

    override fun onDestroy() {
        ClipboardUtils.removeChangedListener(clipBoardListener)
        super.onDestroy()
    }

}