package com.example.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jyothsna on 7/2/18.
 */

public class GameView extends SurfaceView implements Runnable{

    private boolean isPlaying = true;
    private Thread gameThread = null;

    private Grid grid;
    private SurfaceHolder surfaceHolder = getHolder();

    public GameView(Context context, int screenX, int screenY) {

        super(context);
        grid = new Grid(context, screenX, screenY);
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        grid.update();
    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.WHITE);

            //draw grid outer box
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(grid.getLeftX(), grid.getTopY(), grid.getGrid_width()+grid.getLeftX(), grid.getGrid_height()+grid.getTopY(), paint);

            //drawing 9 boxes in the grid
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    Box box = grid.getBox(i,j);
                    paint.setColor(box.getColor());
                    canvas.drawRect(box.getX(), box.getY(), box.getWidth()+box.getX(), box.getHeight()+box.getY(), paint);
                }
            }

            //drawing top Box
            Box topBox = grid.getTopBox();
            paint.setColor(topBox.getColor());
            canvas.drawRect(topBox.getX(),topBox.getY(),topBox.getWidth()+topBox.getX(),topBox.getHeight()+topBox.getY(),paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try{
            gameThread.sleep(250);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
