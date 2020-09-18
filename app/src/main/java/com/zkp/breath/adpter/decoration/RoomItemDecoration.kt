package com.zkp.breath.adpter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.jessyan.autosize.utils.AutoSizeUtils

class RoomItemDecoration : BaseItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        // 总数
        val itemCount = state.itemCount
        // 当前item的角标
        val current = parent.getChildLayoutPosition(view)

        if (current == 0) {
            outRect.top = AutoSizeUtils.dp2px(parent.context, 15f)
        } else {
            if (current == itemCount - 1) {
                outRect.top = AutoSizeUtils.dp2px(parent.context, 12f)
                outRect.bottom = AutoSizeUtils.dp2px(parent.context, 15f)
            } else {
                outRect.top = AutoSizeUtils.dp2px(parent.context, 12f)
            }
        }
    }
}