package com.example.game1;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by jyothsna on 7/2/18.
 */

public class GameView extends SurfaceView implements Runnable{

    private boolean isPlaying = true;
    private Thread gameThread = null;

    public GameView(Context context) {
        super(context);
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

    }

    private void draw() {

    }

    private void control() {

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
