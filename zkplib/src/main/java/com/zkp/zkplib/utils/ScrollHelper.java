package com.zkp.zkplib.utils;

import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

/**
 * Created b Zwp on 2019/4/24.
 */
public class ScrollHelper {

    /**
     * 点击滚动指定的position，布局管理器必须是{@link LinearLayoutManager}
     *
     * @param recyclerView item的父类
     * @param position     指定的position
     * @param isToCenter   true则居中(方向垂直或水平都适用)，false则居左或居上
     *                     {@link LinearLayoutManager#HORIZONTAL}
     *                     {@link LinearLayoutManager#VERTICAL}
     * @param hasAnimator  是否有动画效果
     *                     {@link RecyclerView#scrollBy(int, int)}
     *                     {@link RecyclerView#smoothScrollBy(int, int)}
     */
    public static void clickScrollToPosition(RecyclerView recyclerView, int position,
                                             boolean isToCenter, boolean hasAnimator) {

        LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean b = layoutManager instanceof LinearLayoutManager;
        if (!b) {
            return;
        }

        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        int orientation = manager.getOrientation();

        View view = recyclerView.getChildAt(position - firstItem);
        if (view == null) {
            return;
        }

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            int left = view.getLeft();
            int offX;
            if (isToCenter) { // 居中位移
                offX = left - recyclerView.getWidth() / 2 + view.getWidth() / 2;
            } else { // 居左位移
                offX = left;
            }
            if (hasAnimator) {
                recyclerView.smoothScrollBy(offX, 0);
            } else {
                recyclerView.scrollBy(offX, 0);
            }
        } else {
            int top = view.getTop();
            int offY;
            if (isToCenter) { // 居中位移
                offY = top - recyclerView.getHeight() / 2 + view.getHeight() / 2;
            } else { // 居左位移
                offY = top;
            }
            if (hasAnimator) {
                recyclerView.smoothScrollBy(0, offY);
            } else {
                recyclerView.scrollBy(0, offY);
            }
        }
    }

    /**
     * 跳转到指定position并居中
     * @param recyclerView
     * @param position
     */
    public static void specialPositionScroll(final RecyclerView recyclerView, final int position) {
        final LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean b = layoutManager instanceof LinearLayoutManager;
        if (!b) {
            return;
        }

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        final int orientation = manager.getOrientation();
        View view = layoutManager.findViewByPosition(position);

        if (view == null) {
            manager.scrollToPosition(position);
        }

        Looper.myQueue().addIdleHandler(new IdleHandler() {

            @Override
            public boolean queueIdle() {
                View view = manager.findViewByPosition(position);
                if (view == null) {
                    return false;
                }

                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    float center = recyclerView.getWidth() / 2f;
                    float viewCenter = view.getX() + view.getWidth() / 2f;
                    recyclerView.scrollBy((int) (viewCenter - center), 0);
                } else {
                    float center = recyclerView.getHeight() / 2f;
                    float viewCenter = view.getY() + view.getHeight() / 2f;
                    recyclerView.scrollBy(0, (int) (viewCenter - center));
                }
                return false;
            }
        });
    }


    /**
     * 跳转到指定position并居顶或局左
     * @param recyclerView
     * @param position
     */
    public static void specialPositionScrollTopOrLeft(final RecyclerView recyclerView, final int position) {
        if (recyclerView == null) {
            return;
        }

        final LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean b = layoutManager instanceof LinearLayoutManager;
        if (!b) {
            return;
        }

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        final int orientation = manager.getOrientation();
        View view = layoutManager.findViewByPosition(position);

        if (view == null) {
            manager.scrollToPosition(position);
        }

        // 如果使用下面的方法则指定的position可能不会置定且点击暂停刷新的时候会发生跳转到顶部
        recyclerView.post(() -> {
            View view1 = manager.findViewByPosition(position);
            if (view1 == null) {
                return;
            }

            if (orientation == LinearLayoutManager.HORIZONTAL) {
                int left = view1.getLeft();
                recyclerView.scrollBy(left, 0);
            } else {
                int top = view1.getTop();
                recyclerView.scrollBy(0, top);
            }
        });

//        Looper.myQueue().addIdleHandler(new IdleHandler() {
//
//            @Override
//            public boolean queueIdle() {
//
//                return false;
//            }
//        });
    }

}
