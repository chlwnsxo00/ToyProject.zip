package com.example.animationservice;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, AnimationService.class);
        MyGLSurfaceView glview = (MyGLSurfaceView) findViewById(R.id.glView);
        Messenger angleReceiver = glview.getAngleReceiver();

        glview.setAnimeStarted();

        // Create a ValueAnimator for the rotation animation
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 360.0f);
        animator.setDuration(1000); // Set the duration of the animation to 1000 milliseconds
        animator.setInterpolator(new OvershootInterpolator()); // Apply OvershootInterpolator
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                glview.requestRender(); // Request a render to update the view
            }
        });

        // Start the animator
        animator.start();

        intent.putExtra("ValueMessenger", angleReceiver);
        intent.putExtra("StartValue", 0.0f);
        intent.putExtra("EndValue", 360.0f);
        intent.putExtra("IncValue", 1.0f);
        intent.putExtra("UpdateTime", 100.0f);
        startService(intent);
    }

    public void onClickStop(View view){
        MyGLSurfaceView glView = (MyGLSurfaceView) findViewById(R.id.glView);
        glView.setAnimeStopped();
        glView.clearAnimation();
    }
}