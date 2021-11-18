package com.example.canvasapp02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class ViewCanvas extends View {

    private final Paint paint;
    private final Path path;

    public ViewCanvas(Context context) {
        super(context);

        paint = new Paint();
        path = new Path();

        paint.setAntiAlias(true);
        paint.setStrokeWidth(4f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void clean() {
        path.reset();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float posX = event.getX();
        float posY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(posX, posY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(posX, posY);
                performClick();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
