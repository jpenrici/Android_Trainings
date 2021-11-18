package com.example.canvasapp01;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

public class ViewCanvas extends View {

    private final Paint mPaint;
    private final Path mPath;

    public ViewCanvas(Context context) {
        super(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorLine, null));
        mPaint.setStrokeWidth(8);

        float centerX = (float) screenWidth / 2;
        float centerY = (float) screenHeight / 2;
        float radius = (float) Math.min(screenWidth, screenHeight) / 3;
        int step = 5;

        mPath = new Path();
        for (int angle = 0; angle < 360; angle += step) {
            float x = (float) (radius * Math.cos(angle * Math.PI / 180) + centerX);
            float y = (float) (radius * Math.sin(angle * Math.PI / 180) + centerY);
            if (angle == 0) {
                mPath.moveTo(x, y);
            } else {
                mPath.lineTo(x, y);
            }
        }
        mPath.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }
}
