package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

import static com.example.game1.GameView.correctSymbol;
import static com.example.game1.GameView.wrongSymbol;

/**
 * Created by jyothsna on 7/2/18.
 */

public class Box {
    private static final String TAG = "Box.java";

    private int x, y;
    private int width, height, space;
    private int colorIndex;
    private int newColorIndex;
    private int borderColor;
    private int timeInterval; //to change the color

    private int[] possibleColors;

    public static int num_colors = 4;

    private long startTime;
    private long currentTime;

    private boolean isChanging=false;
    private int clicked = 0; //0: not clicked, -1: wrong click; 1: correct click
    private int iterations = 0;


    public Box(Context context, int x, int y, int width, int height, int space, int timeInterval) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.space = space;
        this.timeInterval = timeInterval;

        possibleColors = new int[num_colors];
        possibleColors[0] = context.getResources().getColor(R.color.box1);
        possibleColors[1] = context.getResources().getColor(R.color.box2);
        possibleColors[2] = context.getResources().getColor(R.color.box3);
        possibleColors[3] = context.getResources().getColor(R.color.box4);

        borderColor = context.getResources().getColor(R.color.boxBorder);

        startTime = System.currentTimeMillis() - timeInterval;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void update() {
        if(isChanging) iterations++;
        if(iterations>=10 && iterations<20)
            colorIndex = newColorIndex;
        else if(iterations>=20) {
            isChanging = false;
            iterations = 0;
        }

        Log.d(TAG, "isChanging = " + isChanging + "; iterations = " + iterations);
        currentTime = System.currentTimeMillis();
        if (currentTime - startTime > timeInterval) {
            changeColor(getRandomColor());
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // draw border
        paint.setColor(borderColor);
        canvas.drawRect(x - space, y - space, width + space + x, height + space + y, paint);

        // draw box
        paint.setColor(possibleColors[colorIndex]);
        if(iterations > 0 && iterations <= 10) {
            //decreasing color
            paint.setAlpha(255-10*iterations);
        }
        else if(iterations > 10 && iterations <= 20){
            //increasing color
            paint.setAlpha(255-10*(20-iterations));
        }
        canvas.drawRect(x, y, width + x, height + y, paint);

        //draw wrong mark on wrong click
        if(clicked==-1){
            canvas.drawBitmap(wrongSymbol, x, y, paint);
            clicked=0;
        }

        //draw circle on correct click
        if(clicked==1){
            canvas.drawBitmap(correctSymbol, x, y, paint);
            changeColor(getRandomColor());
            clicked = 0;
        }
    }

    public int getRandomColor() {
        Random generator = new Random();
        int i;
        // generate color not equal to previous color
        do {
            i = generator.nextInt(num_colors);
        } while (i == colorIndex);

        startTime = currentTime;

        return i;
    }

    public void changeColor(int newColorIndex){
        // 10 frames lighten 10 darken
        isChanging = true;
        this.newColorIndex = newColorIndex;
    }

    public boolean isTouched(float touchX, float touchY){
        return (x <= touchX && touchX <= x + width &&
                y <= touchY && touchY <= y + height);
    }
}
