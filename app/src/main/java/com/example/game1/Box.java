package com.example.game1;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by jyothsna on 7/2/18.
 */

public class Box {
    private int x,y;
    private int width, height;
    private Color color;
    private Context context;

    public Box(Context context, int x, int y, int width, int height) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setColor(Color color) {
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

    public Color getColor() {
        return color;
    }

    public void update(){
        // change color after some time interval
    }
}
