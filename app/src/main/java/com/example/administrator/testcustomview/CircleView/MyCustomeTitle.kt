package com.example.administrator.testcustomview.CircleView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
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
    var leftText1: String = "tx1"
    var leftText2: String? = "tx2"
    var cen: String? = "tx3"
    var rightText1: String? = "tx4"
    var rightText2: String? = "tx5"

    var left1vis: Int = -1
    var left2vis: Int = -1
    var cenvis: Int = -1
    var right1vis: Int = -1
    var right2vis: Int = -1

    //lateinit var src: Drawable

    constructor(context: Context?, attrs: AttributeSet?): super(context,attrs){
        LayoutInflater.from(context).inflate(R.layout.custome_title_cos,this,true)
        var title1: TextView = findViewById<View>(R.id.title1) as TextView
        var title2: TextView = findViewById<View>(R.id.title2) as TextView
        var title3: TextView = findViewById<View>(R.id.title3) as TextView
        var title4: TextView = findViewById<View>(R.id.title4) as TextView
        var title5: TextView = findViewById<View>(R.id.title5) as TextView
        var arr: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.myTitle)

        leftText1 = arr.getString(R.styleable.myTitle_left1_text)
        leftText2 = arr.getString(R.styleable.myTitle_left2_text)
        cen = arr.getString(R.styleable.myTitle_cen_text)
        rightText1 = arr.getString(R.styleable.myTitle_right1_text)
        rightText2 = arr.getString(R.styleable.myTitle_right2_text)

        left1vis = arr.getInt(R.styleable.myTitle_left1_text_visable,View.VISIBLE)
        left2vis = arr.getInt(R.styleable.myTitle_left2_text_visable,View.VISIBLE)
        cenvis = arr.getInt(R.styleable.myTitle_cen_text_visable,View.VISIBLE)
        right1vis = arr.getInt(R.styleable.myTitle_right1_text_visable,View.VISIBLE)
        right2vis = arr.getInt(R.styleable.myTitle_right2_text_visable,View.VISIBLE)


       // src = arr.getDrawable(R.styleable.myTitle_image)


        
        title1.setText(leftText1)
        title2.setText(leftText2)
        title3.setText(cen)
        title4.setText(rightText1)
        title5.setText(rightText2)

        title1.visibility = left1vis
        title2.visibility = left2vis
        title3.visibility = cenvis
        title4.visibility = right1vis
        title5.visibility = right1vis

        arr.recycle()
    }
}