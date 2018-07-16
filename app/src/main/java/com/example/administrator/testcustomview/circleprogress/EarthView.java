package com.example.administrator.testcustomview.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
//好饿
public class EarthView extends View{
    Paint paint;
    float mPresentX = 150;
    float mPresentY = 100;
    float mPresentCenterX = 100;
    float mPresentCenterY = 100;
//
    roatAn mRoatAn;
    public EarthView(Context context) {
        super(context);
        mRoatAn = new roatAn();
    }

    public EarthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRoatAn = new roatAn();
    }

    public EarthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRoatAn = new roatAn();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        canvas.drawCircle(mPresentCenterX,mPresentCenterY,50,paint);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GREEN);
        //paint.setStrokeWidth(10);
        canvas.drawCircle(mPresentX,mPresentY,5,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // boolean isDown = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
                //isDown = true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                mPresentCenterX = event.getX();
                mPresentCenterY = event.getY();
                mPresentX = mPresentCenterX + 50;
                mPresentY = mPresentCenterY;
                postInvalidate();
        }
        return true;
    }

    public void setTime(int time){
        mRoatAn.setDuration(time);
        mRoatAn.setRepeatCount(1000);

        startAnimation(mRoatAn);
    }
    class roatAn extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mPresentX = mPresentCenterX + (float) (50*Math.cos(interpolatedTime*4*Math.PI));
            mPresentY = mPresentCenterY + (float) (50*Math.sin(interpolatedTime*4*Math.PI));
            postInvalidate();
        }
    }
}
