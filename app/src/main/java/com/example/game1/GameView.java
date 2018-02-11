package com.example.game1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jyothsna on 7/2/18.
 */

public class GameView extends SurfaceView implements Runnable{

    private static final String TAG = "GameView";
    private boolean isPlaying = true;
    private Thread gameThread = null;

    private int score;
    private int lives;

    //the high Scores Holder
    int highScore[] = new int[4];

    //Shared Preferences to store the High Scores
    SharedPreferences sharedPreferences;

    private Grid grid;
    private SurfaceHolder surfaceHolder = getHolder();

    private Bitmap livesSymbol, wrongSymbol, correctSymbol;


    private SoundPool soundPool;
    final int[] correctSounds;
    final int errorSound;
    private int[] colors;
    int num_colors;

    public GameView(Context context, int screenX, int screenY) {

        super(context);

        grid = new Grid(context, screenX, screenY);

        livesSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.lives);
        livesSymbol = Bitmap.createScaledBitmap(livesSymbol,100,100,false);

        wrongSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrong);
        wrongSymbol = Bitmap.createScaledBitmap(wrongSymbol,grid.getWidth(),grid.getHeight(),false);

        correctSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.correct);
        correctSymbol = Bitmap.createScaledBitmap(correctSymbol,grid.getWidth(),grid.getHeight(),false);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);


        num_colors = Box.num_colors;
        correctSounds = new int[num_colors];
        correctSounds[0] = soundPool.load(context, R.raw.dor,1);
        correctSounds[1] = soundPool.load(context, R.raw.mi,1);
        correctSounds[2] = soundPool.load(context, R.raw.so,1);
        correctSounds[3] = soundPool.load(context, R.raw.fa,1);

        errorSound = soundPool.load(context, R.raw.error,1);;


        colors = new int[num_colors];
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        score = grid.getScore();
        lives = grid.getLives();
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

        score = grid.getScore();
        lives = grid.getLives();

        if(lives<=0) {
            isPlaying = false;
            //finding if score is greater than last highscore
            int finalI = 4;
            for (int i = 0; i < 4; i++) {
                if (highScore[i] < score) {
                    finalI = i;
                    break;
                }
            }
            if(finalI!=4) {
                //storing the scores through shared Preferences
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putInt("score" + (finalI + 1), score);
                for (int i = finalI + 1; i < 4; i++) {
                    int j = i + 1;
                    e.putInt("score" + j, highScore[i - 1]);
                }
                e.apply();
            }
        }

    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            Paint paint = new Paint();

            canvas.drawColor(Color.WHITE);

            if(isPlaying) {
                //draw grid outer box
                paint.setColor(Color.BLACK);
                canvas.drawRect(grid.getLeftX(), grid.getTopY(), grid.getGrid_width() + grid.getLeftX(),
                        grid.getGrid_height() + grid.getTopY(), paint);

                //drawing 9 boxes in the grid
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Box box = grid.getBox(i, j);
                        paint.setColor(colors[box.getColorIndex()]);
                        canvas.drawRect(box.getX(), box.getY(), box.getWidth() + box.getX(),
                                box.getHeight() + box.getY(), paint);

                        //draw wrong mark on wrong click
                        if(box.getClicked()==-1){
                            soundPool.play(errorSound,1,1,1,0,1);
                            canvas.drawBitmap(wrongSymbol, box.getX(), box.getY(), paint);
                            box.setClicked(0);
                        }

                        //draw circle on correct click
                        if(box.getClicked()==1){
                            soundPool.play(correctSounds[box.getColorIndex()],1,1,1,0,1);
                            canvas.drawBitmap(correctSymbol, box.getX(), box.getY(), paint);
                            box.setColorIndex(box.getRandomColor());
                            box.setClicked(0);
                        }
                    }
                }


                Box topBox = grid.getTopBox();


                //drawing grid for topbox
                paint.setColor(Color.BLACK);
                canvas.drawRect(topBox.getX()-grid.getSpace(),topBox.getY()-grid.getSpace(),
                        topBox.getX()+topBox.getWidth()+grid.getSpace(), topBox.getY()+topBox.getHeight()+grid.getSpace(), paint);

                //drawing top Box
                paint.setColor(colors[topBox.getColorIndex()]);
                canvas.drawRect(topBox.getX(), topBox.getY(), topBox.getWidth() + topBox.getX(),
                        topBox.getHeight() + topBox.getY(), paint);

                //adding score to the screen
                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("" + score, 100, 150, paint);

                //adding lives to the screen
                for(int i=1; i<=lives; i++)
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
