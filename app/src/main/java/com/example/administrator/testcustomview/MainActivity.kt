package com.example.administrator.testcustomview

import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.administrator.testcustomview.Util.LinearGradientUtil
import com.example.administrator.testcustomview.circleprogress.FallObject
import com.example.administrator.testcustomview.circleprogress.MyCircleProgress
import com.example.administrator.testcustomview.circleprogress.SnowView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        //var pro: MyCircleProgress = findViewById(MyCircleProgress)(R.id.pro)
        //pro.setProgressTime(8000)
        pro.setText(tx)
//        pro.steOnAnimationListener(object : MyCircleProgress.onAnimationListener{
//            override fun howtoChangeTetx(interpolatedTime: Float, proNum: Float, maxNum: Float): String {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
        pro.apply {
            steOnAnimationListener(object : MyCircleProgress.onAnimationListener{
                override fun howtoChangeTetx(paint: Paint, interpolatedTime: Float, proNum: Float, maxNum: Float): String {
                    var format: DecimalFormat = DecimalFormat("0.00")
                    var s: String = format.format(interpolatedTime*proNum/maxNum*100)+"%"
                    var linearUtil: LinearGradientUtil = LinearGradientUtil(Color.YELLOW,Color.RED)
                    paint.color = linearUtil.getColor(interpolatedTime)
                    return s
                }
            })
        }
        pro.steNum(60,8000)

        var snowPaint: Paint = Paint()
        snowPaint.style = Paint.Style.FILL
        snowPaint.color = Color.WHITE

        var bitmap: Bitmap = Bitmap.createBitmap(50,50,Bitmap.Config.ARGB_8888)
        var canvas: Canvas = Canvas(bitmap)
        canvas.drawCircle(25f,25f,25f,snowPaint)


        var build: FallObject.Builder = FallObject.Builder(bitmap)
        var fallObject: FallObject = build.setSpeed(50).build();
        earth.setTime(3000)
       // snow.addFallObject(fallObject,50)

    }
}
