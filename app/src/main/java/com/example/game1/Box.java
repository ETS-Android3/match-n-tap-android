package com.example.game1;

import android.content.Context;

import java.util.Random;

/**
 * Created by jyothsna on 7/2/18.
 */

public class Box {
    private int x,y;
    private int width, height;
    private int colorIndex;
    private int timeInterval; //to change the color

    public static int num_colors=4;

    private long startTime;
    private long currentTime;

    private int clicked = 0; //0: not clicked, -1: wrong click; 1: correct click

    public Box(int x, int y, int width, int height, int timeInterval) {
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
        this.colorIndex = color;
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
        return colorIndex;
    }

    public void update(){
        currentTime = System.currentTimeMillis();
        if(currentTime - startTime > timeInterval) {

            colorIndex = getRandomColor();
        }
    }

    public int getRandomColor(){
        Random generator = new Random();
        int i;
        // generate color not equal to previous color
        do{
            i = generator.nextInt(num_colors);
        }while(i== colorIndex);

        startTime = currentTime;

        return i;
    }
}
