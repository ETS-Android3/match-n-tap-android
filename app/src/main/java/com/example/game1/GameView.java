package com.example.game1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jyothsna on 7/2/18.
 */

public class GameView extends SurfaceView implements Runnable{

    private static final String TAG = "GameView";

    // constants:
    private final int score1;
    private final int score2;
    private final int score3;

    private final long timeLimit;

    private final int[] colors;
    private final int num_colors;

    private final int padding; //for display spaces
    // ---

    private boolean isPlaying = true;
    private Thread gameThread = null;

    private int userScore;
    private int num_stars;

    private Rect timeBarRect; //for time bar

    private long startTime;
    private long currentTime;

    //the high Scores Holder
    private int highScore[] = new int[4];

    //Shared Preferences to store the High Scores
    SharedPreferences sharedPreferences;

    private Grid grid;

    private SurfaceHolder surfaceHolder = getHolder();

    private Bitmap wrongSymbol, correctSymbol;


    private SoundPool soundPool;
    final int[] correctSounds;
    final int errorSound;


    int screenX;
    int screenY;


    public GameView(Context context, int screenX, int screenY) {

        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        grid = new Grid(context, screenX, screenY);


        // constants
        num_colors = Box.num_colors;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        correctSounds = new int[num_colors];
        correctSounds[0] = soundPool.load(context, R.raw.dor,1);
        correctSounds[1] = soundPool.load(context, R.raw.mi,1);
        correctSounds[2] = soundPool.load(context, R.raw.so,1);
        correctSounds[3] = soundPool.load(context, R.raw.fa,1);

        errorSound = soundPool.load(context, R.raw.error,1);

        colors = new int[num_colors];
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;
        padding = grid.getSpace();

        wrongSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrong);
        wrongSymbol = Bitmap.createScaledBitmap(wrongSymbol,grid.getWidth(),grid.getHeight(),false);

        correctSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.correct);
        correctSymbol = Bitmap.createScaledBitmap(correctSymbol,grid.getWidth(),grid.getHeight(),false);

        score1 = 800;
        score2 = 1000;
        score3 = 1200;

        timeLimit = 60000; // 1 min

        //--

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        userScore = grid.getScore();

        timeBarRect = new Rect(padding, padding,screenX -padding,2*padding);

        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();

        num_stars = 0;
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
        currentTime = System.currentTimeMillis();

        grid.update();

        userScore = grid.getScore();

        if(currentTime-startTime > timeLimit) {
            isPlaying = false;

            // num_stars are needed after level completes
            num_stars = getNumStars(userScore);
            updateHighScoresinSharedPref(userScore);
        }

    }




    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            Paint paint = new Paint();

            // background
            canvas.drawColor(Color.WHITE);

            if(isPlaying) {
                drawGrid(canvas, paint);
                drawTopBox(canvas, paint);
                drawScore(canvas, paint);
                drawTimebar(canvas, paint);
            }
            else{
                // temp code: need to display in other screen

                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Score" + userScore, screenX/2, 150, paint);

                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Stars" + num_stars, screenX/2, 300, paint);

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

    private int getNumStars(int userScore) {
        if(userScore >= score3){
            return 3;
        }else if(userScore >= score2){
            return 2;
        }else if(userScore >= score1) {
            return 1;
        }else{
            return 0;
        }
    }

    private void updateHighScoresinSharedPref(int userScore) {
        //finding if userScore is greater than last highscore
        int finalI = 4;
        for (int i = 0; i < 4; i++) {
            if (highScore[i] < userScore) {
                finalI = i;
                break;
            }
        }
        if(finalI!=4) {
            //storing the scores through shared Preferences
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putInt("userScore" + (finalI + 1), userScore);
            for (int i = finalI + 1; i < 4; i++) {
                int j = i + 1;
                e.putInt("userScore" + j, highScore[i - 1]);
            }
            e.apply();
        }
    }

    private void drawTimebar(Canvas canvas, Paint paint) {
        //adding timebar to the screen

        // border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.timebarBorder));
        canvas.drawRect(timeBarRect, paint);

        // fill
        int width = (int) (((screenX - 2*padding)*(currentTime-startTime))/timeLimit);
        timeBarRect.set(padding, padding,width,2*padding);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.timebarFill));
        canvas.drawRect(timeBarRect, paint);
    }

    private void drawScore(Canvas canvas, Paint paint) {
        //adding userScore to the screen
        paint.setColor(getResources().getColor(R.color.scoreColor));
        paint.setTextSize(150);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(userScore), screenX/2, 3*padding - (paint.ascent()+paint.descent()), paint);
    }

    private void drawTopBox(Canvas canvas, Paint paint) {
        Box topBox = grid.getTopBox();

        //drawing grid for topbox
        paint.setColor(getResources().getColor(R.color.boxBorder));
        canvas.drawRect(topBox.getX()-grid.getSpace(),topBox.getY()-grid.getSpace(),
                topBox.getX()+topBox.getWidth()+grid.getSpace(), topBox.getY()+topBox.getHeight()+grid.getSpace(), paint);

        //drawing top Box
        paint.setColor(colors[topBox.getColorIndex()]);
        canvas.drawRect(topBox.getX(), topBox.getY(), topBox.getWidth() + topBox.getX(),
                topBox.getHeight() + topBox.getY(), paint);
    }

    private void drawGrid(Canvas canvas, Paint paint) {
        //draw grid outer box
        paint.setColor(getResources().getColor(R.color.boxBorder));
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
    }
}
