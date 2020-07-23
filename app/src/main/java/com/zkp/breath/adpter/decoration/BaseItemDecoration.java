package com.zkp.breath.adpter.decoration;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {

    public boolean isFirstRaw(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                if (pos < spanCount) {
                    return true;
                }
            } else {
                if (pos % spanCount == 0) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if (pos == 0) {
                    return true;
                }
            } else {
                //每一个都是第一行，也是最后一行
                return true;
            }
        }
        return false;
    }


    public boolean isFirstColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = (layoutManager instanceof GridLayoutManager) ? ((GridLayoutManager) layoutManager).getOrientation() : ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0) {
                    return true;
                }
            } else {
                if (pos < spanCount) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                //每一个都是第一列，也是最后一列
                return true;
            } else {
                if (pos == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isLastRaw(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            // 如果是最后一行，则不需要绘制底部
            return position >= childCount;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                return position >= childCount;
            } else {// StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行，则不需要绘制底部
                return (position + 1) % spanCount == 0;
            }
        }
        return false;
    }


    public boolean isLastColum(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边
            return (position + 1) % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                return (position + 1) % spanCount == 0;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                return position >= childCount;
            }
        }
        return false;
    }
}
