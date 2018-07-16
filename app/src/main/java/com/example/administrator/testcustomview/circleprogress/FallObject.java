package com.example.administrator.testcustomview.circleprogress;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class FallObject {
    private int mInitX;
    private int mInitY;
    private int mInitSpeed;

    private Random random;

    private int mParentWidth;
    private int mParentHeight;
    private float mObjectWidth;
    private float mObjectHeight;

    private float mPresentX;
    private float mPresentY;
    private float mPresentSpeed;

    private Bitmap mBitmap;
    public Builder builder;


    private static final int DEFAULTSPEED = 20;
    public FallObject(Builder builder,int parentHeight,int parentWidth){
        random = new Random();
        this.mParentHeight = parentHeight;
        this.mParentWidth = parentWidth;
        mInitX = random.nextInt(mParentWidth);
        mInitY = random.nextInt(mParentHeight);

        mPresentX = mInitX;
        mPresentY = mInitY;

        this.mBitmap = builder.bitmap;

        mInitSpeed = builder.initSpeed;
        mPresentSpeed = mInitSpeed;

        mObjectWidth = mBitmap.getWidth();
        mObjectHeight = mBitmap.getHeight();

    }
    public FallObject(Builder builder){
        this.builder = builder;
        this.mBitmap = builder.bitmap;
        this.mInitSpeed = builder.initSpeed;
    }
    public static final class Builder {
        private int initSpeed;
        private Bitmap bitmap;

        public Builder(Bitmap bitmap) {
            this.bitmap = bitmap;
            this.initSpeed = DEFAULTSPEED;
        }

        public Builder setSpeed(int initSpeed) {
            this.initSpeed = initSpeed;
            return this;
        }

        public FallObject build() {
            return new FallObject(this);
        }
    }
    public void drawObject(Canvas canvas){
        moveObject();
        canvas.drawBitmap(mBitmap,mPresentX,mPresentY,null);
    }
    private void moveObject(){
        moveY();
        if(mPresentY > mParentHeight){
            reset();
        }
    }
    private void moveY(){
        mPresentY += mPresentSpeed;
    }
    public void reset(){
            mPresentY = mInitY;
            mPresentSpeed = mInitSpeed;
    }
}
