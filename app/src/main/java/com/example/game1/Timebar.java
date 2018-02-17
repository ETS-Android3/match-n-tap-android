package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by jyothsna on 17/2/18.
 */

public class Timebar {
    private Rect timeBarRect; //for time bar
    private long currentTime, startTime, timeLimit;
    private int padding, maxWidth;
    private boolean isTimeExpired=false;

    private int borderColor, fillColor;

    public Timebar(Context context, int padding, long timeLimit, int screenX) {
        this.timeLimit = timeLimit;
        this.padding = padding;
        this.maxWidth = screenX-2*padding;
        timeBarRect = new Rect(padding, padding,screenX -padding,2*padding);

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
        int width = (int) ((maxWidth*(timeElapsed))/timeLimit);
        timeBarRect.set(padding, padding,width,2*padding);
    }

    public void draw(Canvas canvas, Paint paint){
        // border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        canvas.drawRect(timeBarRect, paint);

        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawRect(timeBarRect, paint);
    }

    public boolean getTimeExpired(){
        return isTimeExpired;
    }
}
