package com.example.game1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
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

    private Bitmap livesSymbol;

    public GameView(Context context, int screenX, int screenY) {

        super(context);
        livesSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.lives);
        livesSymbol = Bitmap.createScaledBitmap(livesSymbol,100,100,false);
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
        if(grid.getLives()<=0) isPlaying = false;
    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            Paint paint = new Paint();

            canvas.drawColor(Color.WHITE);

            if(isPlaying) {
                //draw grid outer box
                paint.setColor(Color.BLACK);
                canvas.drawRect(grid.getLeftX(), grid.getTopY(), grid.getGrid_width() + grid.getLeftX(), grid.getGrid_height() + grid.getTopY(), paint);

                //drawing 9 boxes in the grid
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Box box = grid.getBox(i, j);
                        paint.setColor(box.getColor());
                        canvas.drawRect(box.getX(), box.getY(), box.getWidth() + box.getX(), box.getHeight() + box.getY(), paint);
                    }
                }

                //drawing top Box
                Box topBox = grid.getTopBox();
                paint.setColor(topBox.getColor());
                canvas.drawRect(topBox.getX(), topBox.getY(), topBox.getWidth() + topBox.getX(), topBox.getHeight() + topBox.getY(), paint);

                //adding score to the screen
                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("" + grid.getScore(), canvas.getWidth()/2, 100, paint);

                //adding lives to the screen
                for(int i=1; i<=grid.getLives(); i++)
                    canvas.drawBitmap(livesSymbol, canvas.getWidth()-i*100,50,paint);
            }
            else{
                paint.setColor(Color.BLACK);
                paint.setTextSize(200);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Gameover",canvas.getWidth()/2, canvas.getHeight()/2, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try{
            gameThread.sleep(17);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
            grid.checkColor(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }
}
