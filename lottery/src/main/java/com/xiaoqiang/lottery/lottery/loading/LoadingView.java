package com.xiaoqiang.lottery.lottery.loading;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${王俊强} on 2018/9/12.
 */
public class LoadingView extends View {
    private static final int SIZE = 100;
    private Paint mPaint;
    private int[] mColorRGB = new int[]{255, 255, 255};
    private int listSize = 8;
    private int mWidth, mHeight;
    private float mRadius;
    private int mIndex = 0;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public int[] getColorRGB() {
        return mColorRGB;
    }

    public void setColorRGB(int r, int g, int b) {
        mColorRGB[0] = r;
        mColorRGB[1] = g;
        mColorRGB[2] = b;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = w / 2 / 3 / 2;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.save();
        int deg = 360 / listSize;
        canvas.rotate(mIndex * deg, mWidth / 2, mHeight / 2);
        for (int i = 0; i < listSize; i++) {
            mPaint.setARGB(Math.min(255, (i + 1) * (255 / listSize)), mColorRGB[0], mColorRGB[1], mColorRGB[2]);
            canvas.drawCircle(mWidth / 2, mRadius * 2, mRadius * (1.2F * (i + 1) / listSize) + mRadius * 0.3F, mPaint);
            canvas.rotate(deg, mWidth / 2, mHeight / 2);
        }

        canvas.restore();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        if (index > listSize - 1) {
            index = index % listSize;
        }
        mIndex = index;
        postInvalidate();
    }
}
