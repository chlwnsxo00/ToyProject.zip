package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onColorButtonClicked(View v){
        SingleTouchView single = findViewById(R.id.singleTouchView);
        switch (v.getId()){
            case R.id.btn_black:
                single.setBlack();
                break;
            case R.id.btn_green:
                single.setGreen();
                break;
            case R.id.btn_red:
                single.setRed();
                break;
            case R.id.btn_clear:
                single.clearPaths();
                break;
        }
    }

}