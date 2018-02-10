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

    private int num_colors;
    private int[] colors;

    private long startTime;
    private long currentTime;

    public Box(Context context, int x, int y, int width, int height, int timeInterval) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timeInterval = timeInterval;
        startTime = System.currentTimeMillis()-timeInterval;

        num_colors = 6;
        colors = new int[num_colors];
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;
        colors[4] = Color.CYAN;
        colors[5] = Color.MAGENTA;
    }

    public void setColor(int color) {
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

    public int getColor() {
        return color;
    }

    public void update(){
        currentTime = System.currentTimeMillis();
        if(currentTime - startTime > timeInterval) {
            Random generator = new Random();
            int i = generator.nextInt(num_colors);
            color = colors[i];
            startTime = currentTime;
        }
    }
}
