package com.example.lunarlander;

import android.os.AsyncTask;

public class LunarAnimator extends AsyncTask<Integer, Integer, Integer> {

    private LunarView mLanderView;
    private int mX, mY;

    public void setmY(int mY) {
        this.mY = mY;
    }

    public LunarAnimator(LunarView lander){
        mLanderView = lander;
    }

    protected Integer doInBackground(Integer...integers){
        mX = integers[0];
        mY = integers[1];
        while (true){
            if (!mPaused) {
                mX += 5;
                mY += 5;
                if (mX >= mLanderView.getWidth())
                    mX = 0;
                publishProgress(mX, mY);
            }
            try{
                Thread.sleep(100);
            }catch (Exception e){}
        }
    }
    @Override
    protected void onProgressUpdate(Integer...values){
        super.onProgressUpdate(values);
        mLanderView.setLanderPos(values[0],values[1]);
        mLanderView.invalidate();
    }
    private boolean mPaused = false;
    public void pauseAnimator() {
        mPaused = (!mPaused);
    }
}
