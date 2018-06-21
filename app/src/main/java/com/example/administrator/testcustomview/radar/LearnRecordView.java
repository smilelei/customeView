package com.example.administrator.testcustomview.radar;

/**
 * Created by Administrator on 2018/6/14.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.administrator.testcustomview.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:  自定义柱状图
 * @author: fanrunqi@qq.com
 * @date: 2018/6/9 11:46
 */
public class LearnRecordView extends View {
    //柱状图之间的间隔距离
    private float interval=2;
    //间距的颜色
    private String intervalColor ="#f0f0f2";
    //view 的宽度
    private float viewWidth;
    //view 的高度
    private float viewHeight;
    //数字距离顶部的距离
    private float numFromTop;
    //文字距离顶部的距离
    private float textFromTop;
    //柱状图的最大高度;
    private float HistogramMaxHeight;
    //每个柱状图的宽度
    private float Histogramwidth;
    //柱状图的背景色
    private String HistogramColor ="#f0f0f2";

    //数字颜色
    private String numColor="#222222";
    //文字颜色
    private String textColor="#a9a9a9";

    //间隔线画笔
    private Paint intervalPaint;
    //柱状图画笔
    private Paint HistogramPaint;
    //数字画笔
    private Paint numPaint;
    //文字画笔
    private Paint textPaint;

    List<LeanRecordBean> datas;

    Context ctx;

    public LearnRecordView(Context context) {
        this(context, null);
    }

    public LearnRecordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearnRecordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx =context;
        init();
    }
    //构造函数初始化
    private void init() {

        //数字画笔
        numPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        numPaint.setTypeface(Typeface.DEFAULT_BOLD);
        numPaint.setColor(Color.parseColor(numColor)); // 设置paint颜色
        numPaint.setAntiAlias(true);       // 设置抗锯齿
        numPaint.setTextAlign(Paint.Align.CENTER); // 设置字体居中

        //文字画笔
        textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor(textColor));
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        //间隔线画笔，粗线条
        intervalPaint=new Paint();
        intervalPaint.setColor(Color.parseColor(intervalColor));
        intervalPaint.setAntiAlias(true);
        intervalPaint.setStrokeCap( Paint.Cap.SQUARE);
        intervalPaint.setStrokeWidth(interval);

        //柱状图画笔，画路径连线
        HistogramPaint=new Paint();
        HistogramPaint.setColor(Color.parseColor(HistogramColor));
        HistogramPaint.setAntiAlias(true);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setAxis();
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);

        drawHistogram(canvas);

        if(curItemClick!=-1){
            //画点击效果
            drawClickedItem(canvas);

        }
    }

    int curItemClick = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 判断点击位置
         */
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float curX = (int)event.getX();
            int distance = (int)(interval+Histogramwidth);
            float i = curX/distance;
            float j = curX%distance;
            if (j>interval){
                if(curItemClick == (int)i){
                    curItemClick = -1;
                }else {
                    curItemClick =(int)i;
                    if(curItemClick>4){
                        curItemClick=4;
                    }
                }
            }
            startAnim2();
        }

        return super.onTouchEvent(event);

    }

    //阴影的宽度
    private float shadowHeight = 30;
    //点击放大的距离
    private float overflowLength=6;
    //间距
    private float space = shadowHeight+overflowLength;
    //红色画布padding距离
    private float padding=20;
    /**
     *  画点击后的视图
     * @param canvas
     */

    String [] itemColors={"#fdd124","#17cff9","#ff3f7c"};
    String [] itemShadow={"#feefe1","#e1f2fe","#fee1ea"};
    private void drawClickedItem(Canvas canvas){

        //需要绘制图标的个数
        List<Bitmap> list = new ArrayList<>();

        if(datas.get(curItemClick).getQuestionFlag()){
           // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.question_white);
            // list.add(setImgSize(bitmap,imgSize,imgSize));
        }
        if(datas.get(curItemClick).getVideoFlag()){
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.video_white);
            //list.add(setImgSize(bitmap,imgSize,imgSize));
        }
        if(datas.get(curItemClick).getLiveFlag()){
         //   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.live_white);
           // list.add(setImgSize(bitmap,imgSize,imgSize));
        }
        float redHeight = (HistogramMaxHeight/3)*list.size();

        Path path = new Path();
        path.moveTo(x1,y1);
        path.lineTo(x2,y1);
        path.lineTo(x2,y2);
        path.lineTo(x1,y2);
        path.close();
        Paint mPaint =new Paint();
        mPaint.setShadowLayer(shadowHeight,0,3,Color.parseColor(itemShadow[list.size()-1]));
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,mPaint);//设置为SOFTWARE才会实现阴影
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        canvas.drawPath(path,mPaint);

        //文字和数字
        numPaint.setTextSize(DensityUtil.sp2px(ctx,18));       // 设置字体大小DP
        textPaint.setTextSize(DensityUtil.sp2px(ctx,12));
        drawTextAndNum(curItemClick,canvas);


        //绘制布
        Path redPath = new Path();
        float z1 = x1+padding;
        float z2 =  x2-padding;
        float w2 = y2-padding;
        float w1 = w2-redHeight;

        redPath.moveTo(z1,w1);
        redPath.lineTo(z2,w1);
        redPath.lineTo(z2,w2);
        redPath.lineTo(z1,w2);
        redPath.close();
        Paint redPaint =new Paint();
        redPaint.setColor(Color.parseColor(itemColors[list.size()-1]));
        redPaint.setAntiAlias(true);
        canvas.drawPath(redPath,redPaint);


        Paint mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);


        float distance = (redHeight-imgSize*(list.size()) )/(list.size()+1);
        for(int i=0;i<list.size();i++){
            int left = (int)( (z2-z1-imgSize)/2+z1 );
            int top = (int) ((w2-distance-imgSize)-i*(distance+imgSize) );
            canvas.drawBitmap(list.get(i), left, top, mBitPaint);
        }

    }
    private int imgSize=46;
    /**
     *  重设图片大小
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newBm;
    }

    /**
     * 画柱状图
     * @param canvas
     */
    private void drawHistogram(Canvas canvas){

        float HMH = 0;
        Path path = new Path();
        for(int i=0;i<5;i++){
            if(0==i){
                HMH = HistogramMaxHeight0;
            } else if(1==i){
                HMH = HistogramMaxHeight1;
            }else if(2==i){
                HMH = HistogramMaxHeight2;
            }else if(3==i ){
                HMH = HistogramMaxHeight3;
            }else if(4==i){
                HMH = HistogramMaxHeight4;
            }

            float per = datas.get(i).getValue()/3f;
            path.moveTo(space+interval+i*(interval+Histogramwidth),space+viewHeight-(per*HMH));
            path.lineTo(space+interval+i*(interval+Histogramwidth)+Histogramwidth,space+viewHeight-(per*HMH));
            path.lineTo(space+interval+i*(interval+Histogramwidth)+Histogramwidth,space+viewHeight);
            path.lineTo(space+interval+i*(interval+Histogramwidth),space+viewHeight);
            path.close();
            canvas.drawPath(path,HistogramPaint);
        }


    }
    /**
     * 画背景
     * @param canvas
     */
    private void drawBg(Canvas canvas){

        for(int i=0;i<5;i++){
            numPaint.setTextSize(DensityUtil.sp2px(ctx,15));       // 设置字体大小DP
            textPaint.setTextSize(DensityUtil.sp2px(ctx,9));
            drawTextAndNum(i,canvas);
            //--------------------------画间隔线---------------------
            canvas.drawLine( interval/2+i*(Histogramwidth+interval)+space,space,
                    i*(Histogramwidth+interval)+space, space+viewHeight, intervalPaint );
            if(i==4){
                i+=1;
                canvas.drawLine( space+i*(Histogramwidth+interval),space,
                        i*(Histogramwidth+interval)+space, space+viewHeight, intervalPaint );
            }
        }
    }

    private void drawTextAndNum(int i , Canvas canvas){
        //--------------------------画数字---------------------

//            float stringWidth = numPaint.measureText(datas.get(i).getDay());
        Paint.FontMetrics fontMetrics = numPaint.getFontMetrics();
        float stringHeight =  (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
        float x = interval + Histogramwidth/2+ i*(Histogramwidth+interval)+space;
        float y = numFromTop +stringHeight;
        canvas.drawText(datas.get(i).getDay(), x, y, numPaint);

        //--------------------------画文字---------------------

        Paint.FontMetrics fontMetrics1 = textPaint.getFontMetrics();
        float stringHeight1 =  (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
        float x1 = interval + Histogramwidth/2+ i*(Histogramwidth+interval)+space;
        float y1 = textFromTop +stringHeight1;
        canvas.drawText(datas.get(i).getXinqi(), x1, y1, textPaint);

    }

    /**
     * 初始获取画布坐标
     */
    private void setAxis(){
        viewWidth = getWidth()-2*space;
        viewHeight = getHeight()-2*space;

        numFromTop = (float) (viewHeight*0.043478)+space;
        textFromTop = (float)(viewHeight*0.162857)+space;
        HistogramMaxHeight = (float)(viewHeight*0.707764);

        Histogramwidth = (float)((viewWidth-6*interval)/5.0);

    }

    /**
     * 点击放大动画
     */
    float x1,x2,y1,y2;
    float x11,x22;
    public void startAnim2(){
        x11=x1 =space+ curItemClick*(interval+Histogramwidth)-overflowLength;
        x22=x2 = space+(1+curItemClick)*(interval+Histogramwidth)+overflowLength;
        y1 = shadowHeight;
        y2 = getHeight()-shadowHeight;

        ValueAnimator animator = ValueAnimator.ofFloat(0,100);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float)animation.getAnimatedValue();
                x1 = x11+overflowLength-overflowLength*(v/100);
                x2 = x22-overflowLength+overflowLength*(v/100);
                y1 = 30+overflowLength - overflowLength*(v/100);
                y2 = getHeight()-30-overflowLength+ overflowLength*(v/100);

                shadowHeight = 30*(v/100);
                invalidate();
            }
        });
        animator.start();
    }

    /**
     * 柱状图加载动画
     */
    float HistogramMaxHeight0,HistogramMaxHeight1,HistogramMaxHeight2,HistogramMaxHeight3,HistogramMaxHeight4 =0;
    public void startAnim(){

        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                synAnim(count);
                if (count < 5) {
                    handler.postDelayed(this, 100);
                    count++;
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }
    int count=0;
    public void synAnim(final int i){
        ValueAnimator animator = ValueAnimator.ofFloat(0,HistogramMaxHeight);
        animator.setDuration(700-i*100);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHistogramMaxHeight((float)animation.getAnimatedValue(),i);
                invalidate();
            }
        });
        animator.start();

    }
    private void setHistogramMaxHeight(float H,int i){
        switch (i){
            case 0:
                HistogramMaxHeight0 = H;
                break;
            case 1:
                HistogramMaxHeight1 = H;
                break;
            case 2:
                HistogramMaxHeight2 = H;
                break;
            case 3:
                HistogramMaxHeight3 = H;
                break;
            case 4:
                HistogramMaxHeight4 = H;
                break;

        }
    }


    public void setData(List<LeanRecordBean> datas){
        this.datas=datas;
    }
}