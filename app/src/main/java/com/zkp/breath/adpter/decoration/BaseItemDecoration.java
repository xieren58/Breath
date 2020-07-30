package com.zkp.breath.adpter.decoration;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {

    public boolean isFirstRaw(@NotNull RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                return pos < spanCount;
            } else {
                return pos % spanCount == 0;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                return pos == 0;
            } else {
                //每一个都是第一行，也是最后一行
                return true;
            }
        }
        return false;
    }


    public boolean isFirstColumn(@NotNull RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                return pos % spanCount == 0;
            } else {
                return pos < spanCount;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //每一个都是第一列，也是最后一列
                return true;
            } else {
                return pos == 0;
            }
        }
        return false;
    }


    public boolean isLastRow(@NotNull RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            // 如果是最后一行
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == position / spanCount + 1;

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行
                return position >= childCount;
            } else {// StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行
                return (position + 1) % spanCount == 0;
            }
        }
        return false;
    }


    public boolean isLastColumn(@NotNull RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列
            return (position + 1) % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列
                return (position + 1) % spanCount == 0;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列
                return position >= childCount;
            }
        }
        return false;
    }

    //  获取列数
    private int getSpanCount(@NotNull RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }
}
