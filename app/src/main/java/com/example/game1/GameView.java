package com.example.game1;

import android.content.Context;
import android.content.Intent;
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
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by jyothsna on 7/2/18.
 */

public class GameView extends SurfaceView implements Runnable{

    private static final String TAG = "GameView";


    // constants:
    private final int score1;
    private final int score2;
    private final int score3;

    private boolean isPlaying;
    private Thread gameThread = null;

    private int userScore;
    private int num_stars;

    private int level_num;
    private int current_level;

    public int getLevel_to_display() {
        return level_to_display;
    }

    private int level_to_display;

    //the high Scores Holder
    private int highScore[] = new int[4];

    //Shared Preferences to store the High Scores
    SharedPreferences sharedPreferences,currentLevelSP;

    private Grid grid;
    private static SoundManager soundManager;
    private Timebar timebar;

    private SurfaceHolder surfaceHolder = getHolder();

    public static Bitmap wrongSymbol, correctSymbol;

    int screenX;
    int screenY;

    private Context context;

    //public PopupWindow popupWindow;

    public GameView(Context context,int level_number, int screenX, int screenY) {

        super(context);
        isPlaying = true;

        this.context = context;

        this.screenX = screenX;
        this.screenY = screenY;

        level_num = level_number;
        currentLevelSP = context.getSharedPreferences("CURR_LEVEL",Context.MODE_PRIVATE);
        current_level = currentLevelSP.getInt("curr_level",1);
        Log.d(TAG,"level_num="+level_number);
        Log.d(TAG,"current level"+current_level);


        if(level_num==0){
            level_to_display = current_level;
        }
        else{
            level_to_display = level_num;
        }
        int range;
        if(level_to_display<9){
            range = 2000;
        }else{
            range = 1000;
        }
        grid = new Grid(context, screenX, screenY, 1000*(15-((level_to_display-1)/4)*3),
                (5- (level_to_display-1)/4)*1000,range);
        soundManager = new SoundManager(context);
        timebar = new Timebar(context, grid.getSpace(), 5000, screenX);

        wrongSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrong);
        wrongSymbol = Bitmap.createScaledBitmap(wrongSymbol,grid.getWidth(),grid.getHeight(),false);

        correctSymbol = BitmapFactory.decodeResource(context.getResources(), R.drawable.correct);
        correctSymbol = Bitmap.createScaledBitmap(correctSymbol,grid.getWidth(),grid.getHeight(),false);

        score1 = 800;
        score2 = 1000;
        score3 = 1200;

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        userScore = grid.getScore();

        num_stars = 0;
        soundManager.playBackground();

//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View customView = layoutInflater.inflate(R.layout.level_complete,null);
//
//        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
//        final View parent = this;
//        this.post(new Runnable() {
//            @Override
//            public void run() {
//                //display the popup window
//                popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
//                Log.d(TAG, "popup");
//            }
//        });
    }

    private void update() {
        grid.update();
        timebar.update();

        userScore = grid.getScore();

        if(timebar.getTimeExpired()) {
            isPlaying = false;
            // num_stars are needed after level completes
            num_stars = getNumStars(userScore);
            if(num_stars>0) {
                //level_no = 0 means current level opened through play now option
                if(level_num == 0 || level_num==current_level){
                    SharedPreferences.Editor editor = currentLevelSP.edit();
                    editor.putInt("curr_level", current_level + 1);
                    editor.apply();
                    Level level = new Level(current_level, num_stars,true,new int[4]);
                    MainActivity.levelDbHandler.addLevel(level);
                    MainActivity.levels[current_level + 1].setUnlocked(true);
                }else{
                    if(MainActivity.levelDbHandler.getLevel(level_num).getNumStars()<num_stars) {
                        Level level = new Level(level_num, num_stars,true,new int[4]);
                        MainActivity.levelDbHandler.updateLevel(level);
                    }
                }
            }
            updateHighScoresinSharedPref(userScore);


        }

        if(isPlaying==false) {
            soundManager.stopBackground();

            // go to level complete activity
            Intent intent = new Intent(context, LevelCompleteActivity.class);
            intent.putExtra("LevelNum", level_to_display);
            context.startActivity(intent);
        }
    }


    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            Paint paint = new Paint();

            // background
            canvas.drawColor(Color.WHITE);

            //if(isPlaying) {
                grid.draw(canvas,paint);
                timebar.draw(canvas, paint);
                drawScore(canvas, paint);
            //}
            /*else{
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
            }*/

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
            int clicked = grid.checkColor(event.getX(), event.getY());
            if(clicked==Box.num_colors)
                soundManager.playError();
            else if(clicked>=0)
                soundManager.playCorrect();
        }
        return super.onTouchEvent(event);
    }


    public static void stopMusic(){
        soundManager.stopBackground();
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

    private void drawScore(Canvas canvas, Paint paint) {
        //adding userScore to the screen
        paint.setColor(getResources().getColor(R.color.scoreColor));
        paint.setTextSize(150);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(userScore),
                screenX/2, 3*grid.getSpace() - (paint.ascent()+paint.descent()), paint);
        drawLevelNum(canvas, paint);


    }

    private void drawLevelNum(Canvas canvas, Paint paint) {
        // draw circle to display level number in the circle
        float radius = 50+grid.getSpace()/2;
        paint.setColor(getResources().getColor(R.color.timebarFill));
        canvas.drawCircle(radius, radius, radius, paint);

        // draw text for level number
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint.setTextSize(100);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(level_to_display),
                radius, grid.getSpace() - (paint.ascent()+paint.descent()), paint);
    }
}
