package com.example.lunarlander;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Timer;

public class LunarLander extends AppCompatActivity {

    private LunarView mLunarView;
    private LunarAnimator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunar_layout);
        mLunarView = (LunarView) findViewById(R.id.lunar);

        animator = new LunarAnimator(mLunarView);
        animator.execute(mLunarView.getLanderX(), mLunarView.getLanderY());
    }
    @Override
    protected void onPause() { super.onPause(); }

    public void onTouch(View view){
        animator.pauseAnimator();
    }

    public void onClick(View v) {
        final float increaseHeight = mLunarView.getHeight() * 0.1f;
        int targetHeight = (int) (mLunarView.getLanderY() - increaseHeight);
        ValueAnimator valanimator = ValueAnimator.ofInt(mLunarView.getLanderY(), targetHeight);
        // 높이를 10% 증가시킵니다.
        valanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int)animation.getAnimatedValue();
                mLunarView.setLanderPos(mLunarView.getLanderX(), progress);
                Log.d("progress", String.valueOf(progress));
                // 보간된 값에 따라 높이를 조정하여 새 높이를 계산합니다.
            }
        });
        // AccelerateDecelerateInterpolator를 사용합니다.
        valanimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valanimator.setDuration(1000);
        valanimator.start();
        animator.setmY(targetHeight);
    }


    @Override
    protected void onSaveInstanceState(Bundle outstate){super.onSaveInstanceState(outstate);}

}