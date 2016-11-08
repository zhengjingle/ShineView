package com.zjl.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * @author zhengjingle
 */
public class ShineView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private float x,y;//光源坐标
    private float detalX,detalY;
    private boolean hasInitXY;//光源是否初始化
    private float startRange;//
    private float sweepAngle=45;//角度
    private float addRange;//增量
    private int lightSize=1;//光线数量
    private SurfaceHolder mHolder;
    private Thread thread;
    private boolean isRun;
    private int bgColor=Color.WHITE;
    private int lightColor=Color.GREEN;
    
    public static final int CLOCKWISE=1;//顺时针
    public static final int ANTICLOCKWISE=2;//逆时针
    private int rotationDirection=CLOCKWISE;
    
    public ShineView(Context context) {
        super(context);
        mHolder=getHolder();
        mHolder.addCallback(this);
    }

    public ShineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder=getHolder();
        mHolder.addCallback(this);
    }

    Paint p= null;
    RectF oval = null;
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        p=new Paint();
        p.setColor(lightColor);
        oval= new RectF(-getWidth()+detalX, -getHeight()+detalY, getWidth()*2+detalX, getHeight()*2+detalY);

        isRun=true;
        thread=new Thread(this);
        thread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun=false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(x>getMeasuredWidth())x=getMeasuredWidth();
        if(x<0)x=0;
        if(y>getMeasuredHeight())y=getMeasuredHeight();
        if(y<0)y=0;

        float centerX=getMeasuredWidth()/2;
        float centerY=getMeasuredHeight()/2;

        if(hasInitXY){
            detalX=x-centerX;
            detalY=y-centerY;
        }else{
            detalX=0;
            detalY=0;
        }

    }

    @Override
    public void run() {
        while(isRun){
            Canvas canvas=mHolder.lockCanvas();
            canvas.drawColor(bgColor);
            for(int i=0;i<lightSize;i++){
                canvas.drawArc(oval, startRange+addRange*i, sweepAngle, true, p);
            }
            mHolder.unlockCanvasAndPost(canvas);

            SystemClock.sleep(50);//设置更小的数值可以加快速度
            if(rotationDirection==ANTICLOCKWISE){
                startRange-=1;
            }else{
                startRange+=1;
            }
            this.postInvalidate();
        }
    }

    /**
     * 设置光源在控件内的坐标位置X,Y。不设置，默认光源在控件的中心。
     * @param x 控件内x坐标
     * @param y 控件内y坐标
     */
    public void setLightXY(float x,float y){
        this.x=x;
        this.y=y;
        hasInitXY=true;
    }

    /**
     * 设置光线数量
     * @param lightSize 光线数量
     */
    public void setLightSize(int lightSize){
        if(lightSize<1){
            lightSize=1;//最少一条光线
            sweepAngle=45;//弧度最大45
        }

        this.lightSize=lightSize;
        this.addRange=360/lightSize;
        this.sweepAngle=this.addRange/2;
    }

    /**
     * 设置背景颜色
     * @param bgColor 背景颜色
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 设置光线颜色
     * @param lightColor 光线颜色
     */
    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    /**
     * 设置转动方向。不设置，默认是顺时针。
     * @param rotationDirection 转动方向
     */
    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
}
