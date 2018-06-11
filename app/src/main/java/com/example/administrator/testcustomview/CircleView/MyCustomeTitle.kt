package com.example.administrator.testcustomview.CircleView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.administrator.testcustomview.R
import org.w3c.dom.Text

/**
 * Created by Administrator on 2018/6/11.
 */
class MyCustomeTitle : ConstraintLayout {
    var leftText1: String = ""
    var leftText2: String = ""
    var cen: String = ""
    var rightText1: String = ""
    var rightText2: String = ""
    var r: String = ""

    constructor(context: Context?, attrs: AttributeSet?): super(context,attrs){
        LayoutInflater.from(context).inflate(R.layout.custome_title_cos,this,true)
        var title1: TextView = findViewById<View>(R.id.title1) as TextView
        var title2: TextView = findViewById<View>(R.id.title2) as TextView
        var arr: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.myTitle)
        leftText1 = arr.getString(R.styleable.myTitle_left1_text)
        leftText2 = arr.getString(R.styleable.myTitle_left2_text)
        cen = arr.getString(R.styleable.myTitle_cen_text)
        rightText1 = arr.getString(R.styleable.myTitle_right2_text)
        r = arr.getString(R.styleable.myTitle_left2_text_visable)
        
        title1.setText(leftText1)
        title2.setText(leftText2)

        arr.recycle()
    }
}