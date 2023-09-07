package com.example.animationservice;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.AttributeSet;


public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private boolean isAnimeStopped = false;

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(isAnimeStopped != true)
                mRenderer.setAngle((Float)(msg.obj));
            requestRender();
        }
    };

    public Messenger getAngleReceiver(){
        return new Messenger(handler);
    }

    public void setAnimeStopped(){
        isAnimeStopped = true;
    }

    public void setAnimeStarted(){
        isAnimeStopped = false;
    }
}