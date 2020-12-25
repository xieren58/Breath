package com.zkp.breath.component.activity.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.blankj.utilcode.util.ClickUtils

/**
 * 拥有点击事件的BaseActivity
 */
abstract class ClickBaseActivity(@LayoutRes contentLayoutId: Int = 0) : BaseActivity(contentLayoutId),
        View.OnClickListener {

    /**
     * 批量setOnClickListener
     */
    protected open fun varargSetClickListener(vararg v: View?) {
        for (view in v) {
            view?.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        // 转接实现
        onClickListener?.onClick(v)
    }

    private var onClickListener: ClickUtils.OnDebouncingClickListener? =
            object : ClickUtils.OnDebouncingClickListener() {
                override fun onDebouncingClick(v: View?) {
                    v?.let {
                        this@ClickBaseActivity.onDebouncingClick(it)
                    }
                }
            }

    /**
     * 实现自定义点击逻辑
     */
    abstract fun onDebouncingClick(@NonNull v: View)

    override fun onDestroy() {
        onClickListener = null
        super.onDestroy()
    }

}