package com.example.administrator.testcustomview.rotationtextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

/**
 * Created by Administrator on 2018/6/15.
 */
 class RotationTextView: TextView{
    lateinit var mBackground: Drawable
    var mBackgroundPaint: Paint = Paint()
    lateinit var d: Drawable
    constructor(mContext: Context): this(mContext,null)
    constructor(mContext: Context,arr: AttributeSet?): this(mContext,arr,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var textWidth: Int = paint.measureText(text.toString(),0,text.length).toInt()
        var min: Int = (textWidth/Math.cos(Math.PI/4)).toInt()
        var width: Int = View.resolveSizeAndState(min,widthMeasureSpec,0)
        var height: Int = View.resolveSizeAndState(min,heightMeasureSpec,0)
        var result: Int = Math.max(width,height)
        setMeasuredDimension(result,result)
    }

    override fun onDraw(canvas: Canvas) {
        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.style  = Paint.Style.FILL
        if (mBackground != null){
            mBackgroundPaint.color = (mBackground as ColorDrawable).color
        } else {
            //mBackgroundPaint.color = ContextCompat.getColor(context, android.R.color.white)
            mBackgroundPaint.color = context.resources.getColor(android.R.color.white)
            //context.resources.getColor(android.R.color.white,null)
        }

        var fm: Paint.FontMetrics = paint.fontMetrics
        var textHeight: Double = Math.ceil((fm.descent-fm.top).toDouble())

        var backgroundPath: Path = Path()
        backgroundPath.moveTo(0f, (height-textHeight.toInt()-20).toFloat())
        backgroundPath.lineTo(0f,height.toFloat())
        backgroundPath.lineTo(width.toFloat(),0f)
        backgroundPath.lineTo((width - textHeight-20).toFloat(),0f)
        backgroundPath.close()
        canvas.drawPath(backgroundPath,mBackgroundPaint)

        var backgroundRectF:  RectF = RectF()
        backgroundPath.computeBounds(backgroundRectF,false)
        var textWidth: Int = paint.measureText(text.toString(),0,text.length).toInt()
        var delta: Double = textWidth /2 * Math.cos(Math.PI/4)

        var textPath: Path = Path()
        textPath.moveTo((backgroundRectF.centerX() - delta - 5).toFloat(),
                (backgroundRectF.centerY() + delta - 5).toFloat())
        textPath.lineTo((backgroundRectF.centerX() + delta - 5).toFloat(),
                (backgroundRectF.centerY() - delta - 5).toFloat())
        canvas.drawTextOnPath(text.toString(),textPath,0f,0f,paint)
    }

    override fun setBackgroundResource(resid: Int) {
        if (resid != null){
            d = context.resources.getDrawable(resid)
        }
        this.setBackground(d)
    }
    override fun setBackgroundColor(color: Int) {
        if(mBackground is ColorDrawable){
            (mBackground as ColorDrawable).color  = color
        } else {
            var colorDrawable: ColorDrawable = ColorDrawable()
            this.setBackground(colorDrawable)
        }
    }
    override fun setBackground(background: Drawable) {
       this.setBackgroundDrawable(background)
    }

    override fun  setBackgroundDrawable(background: Drawable){
        mBackground = background
    }
}