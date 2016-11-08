package com.shineview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zjl.customview.ShineView;

/**
 * @author zhengjingle
 */
public class MainActivity extends AppCompatActivity {

    private ShineView mShineView1,mShineView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShineView1=(ShineView)findViewById(R.id.shineView1);
        mShineView2=(ShineView)findViewById(R.id.shineView2);

        mShineView1.setLightXY(0,0);//设置光源在控件内的坐标位置X,Y。不设置，默认光源在控件的中心。
        mShineView1.setLightSize(10);//光线数量
        mShineView1.setRotationDirection(ShineView.ANTICLOCKWISE);//逆时针转动

        mShineView2.setLightSize(10);
        mShineView2.setBgColor(Color.parseColor("#FFD016"));//背景颜色
        mShineView2.setLightColor(Color.parseColor("#88FFFECB"));//光线颜色
    }
}
