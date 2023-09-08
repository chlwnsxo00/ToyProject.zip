package com.example.lunarlander;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class LunarView extends View {
    private int x=0;
    private int y=0;
    private int mCanvasHeight=1;
    private int mCanvasWidth=1;
    private Bitmap mBackgroundImage;
    private Drawable mLanderImage;

    public LunarView(Context context){
        super(context);
        init(context);
    }

    public LunarView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context context){
        Resources res = context.getResources();
        mLanderImage = context.getResources().getDrawable(R.drawable.lander_plain);
        mBackgroundImage = BitmapFactory.decodeResource(res,R.drawable.earthrise);
    }

    protected  void onDraw(Canvas canvas){
        mCanvasHeight = canvas.getHeight();
        mCanvasWidth = canvas.getWidth();
        if(mBackgroundImage.getWidth() != mCanvasWidth||mBackgroundImage.getHeight()!= mCanvasHeight)
            mBackgroundImage = mBackgroundImage.createScaledBitmap(mBackgroundImage, mCanvasWidth,mCanvasHeight,true);
        canvas.drawBitmap(mBackgroundImage,0,0,null);
        mLanderImage.setBounds(x,y,x+100,y+100);
        if(x>mCanvasWidth) x= 0;
        if(y>mCanvasHeight) y= 0;
        mLanderImage.draw(canvas);
    }

    public int getLanderX(){return x;}
    public int getLanderY(){return y;}
    public void setLanderPos(int lx,int ly){x= lx;y=ly;}
}
