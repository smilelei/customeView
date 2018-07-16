package com.example.administrator.testcustomview.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class SnowView extends View{

    int mViewHeight;
    int mVviewWidth;
    int mSnowY;

    Context mContext;
    AttributeSet mAttrs;
    Paint mPaint;

    private static final int INTERTIME = 5;
    private static final int DEFAULTHEIGHTSIZE = 1000;
    private static final int MDEFAULTWIDTHSIZE = 600;

    List<FallObject> fallObjects;
    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public SnowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    public void  init(Context context, @Nullable AttributeSet attrs){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mContext = context;
        mAttrs = attrs;

        mSnowY = 0;

        fallObjects = new ArrayList<>();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = measureSize(MDEFAULTWIDTHSIZE,widthMeasureSpec);
        int heightSize = measureSize(DEFAULTHEIGHTSIZE,heightMeasureSpec);

        setMeasuredDimension(widthSize,heightSize);

        mVviewWidth = widthSize;
        mViewHeight = heightSize;

    }
    private int measureSize(int defaultSize,int spec){
        int size = MeasureSpec.getSize(spec);
        int mode = MeasureSpec.getMode(spec);
        if(mode == MeasureSpec.AT_MOST){
            size = Math.min(defaultSize,size);
        }
        return size;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,mSnowY,25,mPaint);
        if(fallObjects.size() > 0){
            for (int i = 0; i<fallObjects.size(); i++){
                fallObjects.get(i).drawObject(canvas);
            }
            getHandler().postDelayed(runnable,INTERTIME);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            mSnowY += 15;
//             if(mSnowY > mViewHeight){
//                 mSnowY = 0;
//             }
             invalidate();
        }
    };
    public void addFallObject(final FallObject fallObject, final int num){
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                for (int i = 0; i < num; i++){
                    FallObject newFallObject = new FallObject(fallObject.builder,mVviewWidth,mViewHeight);
                    fallObjects.add(newFallObject);
                }
                invalidate();
                return true;
            }
        });
    }
}
