package com.xiaoqiang.lottery.lottery;

/**
 * Created by ${王俊强} on 2018/9/4.
 */
public interface ICallback {
    /**
     * 开始旋转监听器
     *
     * @param mSpeed 返回速度
     */
    void luckStart(double mSpeed);

    /**
     * 停止监听器
     *
     * @param data
     */
    void luckEnd(LuckData data);
}
