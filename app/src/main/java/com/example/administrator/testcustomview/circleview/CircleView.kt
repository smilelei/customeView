package com.example.administrator.testcustomview.circleview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.administrator.testcustomview.R

/**
 * Created by Administrator on 2018/6/5.
 */
class CircleView: View{
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mRect: RectF = RectF()
    var mCenX: Float = 0.0f
    var mCenY: Float = 0.0f
    companion object {
        val LEFT: Int = 0
        val TOP: Int = 1
        val CENTER: Int = 2
        val RIGHT: Int = 3
        val BOOTOM: Int = 4
    }
    var mBg: Int = Color.GRAY
    var mProBg: Int = Color.BLUE
    var mRadioous: Float = 0.0f
    var mProgress: Int = 0
    var mGrviate: Int = CENTER
    constructor(mConText: Context): super(mConText){}
    constructor(mConText: Context,mAttributeset: AttributeSet): super(mConText,mAttributeset){initParm(mConText,mAttributeset)}
    constructor(mConText: Context,mAttributeset: AttributeSet,mStyle: Int): super(mConText,mAttributeset,mStyle){initParm(mConText,mAttributeset)}
    fun initParm(mConText: Context,mAttributeset: AttributeSet){
        var typedArray: TypedArray? = mConText.obtainStyledAttributes(mAttributeset, R.styleable.myCircle)
        if(typedArray != null){
        mRadioous = typedArray.getDimension(R.styleable.myCircle_myRadious,0.0f)
        mBg = typedArray.getColor(R.styleable.myCircle_myBg,Color.GRAY)
        mProBg = typedArray.getColor(R.styleable.myCircle_myProBg,Color.BLUE)
        mProgress = typedArray.getInt(R.styleable.myCircle_myProgress,0)
        mGrviate = typedArray.getInt(R.styleable.myCircle_myGravity,CENTER)
        typedArray.recycle()}
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize: Int  = MeasureSpec.getSize(heightMeasureSpec)
        mCenX = (widthSize/2).toFloat()
        mCenY = (heightSize/2).toFloat()
        when(mGrviate){
            LEFT -> mCenX = mRadioous + paddingLeft
            TOP -> mCenY = mRadioous + paddingTop
            RIGHT -> mCenX = width - mRadioous - paddingRight
            BOOTOM -> mCenY = height - mRadioous - paddingBottom
        }
        var left: Float = mCenX - mRadioous
        var top: Float = mCenY - mRadioous
        var right: Float = mCenX + mRadioous
        var bootom: Float = mCenY + mRadioous
        mRect.set(left,top,right,bootom)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        mPaint.color = mBg
        mPaint.style = Paint.Style.FILL_AND_STROKE
       //var radious: Float = widthh/4f
        if (canvas != null) {
            canvas.drawCircle(mCenX, mCenY,mRadioous,mPaint)
            mPaint.color = mProBg
            var pre: Double = mProgress*1.0/100
            var ag: Int = (pre*360).toInt()
            canvas.drawArc(mRect,270f,ag.toFloat(),true,mPaint)
        }
    }
}