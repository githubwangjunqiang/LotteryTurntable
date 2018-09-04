package com.xiaoqiang.lottery.lottery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2018/9/3.
 */
public class luckView extends SurfaceView implements SurfaceHolder.Callback, Runnable, IluckView {
    /**
     * 图片的百分比 相对于半径
     */
    private float PAGESTIZE;
    /**
     * 控件默认大小
     */
    private int SIZE = 100;
    /**
     * 是否绘制
     */
    private boolean isRunning;
    /**
     * 绘制开始角度 默认0开始
     */
    private float startAngle;
    private SurfaceHolder mSurfaceHolder;
    /**
     * 全局画笔
     */
    private Paint mPaint;
    /**
     * 文字path
     */
    private Path mPath;
    /**
     * 奖品集合数组
     */
    private volatile List<LuckData> mLuckDatas;
    /**
     * 外边框半径
     */
    private float mRanius;
    /**
     * 奖品绘制区域的举行
     */
    private RectF mRectF;
    /**
     * 速度  旋转速度
     */
    private volatile double mSpeed = 0;
    /**
     * 中将下标
     */
    private LuckData mLuckData = null;
    /**
     * 中将起始角度
     */
    private float startIndexAngle, stopIndexAngle;
    /**
     * 外边框颜色
     */
    private int mBorderColor;
    /**
     * 中将是否高亮
     */
    private boolean mIsLuckUnColor;
    /**
     * 中将奖品 高亮颜色
     */
    private int mLuckDataColor;
    /**
     * 中奖奖品模块是否大小发生突出变化
     */
    private boolean mLuckisUnSize;
    /**
     * 字体大小
     */
    private float mTextSize;
    /**
     * 旋转速度
     */
    private int mRotationSpeed;
    /**
     * 背景颜色
     */
    private int mViewBackColor;
    /**
     * 旋转停止的监听器
     */
    private ICallback mICallback;

    public luckView(Context context) {
        this(context, null);
    }

    public luckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public luckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        setFocusableInTouchMode(true);
        setFocusable(true);
        setKeepScreenOn(true);
        getAttr(attrs);
        PAGESTIZE = 4;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                getContext().getResources().getDisplayMetrics());
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        SIZE = dp2px(getContext(), 100);
        mLuckDatas = new ArrayList<>();
        mRectF = new RectF();
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.luckView);
        mIsLuckUnColor = array.getBoolean(R.styleable.luckView_lv_isUnColor, false);
        mLuckisUnSize = array.getBoolean(R.styleable.luckView_lv_isUnSize, false);
        mBorderColor = array.getColor(R.styleable.luckView_lv_bordercolor, Color.RED);
        mLuckDataColor = array.getColor(R.styleable.luckView_lv_luckDataColor, Color.RED);
        mRotationSpeed = array.getInt(R.styleable.luckView_lv_rotationSpeed, 50);
        mViewBackColor = array.getColor(R.styleable.luckView_lv_viewbackcolor, Color.WHITE);
        array.recycle();
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = Math.max(getPaddingLeft(), dp2px(getContext(), 6)) * 2;
        mRanius = (w - padding) / 2;
        mRectF.set(padding, padding, w - padding,
                h - padding);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        startThread();
    }

    private void startThread() {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            drawLuck();
            long endTime = System.currentTimeMillis();
            if (endTime - startTime < 50) {
                try {
                    Thread.sleep(50 - (endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绘制
     */
    private void drawLuck() {
        if (mLuckDatas == null || mLuckDatas.isEmpty()) {
            return;
        }
        Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas();
            if (canvas != null) {
                drawLuckViews(canvas);
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 真正绘制
     *
     * @param canvas
     */
    private void drawLuckViews(Canvas canvas) {
        float start = startAngle;
        float sweep = 360 / mLuckDatas.size();
        canvas.drawColor(mViewBackColor);
        mPaint.setColor(mBorderColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRanius, mPaint);
        for (int i = 0; i < mLuckDatas.size(); i++) {
            LuckData luckData = mLuckDatas.get(i);
            //绘制扇形
            mPaint.setColor(luckData.getBackColor());
            if (stopIndexAngle != 0 && mSpeed == 0 && mLuckData.getId() == luckData.getId()) {
                if (mIsLuckUnColor) {
                    mPaint.setColor(mLuckDataColor);
                }
                if (mLuckisUnSize) {
                    RectF rectF = new RectF();
                    rectF.set(0, 0, getHeight(), getWidth());
                    canvas.drawArc(rectF, start, sweep, true, mPaint);
                } else {
                    canvas.drawArc(mRectF, start, sweep, true, mPaint);
                }
            } else {
                canvas.drawArc(mRectF, start, sweep, true, mPaint);
            }
            //绘制文字
            mPath.reset();
            mPath.addArc(mRectF, start, sweep);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float v = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.leading) + Math.abs(fontMetrics.descent);

            mPaint.setColor(luckData.getTextColor());
            if (luckData.getTextSize() > 0) {
                mPaint.setTextSize(luckData.getTextSize());
            } else {
                mPaint.setTextSize(mTextSize);
            }
            canvas.drawTextOnPath(luckData.getName(), mPath, 0, v, mPaint);

            //绘制图片
            Bitmap bitmap = luckData.getBitmap();
            if (bitmap != null) {
                int imgWidth = (int) (mRanius / PAGESTIZE);
                if (luckData.getBitmepWidth() > 0) {
                    imgWidth = (int) (mRanius / luckData.getBitmepWidth());
                }
                float angle = (float) ((start + 360 / mLuckDatas.size() / 2) * Math.PI / 180);
                int imgx = (int) (getWidth() / 2 + mRanius / 2 * Math.cos(angle));
                int imgy = (int) (getWidth() / 2 + mRanius / 2 * Math.sin(angle));
                RectF rectF = new RectF(imgx - imgWidth / 2, imgy - imgWidth / 2, imgx + imgWidth / 2, imgy + imgWidth / 2);
                canvas.drawBitmap(bitmap, null, rectF, null);
            }


            start += sweep;
        }


        startAngle += mSpeed;
        if (startAngle >= 360) {
            startAngle = startAngle - 360;
        }
        float angle = startAngle + 210;
        if (angle >= 360) {
            angle = angle - 360;
        }
        if (mLuckData != null) {
            mSpeed--;
            if (mSpeed == 20) {
                if (startIndexAngle > stopIndexAngle) {
                    if (angle > startIndexAngle || angle < stopIndexAngle) {
                        //可以做自己的操作
                    } else {
                        mSpeed++;
                    }
                } else {
                    if (angle > startIndexAngle && angle < stopIndexAngle) {
                        //可以做自己的操作
                    } else {
                        mSpeed++;
                    }
                }
            }
        }
        if (mSpeed <= 0) {
            mSpeed = 0;
            stop();
        }
    }

    private void stop() {
        luckStop(mLuckData);
    }

    /**
     * 结束 旋转
     *
     * @param data
     */
    private void luckStop(LuckData data) {
        if (mICallback != null) {
            mICallback.luckEnd(data);
        }
    }

    @Override
    public void addLuckData(LuckData data) {
        if (data != null && mLuckDatas != null) {
            mLuckDatas.add(data);
        }
    }

    @Override
    public void addLuckDatas(List<LuckData> datas) {
        if (datas != null && mLuckDatas != null) {
            mLuckDatas.addAll(datas);
        }
    }

    @Override
    public void startLuck() {
        startLuck(mRotationSpeed);
    }

    @Override
    public void startLuck(double mSpeed) {
        mLuckData = null;
        this.mSpeed = mSpeed;
        luckStart(mSpeed);
    }

    @Override
    public void stopLuck(LuckData data) {
        stopLuck(data, null);
    }

    @Override
    public void stopLuck(LuckData data, ICallback callback) {
        if (callback != null) {
            mICallback = callback;
        }
        int index = 0;
        for (int i = 0; i < mLuckDatas.size(); i++) {
            if (mLuckDatas.get(i).getId() == data.getId()) {
                index = i;
                float luckangle = 360 / mLuckDatas.size();
                startIndexAngle = 270 - (index + 1) * luckangle;
                if (startIndexAngle < 0) {
                    startIndexAngle += 360;
                }
                stopIndexAngle = startIndexAngle + luckangle;
                if (stopIndexAngle > 360) {
                    stopIndexAngle -= 360;
                }
                break;
            }
        }
//        float luckangle = 360 / mLuckDatas.size();
//
//        float startIndexAngle = 270 - (index + 1) * luckangle;
//        float stopIndexAngle = startIndexAngle + luckangle;
//        float targetStart = 10 * 360 + startIndexAngle;
//        float targetStop = 10 * 360 + stopIndexAngle;
//
//        float v1 = (float) ((-1 + Math.sqrt(1 + 8 * targetStart)) / 2) + luckangle/10;
//        float v2 = (float) ((-1 + Math.sqrt(1 + 8 * targetStop)) / 2) - luckangle/10;
//        mSpeed = (v1 + Math.random() * (v2 - v1));
//        startAngle = 0;
        mLuckData = data;

    }

    @Override
    public boolean isTruning() {
        return mSpeed != 0;
    }

    @Override
    public void setBorderColor(int color) {
        mBorderColor = color;
    }

    @Override
    public void setLuckisUnColor(boolean isUnColor) {
        mIsLuckUnColor = isUnColor;
    }

    @Override
    public void setLuckDataColor(int color) {
        mLuckDataColor = color;
    }

    @Override
    public void setLuckisUnSize(boolean isUnSize) {
        mLuckisUnSize = isUnSize;
    }

    @Override
    public void setViewBackColor(int color) {
        mViewBackColor = color;
    }

    @Override
    public void setLuckCallback(ICallback callback) {
        if (callback != null) {
            mICallback = callback;
        }
    }

    /**
     * 开始 旋转
     *
     * @param mSpeed
     */
    private void luckStart(double mSpeed) {
        if (mICallback != null) {
            mICallback.luckStart(mSpeed);
        }
    }


}
