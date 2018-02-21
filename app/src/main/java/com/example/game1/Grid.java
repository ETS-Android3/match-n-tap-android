package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by USER on 2/7/2018.
 */

public class Grid {
    private Box[][] boxes;
    private Box topBox;

    //box width, height and space between boxes
    private static int width;
    private static int height;
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

    public Grid(Context context, int screenX, int screenY) {

        //setting box dimensions and space
        width = 4*(screenX)/15;
        height = width;
        space = width / 8;

        //setting grid dimensions
        grid_width = 3*width + 4*space;
        grid_height = 3*height + 4*space;

        //setting corner point of grid
        leftX = space;
        topY = screenY - grid_height - 4*space;

        //setting topBox corner
        topBox_leftX = (screenX-width)/2;
        topBox_topY = topY - (height + 2*space); //leaving a gap between grid and top box

        //initializing boxes and fixing their positions in the grid
        boxes = new Box[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Random rand = new Random();
                int timeInterval = rand.nextInt(1000)+3000;
                boxes[i][j] = new Box(context, leftX + space + i * (width + space),
                        topY + space + j * (height + space), width, height, space, timeInterval);
            }
        }

        topBox = new Box(context, topBox_leftX,topBox_topY,width,height,space,6000);
    }

    //method to update boxes(basically colors)
    public void update() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxes[i][j].update();
            }
        }

        topBox.update();

        if(IsColorPresent()==false){
            Random rand = new Random();
            boxes[rand.nextInt(3)][rand.nextInt(3)].changeColor(topBox.getColorIndex());
        }
    }

    public void draw(Canvas canvas, Paint paint){
        //drawing 9 boxes in the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box box = boxes[i][j];
                box.draw(canvas, paint);
            }
        }
        //draw top box
        topBox.draw(canvas, paint);
    }

    //checks if any of boxes has color same as topbox
    private boolean IsColorPresent(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(boxes[i][j].getColorIndex()==topBox.getColorIndex()){
                    return true;
                }
            }
        }
        return false;
    }

    public int checkColor(float touchX, float touchY) {
        //which box did user touch
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boxes[i][j].isTouched(touchX, touchY)) {
                    if (boxes[i][j].getColorIndex() == topBox.getColorIndex()) {
                        score+=20;
                        boxes[i][j].setClicked(1);
                        return topBox.getColorIndex();
                    } else {
                        score-=10;
                        boxes[i][j].setClicked(-1);
                        return Box.num_colors;
                    }
                }
            }
        }
        return -1;
    }

    public int getScore() {
        return score;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public int getSpace() {
        return space;
    }
}
