package com.example.game1;

import android.content.Context;

/**
 * Created by USER on 2/7/2018.
 */

public class Grid {
    private Box[][] boxes;
    private int width;
    private int height;
    private int space;

    public Grid(Context context, int screenX, int screenY) {

        width = 20;
        height = 20;
        space = width / 2;

        int leftX = (screenX - (3 * width + 2 * space)) / 2;
        int topY = (screenY * 3 / 4);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxes[i][j] = new Box(context, leftX + i * (width + space), topY + j * (height + space), width, height);
            }
        }

    }
    public void update() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxes[i][j].update();
            }
        }
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpace() {
        return space;
    }
}
