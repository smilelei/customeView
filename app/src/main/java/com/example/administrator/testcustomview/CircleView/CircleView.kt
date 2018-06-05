package com.example.administrator.testcustomview.CircleView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

/**
 * Created by Administrator on 2018/6/5.
 */
class CircleView: View{
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mRect: RectF = RectF()
    constructor(mConText: Context): super(mConText){}
    constructor(mConText: Context,mAttributeset: AttributeSet): super(mConText,mAttributeset){}
    constructor(mConText: Context,mAttributeset: AttributeSet,mStyle: Int): super(mConText,mAttributeset,mStyle){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize: Int  = MeasureSpec.getSize(heightMeasureSpec)

    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        mPaint.color = Color.GRAY
        mPaint.style = Paint.Style.FILL_AND_STROKE
        var width: Int = width
        var heiht: Int = height
        var radious: Float = width/4f
        if (canvas != null) {
            canvas.drawCircle((width/2).toFloat(), (heiht/2).toFloat(),radious,mPaint)
            mPaint.color = Color.BLUE
            mRect.set(width/2-radious, heiht/2-radious, width/2+radious, heiht/2+radious)
            canvas.drawArc(mRect,270f,120f,true,mPaint)
        }
    }
}