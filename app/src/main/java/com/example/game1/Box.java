package com.example.game1;

import android.content.Context;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by jyothsna on 7/2/18.
 */

public class Box {
    private int x,y;
    private int width, height;
    private int color;
    private int timeInterval;

    private Context context;

    public static int num_colors=4;


    private long startTime;
    private long currentTime;

    private int clicked = 0; //0: not clicked, -1: wrong click; 1: correct click

    public Box(Context context, int x, int y, int width, int height, int timeInterval) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timeInterval = timeInterval;
        startTime = System.currentTimeMillis()-timeInterval;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public void setColorIndex(int color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColorIndex() {
        return color;
    }

    public void update(){
        currentTime = System.currentTimeMillis();
        if(currentTime - startTime > timeInterval) {

            color = getRandomColor();
            startTime = currentTime;
        }
    }

    public int getRandomColor(){
        Random generator = new Random();
        int i = generator.nextInt(num_colors);
        return i;
    }
}
