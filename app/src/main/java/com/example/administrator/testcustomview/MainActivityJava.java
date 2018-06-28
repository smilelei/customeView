package com.example.administrator.testcustomview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.administrator.testcustomview.circleprogress.MyCircleProgress;

/**
 * Created by Administrator on 2018/6/28.
 */

public class MainActivityJava extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyCircleProgress pro = findViewById(R.id.pro);
        TextView tx = findViewById(R.id.tx);
        pro.steNum(60,8000);
        pro.setText(tx);
        //pro.setAnimation(new MyCircleProgress.onAnimationListener(){});

    }
}
