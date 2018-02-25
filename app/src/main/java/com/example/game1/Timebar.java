package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by jyothsna on 17/2/18.
 */

public class Timebar {
    private RectF timeBarRect; //for time bar
    private long currentTime, startTime, timeLimit;
    private int padding, maxWidth;
    private boolean isTimeExpired=false;
    private int width;
    private int borderColor, fillColor;

    public Timebar(Context context, int padding, long timeLimit, int screenX) {
        this.timeLimit = timeLimit;
        this.padding = padding;
        this.maxWidth = screenX-2*padding-100;
        timeBarRect = new RectF(padding+100, padding,screenX -padding,2*padding);
        borderColor = context.getResources().getColor(R.color.timebarBorder);
        fillColor = context.getResources().getColor(R.color.timebarFill);

        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
    }

    public void update(){
        currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime-startTime;
        if(timeElapsed > timeLimit)
            isTimeExpired = true;
        width = (int) ((maxWidth*(timeElapsed))/timeLimit);
    }

    public void draw(Canvas canvas, Paint paint){
        // level number


        // border
        timeBarRect.set(padding+100, padding,maxWidth+padding+100,2*padding);
        //paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        canvas.drawRoundRect(timeBarRect, 100, 100, paint);

        // fill
        timeBarRect.set(padding+100, padding,width+padding+100,2*padding);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawRoundRect(timeBarRect, 100, 100, paint);
    }

    public boolean getTimeExpired(){
        return isTimeExpired;
    }
}
