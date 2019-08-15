package com.zkp.zkplib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class RoundRectProgress extends View {

    private static final String TAG = RoundRectProgress.class.getName();

    private final Builder mBuilder;
    private Paint mBgPaint;
    private Paint mPaint;
    private RectF mRectF;
    private Path mPath;
    private int mRadiusXy;
    private PathMeasure mPathMeasure;
    private float mProgress;
    private float mLength;
    private Path mDst;

    public RoundRectProgress(Context context, Builder builder) {
        super(context);
        mBuilder = builder;

        mRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int fgColor = builder.getFgColor();
        mPaint.setColor(fgColor == -1 ? Color.WHITE : fgColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // +1是覆盖掉背景色，防止四边遗留背景色线
        mPaint.setStrokeWidth(builder.getStrokeWidth() + 1f);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        int bgColor = builder.getBgColor();
        mBgPaint.setColor(bgColor == -1 ? 0x4DFFFFFF : bgColor);
        mBgPaint.setStyle(Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeJoin(Paint.Join.ROUND);
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

        if (mBuilder.isRectFlag()) {
            directionRule(w, h, centerX, centerY, strokeWidth);
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

    /**
     * 方向规则，可重写该方法自定义或者扩充
     */
    protected void directionRule(int w, int h, int centerX, int centerY, int strokeWidth) {

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

            default:
                break;
        }
    }

    /**
     * 方向规则，可重写该方法自定义或者扩充
     */
    protected void roundDirectionRule(int w, int h, int centerX, int centerY, int strokeWidth) {

        float halfStrokeWidth = strokeWidth / 2f;

        switch (mBuilder.getDirection()) {
            case Direction.TOP_CENTER_CW:

                mPath.moveTo(centerX, strokeWidth / 2f);
                mPath.lineTo(w - mRadiusXy * 2f, strokeWidth / 2f);
                mPath.arcTo(new RectF(w - mRadiusXy * 2f, strokeWidth / 2f,
                        w - (strokeWidth / 2f), mRadiusXy * 2f), -90, 90);
                mPath.lineTo(w - (strokeWidth / 2f), h - (mRadiusXy * 2f));

                mPath.arcTo(new RectF(w - mRadiusXy * 2f, h - mRadiusXy * 2f,
                        w - (strokeWidth / 2f), h - strokeWidth / 2f), 0, 90);

                mPath.lineTo(mRadiusXy * 2f, h - strokeWidth / 2f);


                mPath.arcTo(new RectF(strokeWidth / 2f, h - mRadiusXy * 2f,
                        mRadiusXy * 2f, h - strokeWidth / 2f), 90, 180);





                mPath.close();
                break;
            case Direction.TOP_CENTER_CCW:
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CW:
                mPath.close();
                break;

            case Direction.LEFT_CENTER_CCW:
                mPath.lineTo(halfStrokeWidth, halfStrokeWidth);
                break;

            case Direction.RIGHT_CENTER_CW:
                mPath.close();
                break;

            case Direction.RIGHT_CENTER_CCW:
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CW:
                mPath.close();
                break;

            case Direction.BOTTOM_CENTER_CCW:
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
        canvas.drawRoundRect(mRectF, mRadiusXy, mRadiusXy, mBgPaint);
        mDst.reset();
        float stopD = mProgress / 100f * mLength;
        mPathMeasure.getSegment(0, stopD, mDst, true);
        canvas.drawPath(mDst, mPaint);
        canvas.restore();
    }

    public static class Builder {

        int bgColor = -1;
        int fgColor = -1;
        int strokeWidth;
        int radiusXy;
        boolean rectFlag;
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
            Direction.BOTTOM_CENTER_CW, Direction.BOTTOM_CENTER_CCW})
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
    }
}

