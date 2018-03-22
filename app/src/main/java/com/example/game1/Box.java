package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

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
    private int borderColor;
    private int timeInterval; //to change the color

    private int[] possibleColors;

    public static int num_colors = 4;

    private long startTime;
    private long currentTime;

    private int clicked = 0; //0: not clicked, -1: wrong click; 1: correct click


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

        borderColor = context.getResources().getColor(R.color.black);

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
        currentTime = System.currentTimeMillis();
        if (currentTime - startTime > timeInterval) {
            changeColor(getRandomColor());
            startTime = currentTime;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // draw border
        paint.setColor(borderColor);
        canvas.drawRect(x - space, y - space, width + space + x, height + space + y, paint);

        // draw box
        paint.setColor(possibleColors[colorIndex]);
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

        return i;
    }

    public void changeColor(int newColorIndex){
        this.colorIndex = newColorIndex;
    }

    public boolean isTouched(float touchX, float touchY){
        return (x <= touchX && touchX <= x + width &&
                y <= touchY && touchY <= y + height);
    }
}
