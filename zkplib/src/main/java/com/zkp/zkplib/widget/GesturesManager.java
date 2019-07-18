package com.zkp.zkplib.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GesturesManager extends GestureDetector.SimpleOnGestureListener {

    private GestureDetector mDetector;
    private float mVelocityX;
    private float mVelocityY;
    private int mMinMoveInterceptSize;

    private float mDownX;
    private float mDownY;

    private GesturesListener mGesturesListener;
    private BasicDataListener mDataListener;

    private boolean mDetectHorizontalGestures;
    private boolean mDetectVerticalGestures;

    public void setCanDetectGesturesDirection(boolean horizontal, boolean vertical) {
        mDetectHorizontalGestures = horizontal;
        mDetectVerticalGestures = vertical;
    }

    public void setGesturesListener(GesturesListener listener) {
        this.mGesturesListener = listener;
    }

    public void setBaseDataListener(BasicDataListener listener) {
        this.mDataListener = listener;
    }

    public interface BasicDataListener {

        int getLeftToRightMinSize();

        int getRightToLeftMinSize();

        int getTopToBottomMinSize();

        int getBottomToTopMinSize();
    }

    public interface GesturesListener {

        void onMove(float distance, int moveType);

        void onUp(int upType, int moveType);
    }

    private int mMoveType = MoveType.TYPE_NONE;

    public @interface MoveType {

        int TYPE_NONE = 0;
        int TYPE_LEFT_TO_RIGHT = 1;
        int TYPE_RIGHT_TO_LEFT = 2;
        int TYPE_TOP_TO_BOTTOM = 3;
        int TYPE_BOTTOM_TO_TOP = 4;
    }

    public @interface UpType {

        int TYPE_STATIC_LIFT = 0;
        int TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE = 1; // 与 move 类型 一致的
        int TYPE_FLING_OPPOSITE_OF_MOVE_TYPE = 2; // 与 move 类型 相反的
    }

    public GesturesManager(Context context) {
        mDetector = new GestureDetector(context, this);
        mMinMoveInterceptSize = 50;

        setCanDetectGesturesDirection(true, true);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mVelocityX = velocityX;
        mVelocityY = velocityY;
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = ev.getX();
                mDownY = ev.getY();
                mVelocityX = 0;
                mVelocityY = 0;
                mMoveType = MoveType.TYPE_NONE;
                break;
            }
            default:
                break;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        boolean out = false;

        if (mDetector != null) {
            mDetector.onTouchEvent(ev);
        }

        switch (ev.getAction() & ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = ev.getX();
                mDownY = ev.getY();
                mVelocityX = 0;
                mVelocityY = 0;
                mMoveType = MoveType.TYPE_NONE;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float diff = 0;
                if (mMoveType == MoveType.TYPE_NONE) {

                    // x轴和y轴的当前点和起始点的间距
                    float diffX = Math.abs(ev.getX() - mDownX);
                    float diffY = Math.abs(ev.getY() - mDownY);

                    // 其中一个方向的滑动间距大于最小值
                    if (diffX > mMinMoveInterceptSize || diffY > mMinMoveInterceptSize) {
                        if (diffX > diffY) {
                            // x轴方向滑动间距大于y轴,判断当前为x方向的移动
                            if (!mDetectHorizontalGestures) {
                                // 不允许滑动则跳出
                                break;
                            }

                            // android屏幕坐标系x轴从左向右逐渐变大(与数学坐标系x轴一致)
                            if ((ev.getX() - mDownX) > 0) {
                                // 从左向右滑动
                                mMoveType = MoveType.TYPE_LEFT_TO_RIGHT;
                            } else if ((ev.getX() - mDownX) < 0) {
                                // 从右向左滑动
                                mMoveType = MoveType.TYPE_RIGHT_TO_LEFT;
                            }
                        } else {
                            // 当前为y方向的移动
                            if (!mDetectVerticalGestures) {
                                // 不允许滑动则跳出
                                break;
                            }

                            // android屏幕坐标系y轴从上向下逐渐变大(与数学坐标系y轴相反)
                            if ((ev.getY() - mDownY) > 0) {
                                // 从上到下滑动
                                mMoveType = MoveType.TYPE_TOP_TO_BOTTOM;
                            } else if ((ev.getY() - mDownY) < 0) {
                                // 从下到上滑动
                                mMoveType = MoveType.TYPE_BOTTOM_TO_TOP;
                            }
                        }
                    }
                }

                switch (mMoveType) {
                    case MoveType.TYPE_RIGHT_TO_LEFT:
                    case MoveType.TYPE_LEFT_TO_RIGHT: {
                        // x轴滑动的间距
                        diff = ev.getX() - mDownX;
                        break;
                    }

                    case MoveType.TYPE_TOP_TO_BOTTOM:
                    case MoveType.TYPE_BOTTOM_TO_TOP: {
                        // y轴滑动的间距
                        diff = ev.getY() - mDownY;
                        break;
                    }
                    default:
                        break;
                }

                if (mGesturesListener != null) {
                    // 传递类型以及间距
                    mGesturesListener.onMove(diff, mMoveType);
                }
                break;
            }

            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                float diffX = ev.getX() - mDownX;
                float diffY = ev.getY() - mDownY;

                int minSize = 0;
                int type = UpType.TYPE_STATIC_LIFT;
                switch (mMoveType) {
                    case MoveType.TYPE_LEFT_TO_RIGHT: {
                        if (mDataListener != null) {
                            minSize = mDataListener.getLeftToRightMinSize();
                        }

                        if (mVelocityX > 0) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (mVelocityX < 0) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (diffX > 0 && diffX >= minSize) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (diffX < 0 && Math.abs(diffX) >= minSize) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        }
                        break;
                    }

                    case MoveType.TYPE_RIGHT_TO_LEFT: {
                        if (mDataListener != null) {
                            minSize = mDataListener.getRightToLeftMinSize();
                        }

                        if (mVelocityX > 0) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (mVelocityX < 0) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (diffX < 0 && Math.abs(diffX) >= minSize) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (diffX > 0 && diffX >= minSize) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        }
                        break;
                    }

                    case MoveType.TYPE_TOP_TO_BOTTOM: {
                        if (mDataListener != null) {
                            minSize = mDataListener.getTopToBottomMinSize();
                        }

                        if (mVelocityY > 0) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (mVelocityY < 0) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (diffY < 0 && Math.abs(diffY) >= minSize) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (diffY > 0 && diffY >= minSize) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        }
                        break;
                    }

                    case MoveType.TYPE_BOTTOM_TO_TOP: {
                        if (mDataListener != null) {
                            minSize = mDataListener.getBottomToTopMinSize();
                        }

                        if (mVelocityY < 0) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        } else if (mVelocityY > 0) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (diffY > 0 && diffY >= minSize) {
                            type = UpType.TYPE_FLING_OPPOSITE_OF_MOVE_TYPE;
                        } else if (diffY < 0 && Math.abs(diffY) >= minSize) {
                            type = UpType.TYPE_FLING_CONSISTENT_WITH_MOVE_TYPE;
                        }
                        break;
                    }
                    default:
                        break;
                }

                if (mGesturesListener != null) {
                    mGesturesListener.onUp(type, mMoveType);
                }
                break;
            }
            default:
                break;
        }
        return out;
    }
}