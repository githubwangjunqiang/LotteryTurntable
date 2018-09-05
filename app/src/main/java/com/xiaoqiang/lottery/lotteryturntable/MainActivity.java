package com.xiaoqiang.lottery.lotteryturntable;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xiaoqiang.lottery.lottery.ICallback;
import com.xiaoqiang.lottery.lottery.IluckView;
import com.xiaoqiang.lottery.lottery.LuckData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化 转盘
        IluckView view = findViewById(R.id.luckview);
        //给转盘填充奖品模块 实体类 LuckData
        for (int i = 0; i < 6; i++) {
            //创建奖品实体类 本库依赖此实体类
            LuckData data = new LuckData();
            //设置奖品背景色
            data.setBackColor(R.color.colorAccent);
            //设置奖品图片 可以不用填
            data.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            //设置图片显示的宽度 百分比 例如控件半径的5分之一
            data.setBitmepWidth(2);
            //设置奖品名字
            data.setName("我是奖品");
            //设置奖品文字颜色
            data.setTextColor(R.color.colorAccent);
            //设置奖品文字大小 单位px
            data.setTextSize(12);
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

    public void doClicl(View view) {
        view.setSelected(!view.isSelected());
    }
}
