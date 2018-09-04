package com.xiaoqiang.lottery.lottery;

import android.graphics.Bitmap;

/**
 * Created by ${王俊强} on 2018/9/3.
 */
public class LuckData {
    private String name;
    private int textColor;
    private int textSize;
    private int backColor;
    private Bitmap mBitmap;
    private int id = -1;
    public LuckData() {
    }
    public LuckData(String name, int textColor,int textSize ,int backColor, Bitmap bitmap, int id) {
        this.name = name;
        this.textColor = textColor;
        this.backColor = backColor;
        this.textSize = textSize;
        mBitmap = bitmap;
        this.id = id;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
