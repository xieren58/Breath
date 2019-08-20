package com.zkp.zkplib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created b Zwp on 2019/5/27.
 */
public class CustomProgress extends View {

    private static final String TAG = CustomProgress.class.getName();

    private PathMeasure mPathMeasure;
    private Builder mBuilder;
    private Paint mBgPaint;
    private Paint mPaint;
    private Path mPath;
    private Path mDst;
    private RectF mBorderRectF;
    private RectF mRectF;
    private int mRadiusXy;
    private float mProgress;
    private float mLength;

    public CustomProgress(Context context, Builder builder) {
        super(context);
        mBuilder = builder;

        mRectF = new RectF();
        mBorderRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int fgColor = builder.getFgColor();
        mPaint.setColor(fgColor == -1 ? Color.WHITE : fgColor);
        mPaint.setStyle(Style.STROKE);
        // 画笔的笔触的形状
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // 两条边交界处的形状
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setStrokeWidth(builder.getStrokeWidth());

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        int bgColor = builder.getBgColor();
        mBgPaint.setColor(bgColor == -1 ? 0x4DFFFFFF : bgColor);
        mBgPaint.setStyle(Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeJoin(Join.ROUND);
        mBgPaint.setStrokeWidth(builder.getStrokeWidth());

        mRadiusXy = builder.getRadiusXy();
        mPathMeasure = new PathMeasure();
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: （" + w + "," + h + ")");

        int centerX = (int) (w / 2f);
        int centerY = (int) (h / 2f);

        int strokeWidth = mBuilder.getStrokeWidth();

        mRectF.left = strokeWidth / 2f;
        mRectF.top = strokeWidth / 2f;
        mRectF.right = w - (strokeWidth / 2f);
        mRectF.bottom = h - (strokeWidth / 2f);

        mBorderRectF.left = 0;
        mBorderRectF.top = 0;
        mBorderRectF.right = w;
        mBorderRectF.bottom = h;

        if (mBuilder.isRectFlag()) {
            rectDirectionRule(w, h, centerX, centerY, strokeWidth);
        } else {
            roundDirectionRule(w, h, centerX, centerY, strokeWidth);
        }

        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
        mDst = new Path();
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    private void rectDirectionRule(int w, int h, int centerX, int centerY, int strokeWidth) {

        // 画笔从中间向两端扩展stroke
        float halfStrokeWidth = strokeWidth / 2f;

        switch (mBuilder.getDirection()) {
            case Direction.TOP_CENTER_CW:
                mPath.moveTo(centerX, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;
            case Direction.TOP_CENTER_CCW:
                mPath.moveTo(centerX, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CW:
                mPath.moveTo(halfStrokeWidth, centerY);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CCW:
                mPath.moveTo(halfStrokeWidth, centerY);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                break;

            case Direction.RIGHT_CENTER_CW:
                mPath.moveTo(w - halfStrokeWidth, centerY);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;

            case Direction.RIGHT_CENTER_CCW:
                mPath.moveTo(w - halfStrokeWidth, centerY);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CW:
                mPath.moveTo(centerX, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CCW:
                mPath.moveTo(centerX, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.LEFT_TOP_CW:
                mPath.moveTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();

                // 相当于上面的逻辑，矩形定死起始点，其他的起始点不能使用这种方法，即便在此之前用moveto也不生效
//                mPath.addRect(mRectF, Path.Direction.CW);
                break;

            case Direction.LEFT_TOP_CCW:
                mPath.moveTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.close();

                // 相当于上面的逻辑，矩形定死起始点，其他的起始点不能使用这种方法，即便在此之前用moveto也不生效
//                mPath.addRect(mRectF, Path.Direction.CCW);
                break;

            case Direction.LEFT_BOTTOM_CW:
                mPath.moveTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.LEFT_BOTTOM_CCW:
                mPath.moveTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;

            case Direction.RIGHT_TOP_CW:
                mPath.moveTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;

            case Direction.RIGHT_TOP_CCW:
                mPath.moveTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            case Direction.RIGHT_BOTTOM_CW:
                mPath.moveTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.close();
                break;

            case Direction.RIGHT_BOTTOM_CCW:
                mPath.moveTo(w - halfStrokeWidth, h - (halfStrokeWidth));
                mPath.lineTo(w - halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                mPath.lineTo(halfStrokeWidth, h - (halfStrokeWidth));
                mPath.close();
                break;

            default:
                break;
        }
    }

    private void roundDirectionRule(int w, int h, int centerX, int centerY, int strokeWidth) {

        float left = mRectF.left;
        float right = mRectF.right;
        float top = mRectF.top;
        float bottom = mRectF.bottom;

        switch (mBuilder.getDirection()) {
            case Direction.TOP_CENTER_CW:
                mPath.moveTo(centerX, top);
                mPath.lineTo(right - mRadiusXy, top);
                mPath.quadTo(right, top, right, top + mRadiusXy);
                mPath.lineTo(right, bottom - mRadiusXy);
                mPath.quadTo(right, bottom, right - mRadiusXy, bottom);
                mPath.lineTo(left + mRadiusXy, bottom);
                mPath.quadTo(left, bottom, left, bottom - mRadiusXy);
                mPath.lineTo(left, top + mRadiusXy);
                mPath.quadTo(left, top, left + mRadiusXy, top);
                mPath.close();
                break;
            case Direction.TOP_CENTER_CCW:
                mPath.moveTo(centerX, top);
                mPath.lineTo(mRadiusXy, top);
                mPath.quadTo(left, top, left, mRadiusXy - (strokeWidth / 2f));
                mPath.lineTo(left, h - mRadiusXy);
                mPath.quadTo(left, bottom, mRadiusXy, bottom);
                mPath.lineTo(w - mRadiusXy, bottom);
                mPath.quadTo(right, bottom, right, h - mRadiusXy);
                mPath.lineTo(right, mRadiusXy);
                mPath.quadTo(right, top, w - mRadiusXy, top);
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CW:
                mPath.moveTo(left, centerY);
                mPath.lineTo(left, top + mRadiusXy);
                mPath.quadTo(left, top, left + mRadiusXy, top);
                mPath.lineTo(right - mRadiusXy, top);
                mPath.quadTo(right, top, right, top + mRadiusXy);
                mPath.lineTo(right, bottom - mRadiusXy);
                mPath.quadTo(right, bottom, right - mRadiusXy, bottom);
                mPath.lineTo(left + mRadiusXy, bottom);
                mPath.quadTo(left, bottom, left, bottom - mRadiusXy);
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CCW:
                mPath.moveTo(left, centerY);
                mPath.lineTo(left, h - mRadiusXy);
                mPath.quadTo(left, bottom, mRadiusXy, bottom);
                mPath.lineTo(w - mRadiusXy, bottom);
                mPath.quadTo(right, bottom, right, h - mRadiusXy);
                mPath.lineTo(right, mRadiusXy);
                mPath.quadTo(right, top, w - mRadiusXy, top);
                mPath.lineTo(mRadiusXy, top);
                mPath.quadTo(left, top, left, mRadiusXy - (strokeWidth / 2f));
                mPath.close();
                break;

            case Direction.RIGHT_CENTER_CW:
                mPath.moveTo(right, centerY);
                mPath.lineTo(right, bottom - mRadiusXy);
                mPath.quadTo(right, bottom, right - mRadiusXy, bottom);
                mPath.lineTo(left + mRadiusXy, bottom);
                mPath.quadTo(left, bottom, left, bottom - mRadiusXy);
                mPath.lineTo(left, top + mRadiusXy);
                mPath.quadTo(left, top, left + mRadiusXy, top);
                mPath.lineTo(right - mRadiusXy, top);
                mPath.quadTo(right, top, right, top + mRadiusXy);
                mPath.close();
                break;

            case Direction.RIGHT_CENTER_CCW:
                mPath.moveTo(right, centerY);
                mPath.lineTo(right, mRadiusXy);
                mPath.quadTo(right, top, w - mRadiusXy, top);
                mPath.lineTo(mRadiusXy, top);
                mPath.quadTo(left, top, left, mRadiusXy - (strokeWidth / 2f));
                mPath.lineTo(left, h - mRadiusXy);
                mPath.quadTo(left, bottom, mRadiusXy, bottom);
                mPath.lineTo(w - mRadiusXy, bottom);
                mPath.quadTo(right, bottom, right, h - mRadiusXy);
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CW:
                mPath.moveTo(centerX, bottom);
                mPath.lineTo(left + mRadiusXy, bottom);
                mPath.quadTo(left, bottom, left, bottom - mRadiusXy);
                mPath.lineTo(left, top + mRadiusXy);
                mPath.quadTo(left, top, left + mRadiusXy, top);
                mPath.lineTo(right - mRadiusXy, top);
                mPath.quadTo(right, top, right, top + mRadiusXy);
                mPath.lineTo(right, bottom - mRadiusXy);
                mPath.quadTo(right, bottom, right - mRadiusXy, bottom);
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CCW:
                mPath.moveTo(centerX, bottom);
                mPath.lineTo(w - mRadiusXy, bottom);
                mPath.quadTo(right, bottom, right, h - mRadiusXy);
                mPath.lineTo(right, mRadiusXy);
                mPath.quadTo(right, top, w - mRadiusXy, top);
                mPath.lineTo(mRadiusXy, top);
                mPath.quadTo(left, top, left, mRadiusXy - (strokeWidth / 2f));
                mPath.lineTo(left, h - mRadiusXy);
                mPath.quadTo(left, bottom, mRadiusXy, bottom);
                mPath.close();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (mBuilder.isRectFlag()) {
            canvas.drawRect(mRectF, mBgPaint);
        } else {
            canvas.drawRoundRect(mRectF, mRadiusXy, mRadiusXy, mBgPaint);
        }
        mDst.reset();
        float stopD = mProgress / 100f * mLength;
        mPathMeasure.getSegment(0, stopD, mDst, true);
        canvas.drawPath(mDst, mPaint);
        canvas.restore();
    }

    public static class Builder {

        /** 背景色 */
        int bgColor = -1;
        /** 前景色 */
        int fgColor = -1;
        /** 边框宽度 */
        int strokeWidth;
        /** 半径 */
        int radiusXy;
        /** 是否为矩形 */
        boolean rectFlag;
        /**
         * 方向
         *
         * @see Direction
         */
        @Direction
        int direction;

        public int getBgColor() {
            return bgColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public int getFgColor() {
            return fgColor;
        }

        public void setFgColor(int fgColor) {
            this.fgColor = fgColor;
        }

        public int getStrokeWidth() {
            return strokeWidth;
        }

        public void setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
        }

        public int getRadiusXy() {
            return radiusXy;
        }

        public void setRadiusXy(int radiusXy) {
            this.radiusXy = radiusXy;
        }

        public boolean isRectFlag() {
            return rectFlag;
        }

        public void setRectFlag(boolean rectFlag) {
            this.rectFlag = rectFlag;
        }

        @Direction
        public int getDirection() {
            return direction;
        }

        public void setDirection(@Direction int direction) {
            this.direction = direction;
        }
    }

    @IntDef({Direction.TOP_CENTER_CW, Direction.TOP_CENTER_CCW,
            Direction.LEFT_CENTER_CW, Direction.LEFT_CENTER_CCW,
            Direction.RIGHT_CENTER_CW, Direction.RIGHT_CENTER_CCW,
            Direction.BOTTOM_CENTER_CW, Direction.BOTTOM_CENTER_CCW,
            Direction.LEFT_TOP_CW, Direction.LEFT_TOP_CCW,
            Direction.RIGHT_TOP_CW, Direction.RIGHT_TOP_CCW,
            Direction.LEFT_BOTTOM_CW, Direction.LEFT_BOTTOM_CCW,
            Direction.RIGHT_BOTTOM_CW, Direction.RIGHT_BOTTOM_CCW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {

        int TOP_CENTER_CW = 0;
        int TOP_CENTER_CCW = 1;
        int LEFT_CENTER_CW = 2;
        int LEFT_CENTER_CCW = 3;
        int RIGHT_CENTER_CW = 4;
        int RIGHT_CENTER_CCW = 5;
        int BOTTOM_CENTER_CW = 6;
        int BOTTOM_CENTER_CCW = 7;

        // 只有矩形才有下面的选项才有效果
        int LEFT_TOP_CW = 8;
        int LEFT_TOP_CCW = 9;
        int RIGHT_TOP_CW = 10;
        int RIGHT_TOP_CCW = 11;
        int LEFT_BOTTOM_CW = 12;
        int LEFT_BOTTOM_CCW = 13;
        int RIGHT_BOTTOM_CW = 14;
        int RIGHT_BOTTOM_CCW = 15;
    }
}

