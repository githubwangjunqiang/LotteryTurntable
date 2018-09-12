package com.xiaoqiang.lottery.lotteryturntable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xiaoqiang.lottery.lottery.ICallback;
import com.xiaoqiang.lottery.lottery.IluckView;
import com.xiaoqiang.lottery.lottery.LuckData;
import com.xiaoqiang.lottery.lottery.loading.LoadingView;

public class MainActivity extends AppCompatActivity {

    private LoadingView mLoadingView;
    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView = findViewById(R.id.loadingview);
        mLoadingView.setColorRGB(255, 1, 1);
        mAnimatorSet = new AnimatorSet();

        startAnimations();
        luck();
    }

    private void startAnimations() {
        ObjectAnimator mObjectAnimator = ObjectAnimator.ofInt(mLoadingView, "index", 0, mLoadingView.getListSize() * 10);
        mObjectAnimator.setDuration(7000);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        mAnimatorSet.playTogether(mObjectAnimator);
        mAnimatorSet.start();
    }

    private void luck() {
        //初始化 转盘
        IluckView view = findViewById(R.id.luckview);
        //给转盘填充奖品模块 实体类 LuckData
        for (int i = 0; i < 6; i++) {
            //创建奖品实体类 本库依赖此实体类
            LuckData data = new LuckData();
            //设置奖品背景色
            data.setBackColor(R.color.colorAccent);
            //设置奖品图片 可以不用填
//            data.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            //设置图片显示的宽度 百分比 例如控件半径的5分之一
//            data.setBitmepWidth(2);
            //设置奖品名字
            data.setName("我是奖品");
            //设置奖品文字颜色
            data.setTextColor(Color.WHITE);
            //设置奖品文字大小 单位px
            data.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
            //设置奖品唯一id 用来制定奖品位置
            data.setId(4);
            //填入奖品
            view.addLuckData(data);
        }


        /**
         * 也可以单独添加回调
         */
        view.setLuckCallback(new ICallback() {
            @Override
            public void luckStart(double mSpeed) {
                //TODO 旋转开始
            }

            @Override
            public void luckEnd(LuckData data) {
                //TODO 旋转结束 可以提示 用户得将了 实体类-》data
            }
        });
//        view.stopLuck(data, new ICallback() {
////            @Override
////            public void luckStart(double mSpeed) {
////                //TODO 旋转开始
////            }
////
////            @Override
////            public void luckEnd(LuckData data) {
////                //TODO 旋转结束 可以提示 用户得将了 实体类-》data
////            }
////        });
    }

    @Override
    protected void onDestroy() {
        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            mAnimatorSet.cancel();
        }
        mAnimatorSet = null;
        super.onDestroy();
    }

    public void doClicl(View view) {
        view.setSelected(!view.isSelected());
    }
}
