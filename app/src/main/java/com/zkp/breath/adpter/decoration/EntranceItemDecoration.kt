package com.zkp.breath.adpter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jessyan.autosize.utils.AutoSizeUtils

class EntranceItemDecoration : BaseItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val layoutManager = parent.layoutManager as GridLayoutManager? ?: return
        // 总数
        val itemCount = state.itemCount
        // 列数
        val spanCount = layoutManager.spanCount
        // 当前item的角标
        val current = parent.getChildLayoutPosition(view)

        if (current % 2 == 0) {
            outRect.left = AutoSizeUtils.dp2px(parent.context, 10f)
            outRect.right = AutoSizeUtils.dp2px(parent.context, 4f)
        } else {
            outRect.left = AutoSizeUtils.dp2px(parent.context, 4f)
            outRect.right = AutoSizeUtils.dp2px(parent.context, 10f)
        }


        if (isFirstRaw(parent, current, spanCount)) {
            outRect.top = AutoSizeUtils.dp2px(parent.context, 15f)
        } else {
            if (isLastRow(parent, current, spanCount, itemCount)) {
                outRect.top = AutoSizeUtils.dp2px(parent.context, 12f)
                outRect.bottom = AutoSizeUtils.dp2px(parent.context, 15f)
            } else {
                outRect.top = AutoSizeUtils.dp2px(parent.context, 12f)
            }
        }

    }
}