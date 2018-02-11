package com.example.game1;

import android.content.Context;

import java.util.Random;

/**
 * Created by USER on 2/7/2018.
 */

public class Grid {
    private Box[][] boxes;
    private Box topBox;

    //box width, height and space between boxes
    private int width;
    private int height;
    private int space;

    //grid width and height
    private int grid_width;
    private int grid_height;

    //point for top left corner of grid
    private int leftX;
    private int topY;

    //point for topBox corner
    private int topBox_leftX;
    private int topBox_topY;

    private int score=0;
    private int lives=5;

    public Grid(Context context, int screenX, int screenY) {

        //setting box dimensions and space
        width = (2*screenX)/9;
        height = width;
        space = width / 4;

        //setting grid dimensions
        grid_width = 3*width + 4*space;
        grid_height = 3*height + 4*space;

        //setting corner point of grid
        leftX = space;
        topY = screenY - grid_height - 4*space;

        //setting topBox corner
        topBox_leftX = (screenX-width)/2;
        topBox_topY = (topY - height)/2;

        //initializing boxes and fixing their positions in the grid
        boxes = new Box[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Random rand = new Random();
                int timeInterval = rand.nextInt(1000)+2000;
                boxes[i][j] = new Box(context, leftX + space + i * (width + space),
                        topY + space + j * (height + space), width, height,timeInterval);
            }
        }

        topBox = new Box(context,topBox_leftX,topBox_topY,width,height,5000);
    }

    //method to update boxes(basically colors)
    public void update() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxes[i][j].update();
            }
        }

        topBox.update();
    }

    public void checkColor(float touchX, float touchY) {
        //which box did user touch
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boxes[i][j].getX() <= touchX && touchX <= boxes[i][j].getX() + width &&
                        boxes[i][j].getY() <= touchY && touchY <= boxes[i][j].getY() + height) {
                    if (boxes[i][j].getColor() == topBox.getColor()) {
                        score+=20;
                        boxes[i][j].setColor(boxes[i][j].getRandomColor());
                    } else {
                        lives--;
                    }
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getGrid_width() {
        return grid_width;
    }

    public int getGrid_height() {
        return grid_height;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getTopY() {
        return topY;
    }

    public Box getBox(int i, int j) {
        return boxes[i][j];
    }

    public Box getTopBox() {
        return topBox;
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
