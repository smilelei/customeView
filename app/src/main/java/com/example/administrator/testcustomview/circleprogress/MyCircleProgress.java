package com.example.administrator.testcustomview.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.example.administrator.testcustomview.R;
import com.example.administrator.testcustomview.Util.DpOrPxUtils;

/**
 * Created by Administrator on 2018/6/27.
 */

public class MyCircleProgress extends View {
    Paint mPaint;
    Paint mProgressPaint;
    Paint mBgPaint;
    //进度的值和最大值
    float proNum;
    float maxNum;
    //角度和
    float mProgressSweepAngle;
    float mStartAngle;
    //float mSweepAngle;

    private float sweepAngle;
    CircleAnimal animal;
    int x = 50;
    int y = 50;

    float barwidth;
    int defaultSize;
    int bgColor;
    int proColor;
    RectF rectF;

    public MyCircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.progress);
        if (array != null) {
            barwidth = array.getFloat(R.styleable.progress_bar_width, 15);
            bgColor = array.getColor(R.styleable.progress_bg_color, Color.GRAY);
            proColor = array.getColor(R.styleable.progress_pro_color, Color.RED);
            mStartAngle = array.getFloat(R.styleable.progress_start_angle, 0);
            sweepAngle = array.getFloat(R.styleable.progress_swep_angle, 360);
            array.recycle();
        }

        defaultSize = DpOrPxUtils.dip2px(context, 100);
        barwidth = DpOrPxUtils.dip2px(context, 10);
        rectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(proColor);
        mProgressPaint.setStrokeWidth(barwidth);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(bgColor);
        mBgPaint.setStrokeWidth(barwidth);

        proNum = 0;
        maxNum = 100;

//        mStartAngle = 0;
//        sweepAngle = 360;
        animal = new CircleAnimal();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = measureSize(defaultSize, heightMeasureSpec);
        int widthSize = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(heightSize, widthSize);
        setMeasuredDimension(min, min);
        if (min >= 2 * barwidth) {
            rectF.set(barwidth / 2, barwidth / 2, min - barwidth / 2, min - barwidth / 2);
        }
    }

    public int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int spaceMode = MeasureSpec.getMode(measureSpec);
        int spaceSize = MeasureSpec.getSize(measureSpec);
        if (spaceMode == MeasureSpec.EXACTLY) {
            result = spaceSize;
        } else if (spaceMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, spaceSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //RectF rectF = new RectF(x,y,x+300,y+300);
        // canvas.drawRect(rectF,mPaint);
        canvas.drawArc(rectF, mStartAngle, sweepAngle, false, mBgPaint);
        canvas.drawArc(rectF, mStartAngle, mProgressSweepAngle, false, mProgressPaint);
    }

    public void setProgressTime(int time) {
        animal.setDuration(time);
        startAnimation(animal);
    }

    //设置百分比和动画时间
    public void steNum(int num, int time) {
        this.proNum = num;
        setProgressTime(time);
    }

    private TextView tx;
    public void setText(TextView tx){
        this.tx = tx;
    }
    onAnimationListener onAnimationListener;
    public void steOnAnimationListener(onAnimationListener onAnimationListener){
        this.onAnimationListener = onAnimationListener;
    }
   public interface onAnimationListener {
        String howtoChangeTetx(Paint paint, float interpolatedTime, float proNum, float maxNum);
    }

    class CircleAnimal extends Animation {
        public CircleAnimal() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mProgressSweepAngle = interpolatedTime * sweepAngle * proNum / maxNum;
            postInvalidate();
            if(tx != null && onAnimationListener != null){
                tx.setText(onAnimationListener.howtoChangeTetx(mProgressPaint,interpolatedTime,proNum,maxNum));
            }
        }
    }
}
