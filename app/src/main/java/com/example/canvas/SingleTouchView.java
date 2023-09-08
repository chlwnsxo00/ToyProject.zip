package com.example.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SingleTouchView extends View {
    private List<PathInfo> paths = new ArrayList<PathInfo>();
    private Paint black = new Paint();
    private Paint red = new Paint();
    private Paint green = new Paint();

    private int color = Color.BLACK;

    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(black);
        black.setColor(Color.BLACK);
        initPaint(red);
        red.setColor(Color.RED);
        initPaint(green);
        green.setColor(Color.GREEN);
        // 계단현상을 완화 ( 라인이 깔끔해짐 )
        // 사양이 안 좋을 경우 성능이 떨어질 가능성이 있음
    }

    private void initPaint(Paint paint) {
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);
    }

    public void clearPaths() {
        paths.clear();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        for (PathInfo pathInfo : paths) {
            Paint paint = pathInfo.color == Color.BLACK ? black : pathInfo.color == Color.RED ? red : green;
            canvas.drawPath(pathInfo.path, paint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PathInfo pathInfo = new PathInfo(color, new Path());
                pathInfo.path.moveTo(eventX, eventY);
                paths.add(pathInfo);
                break;
            case MotionEvent.ACTION_MOVE:
                paths.get(paths.size() - 1).path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setBlack() {
        color = Color.BLACK;
    }

    public void setRed() {
        color = Color.RED;
    }

    public void setGreen() {
        color = Color.GREEN;
    }
    public class PathInfo {
        private int color;
        private Path path;

        public PathInfo(int color, Path path) {
            this.color = color;
            this.path = path;
        }
    }
}
